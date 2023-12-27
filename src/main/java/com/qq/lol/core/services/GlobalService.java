package com.qq.lol.core.services;

import com.qq.lol.core.services.impl.LolPlayerServiceImpl;
import com.qq.lol.core.services.impl.LootServiceImpl;
import com.qq.lol.dto.SummonerInfoDto;
import com.qq.lol.utils.StandardOutTime;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
    // 创建线程池，最大线程 3个，以免 LCU挂掉
    public static final ExecutorService fixedThreadPool = Executors.newFixedThreadPool(3);

    /**
     * 全局的当前已登录召唤师信息，使用该信息不会频繁调用 LCU
     */
    private static SummonerInfoDto summonerInfo = null;
    // 当前神话精粹数量
    private static Integer mythicCount = -1;
    // 默认查询战绩数量 10条
    private static Integer historySize = 9;

    private static final Properties prop;

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

    public static GlobalService getGlobalService() {
        return globalService;
    }

    public static Integer getHistorySize() {
        return historySize;
    }

    // 修改默认查询战绩数量
    public void setHistorySize(Integer historySize) {
        GlobalService.historySize = historySize;
        // 写入配置文件
        prop.setProperty("historySize", historySize.toString());
        try {
            OutputStream os = new FileOutputStream("src/main/resources/data.properties");
            prop.store(os, "更新配置时间" + StandardOutTime.getCurrentTime());    // 加一行注释
            os.close();
        } catch (IOException e) {
            System.out.println("写入properties失败");
            e.printStackTrace();
        }
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
