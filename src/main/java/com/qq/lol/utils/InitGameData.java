package com.qq.lol.utils;

import com.qq.lol.app.services.impl.LolHeroServiceImpl;
import com.qq.lol.app.services.LolHeroService;

import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: null
 * @Date: 2023/12/4 - 12 - 04 - 11:38
 * @Description: 初始化 LOL信息
 * @version: 1.0
 */
public class InitGameData {
    private static LolHeroService lolHeroService = LolHeroServiceImpl.getLolHeroService();

    //    游戏队列id，用于识别战绩中游戏类型
    public static final Map<String, String> gameQueueIdToName = new HashMap<>();

    static {
        gameQueueIdToName.put("420", "单排");
        gameQueueIdToName.put("430", "匹配");
        gameQueueIdToName.put("440", "组排");
        gameQueueIdToName.put("450", "大乱斗");
        gameQueueIdToName.put("900", "无限火力");
        gameQueueIdToName.put("830", "人机入门");
        gameQueueIdToName.put("840", "人机新手");
        gameQueueIdToName.put("850", "人机一般");
        gameQueueIdToName.put("-1", "未知队列类型");
    }

    private InitGameData() {}

    /**
     * 初始化英雄信息
     */
    public static void initHeroData() {

    }

}
