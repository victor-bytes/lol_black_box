package com.qq.lol.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.qq.lol.core.services.GlobalService;
import com.qq.lol.frame.controller.ControllerManager;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import okhttp3.*;
import okio.ByteString;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @Auther: null
 * @Date: 2024/6/12 - 06 - 12 - 11:25
 * @Description: 配置websocket，用于监听并响应客户端事件
 * @version: 1.0
 */
public class NetWebsocket {

    private static NetWebsocket netWebsocket;
    private static OkHttpClient client;
    private static WebSocket websocket;
    private static final GlobalService globalService = GlobalService.getGlobalService();

    private int retryCount = 0;
    private static final int MAX_RETRY = 5; // 最大重连次数
    private ScheduledExecutorService scheduler;

    private NetWebsocket(){
        NetRequestUtil netRequestUtil = NetRequestUtil.getNetRequestUtil();
        client = netRequestUtil.getClient();
        scheduler = Executors.newScheduledThreadPool(1); // 创建调度线程池
    }

    public static NetWebsocket getNetWebsocket() {
        if(netWebsocket == null) {
            return new NetWebsocket();
        }
        return netWebsocket;
    }

    public void closeWebsocket() {
        if(websocket != null){
            boolean b = websocket.close(1000, "客户端主动关闭websocket链接");
            System.out.println("关闭websocket...boolean = " + b);

            websocket = null;
        }
        if(client != null) {
            System.out.println("关闭OkHttpClient...");
            client.dispatcher().executorService().shutdown();
            client = null;
        }
    }

    private static void defaultEventHandler() {
        System.out.println("调用默认事件处理器处理事件...");
        Platform.runLater(() -> ControllerManager.mainWindowController.refreshClientStatus());
        /**
         * {"data":"Lobby","eventType":"Update","uri":"/lol-gameflow/v1/gameflow-phase"}]   大厅中
         * {"data":"None","eventType":"Update","uri":"/lol-gameflow/v1/gameflow-phase"}]    客户端主界面
         * {"data":"ChampSelect","eventType":"Update","uri":"/lol-gameflow/v1/gameflow-phase"} 英雄选择
         */
    }

    /**
     * @Description: 客户端进入游戏，UI切换到对局界面
     * @return void
     * @Auther: null
     * @Date: 2024/6/12 - 11:43
     */
    private static void inPressHandler() {
        System.out.println("事件处理器更新UI界面...");
        globalService.addRecorderText(StandardOutTime.getCurrentTime() + " 事件处理器更新对局界面--");
        // 确保在 JavaFX 应用线程上更新 UI
        Platform.runLater(() -> ControllerManager.mainWindowController.refreshGameHistory(new ActionEvent()));
    }

    public void subscribe_LCU() {
        Request request = new Request.Builder()
                .url(NetRequestUtil.getDefaultHost())
                .get()
                .headers(NetRequestUtil.getDefaultHeaders())
                .build();

        String subEndpoint = "OnJsonApiEvent_lol-gameflow_v1_gameflow-phase";

        client.dispatcher().cancelAll();//清理一次
        websocket = client.newWebSocket(
                request,
                new WebSocketListener() {
                    // 连接成功
                    @Override
                    public void onOpen(@NotNull WebSocket webSocket, @NotNull Response response) {
//                        super.onOpen(webSocket, response);
                        System.out.println("websocket链接打开");
                        // 使用WebSocket对象发送消息，msg为消息内容（一般是json，当然你也可以使用其他的，例如xml等），send方法会马上返回发送结果。
                        // riot给的实示例：[8,"OnJsonApiEvent",{"data":[],"eventType":"Update","uri":"/lol-ranked/v1/notifications"}]
                        // 订阅后就会收到一个空的消息包，所以在 onMessage()中最好判断一下事件类型，以免不必要的调用事件处理器
                        boolean send = webSocket.send("[5,\"" + subEndpoint + "\"]");

                        GlobalService.setInitRecorder(StandardOutTime.
                                        getCurrentTime() + " websocket链接建立" + (send ? "成功" : "失败") + "--");

                        retryCount = 0; // 重置重连次数
                    }

                    //收到字符串类型的消息，一般我们都是使用这个
                    @Override
                    public void onMessage(@NotNull WebSocket webSocket, @NotNull String text) {
                        System.out.println("接收到消息：" + text);
                        if(!StringUtils.equals(null, text) && !StringUtils.equals("", text)) {
                            defaultEventHandler();
                            // 解析json数据
                            JSONArray jsonArray = JSON.parseArray(text);
                            String phase = jsonArray.getJSONObject(2).getString("data");
                            if(StringUtils.equals(phase, "InProgress") || StringUtils.equals(phase, "inGame")) {
                                inPressHandler();
                            }
                        }
                    }

                    // 收到字节数组类型消息
                    @Override
                    public void onMessage(@NotNull WebSocket webSocket, @NotNull ByteString bytes) {
                        System.out.println("这个可以不管，这个接收到是byte类型的");
                    }

                    @Override
                    public void onClosing(@NotNull WebSocket webSocket, int code, @NotNull String reason) {
                        System.out.println("连接关闭中");
                    }

                    // 连接关闭
                    @Override
                    public void onClosed(@NotNull WebSocket webSocket, int code, @NotNull String reason) {
                        System.out.println("连接关闭, 代码: " + code + ", 原因: " + reason);
                    }

                    // 连接失败，一般都是在这里发起重连操作
                    @Override
                    public void onFailure(@NotNull WebSocket webSocket, @NotNull Throwable t, Response response) {
                        System.out.println("连接失败: " + t.getMessage());
                        GlobalService.setInitRecorder(StandardOutTime.getCurrentTime() + " websocket开始重连--");

                        if (retryCount < MAX_RETRY) {
                            retryCount++;
                            long delay = (long) Math.pow(2, retryCount); // 指数退避算法
                            System.out.println("在 " + delay + " 秒后重连...");
                            globalService.addRecorderText(StandardOutTime.getCurrentTime() + " 在 " + delay + " 秒后重连...");
                            // 使用调度器延迟执行重连
                            scheduler.schedule(NetWebsocket.this::subscribe_LCU, delay, TimeUnit.SECONDS);
                        } else {
                            System.out.println("达到最大重连次数，停止重连。");
                            globalService.addRecorderText(StandardOutTime.getCurrentTime() + " 达到最大重连次数，停止重连。");
                        }
                    }
                });
    }
}
