package com.qq.lol.core.services;

import com.qq.lol.core.services.impl.LolPlayerServiceImpl;
import com.qq.lol.core.services.impl.LootServiceImpl;
import com.qq.lol.dto.GameRoomInfoDto;
import com.qq.lol.dto.SummonerInfoDto;
import com.qq.lol.frame.controller.ControllerManager;
import com.qq.lol.utils.StandardOutTime;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

/**
 * @Auther: null
 * @Date: 2023/12/6 - 12 - 06 - 14:23
 * @Description: 控制一些全局信息
 * @version: 1.0
 */
public class GlobalService {
    private static final GlobalService globalService = new GlobalService();
    private static final LolPlayerService lolPlayerService = LolPlayerServiceImpl.getLolPlayerService();
    private static final LootService lootService = LootServiceImpl.getLootService();

    /**
     * 全局的当前已登录召唤师信息，使用该信息不会频繁调用 LCU
     */
    private static SummonerInfoDto summonerInfo = null;
    // 当前神话精粹数量
    private static Integer mythicCount = -1;
    // 默认查询战绩数量 10条
    private static Integer historySize = 9;

    // 当前登录用户大区
    private static String platform;

    private static final Properties prop;

    // 用于存放程序初始化的一些信息显示到 recorder
    private static String initRecorder = "";

    // 当前游戏房间信息，主要给拉黑举报玩家时使用
    private static GameRoomInfoDto currGameRoomInfo;

    public static GameRoomInfoDto getCurrGameRoomInfo() {
        return currGameRoomInfo;
    }

    public static void setCurrGameRoomInfo(GameRoomInfoDto currGameRoomInfo) {
        GlobalService.currGameRoomInfo = currGameRoomInfo;
    }

    static {
        prop = new Properties();
        InputStream is = GlobalService.class.getClassLoader().getResourceAsStream("data.properties");
        try {
            prop.load(is);
            // 初始化 historySize
            String result = prop.getProperty("historySize", "9");   // 若没读取到，则设为默认值 9
            historySize = Integer.parseInt(result);

        } catch (IOException e) {
            System.out.println("初始化 historySize失败");
            e.printStackTrace();
        } finally {
            if(is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private GlobalService(){}

    public static String getInitRecorder() {
        return initRecorder;
    }

    public static void setInitRecorder(String initRecorder) {
        GlobalService.initRecorder = GlobalService.initRecorder + "\n" + initRecorder;
    }

    public static GlobalService getGlobalService() {
        return globalService;
    }

    public static Integer getHistorySize() {
        return historySize;
    }

    public static String getPlatform() {
        return platform;
    }

    public static void setPlatform(String platform) {
        GlobalService.platform = platform;
    }

    // 修改默认查询战绩数量（jar包运行修改无效）
    public void setHistorySize(Integer historySize) {
        GlobalService.historySize = historySize;
        // 写入配置文件
        prop.setProperty("historySize", historySize.toString());
        try {
            // 打成 jar包后无法直接修改，jar包是一种压缩包
            OutputStream os = new FileOutputStream("src/main/java/data.properties");
            prop.store(os, "更新配置时间" + StandardOutTime.getCurrentTime());    // 加一行注释
            os.close();
        } catch (IOException e) {
            System.out.println("写入properties失败");
            e.printStackTrace();
        }
    }

    /**
     * @Description: 向recorder中添加记录
     * @param text:
     * @return void
     * @Auther: null
     * @Date: 2024/6/13 - 15:47
     */
    public void addRecorderText(String text) {
        ControllerManager.runningRecorderController.addRecorderText(text);
    }

    /**
     * @Description: 获取当前神话精粹数量
     * @return java.lang.Integer -1获取失败
     * @Auther: null
     * @Date: 2023/12/18 - 12:50
     */
    public static Integer getMythicCount(){
        if(mythicCount == -1)
            mythicCount = lootService.getMythicCount();

        return mythicCount;
    }

    /**
     * @Description: 刷新神话精粹数量
     * @return java.lang.Integer
     * @Auther: null
     * @Date: 2023/12/18 - 12:56
     */
    public static Integer refreshMythicCount(){
        mythicCount = lootService.getMythicCount();

        return mythicCount;
    }

    /**
     * @Description: 获取当前已登录游戏客户端的召唤师信息
     * 该方法不会频繁调用 LCU,只会在 app初次启动时调用一次 LCU
     * @return com.qq.lol.dto.SummonerInfoDto
     * @Auther: null
     * @Date: 2023/12/6 - 14:38
     */
    public SummonerInfoDto getLoginSummoner() {
        if(summonerInfo == null) {
            summonerInfo = lolPlayerService.getCurrentSummoner();
        }

        return summonerInfo;
    }

    /**
     * @Description: 刷新当前已登录召唤师信息
     * @return com.qq.lol.dto.SummonerInfoDto
     * @Auther: null
     * @Date: 2023/12/6 - 14:46
     */
    public SummonerInfoDto refreshSummoner() {
        summonerInfo = lolPlayerService.getCurrentSummoner();
        // 神话精粹数量也要刷新
        refreshMythicCount();

        return summonerInfo;
    }

}
