package com.qq.lol.frame;

import com.qq.lol.utils.NetWebsocket;

/**
 * @Auther: null
 * @Date: 2023/12/26 - 12 - 26 - 10:27
 * @Description: 标准启动类
 * @version: 1.0
 */
public class AppStart {
    public static void main(String[] args) {
//        订阅客户端事件
        NetWebsocket.getNetWebsocket().subscribe_LCU();
//        启动软件
        MainApp.main(args);
        // 关闭websocket和OkHttpClient
        NetWebsocket.getNetWebsocket().closeWebsocket();
    }
}
