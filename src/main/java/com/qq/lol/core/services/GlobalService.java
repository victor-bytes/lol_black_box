package com.qq.lol.core.services;

import com.qq.lol.core.services.impl.LolHeroServiceImpl;
import com.qq.lol.core.services.impl.LolPlayerServiceImpl;
import com.qq.lol.core.services.impl.LootServiceImpl;
import com.qq.lol.dto.SummonerInfoDto;
import com.qq.lol.utils.StandardOutTime;

/**
 * @Auther: null
 * @Date: 2023/12/6 - 12 - 06 - 14:23
 * @Description: 控制一些全局信息
 * @version: 1.0
 */
public class GlobalService {
    private static final GlobalService globalService = new GlobalService();
    private static final LolPlayerService lolPlayerService = LolPlayerServiceImpl.getLolPlayerService();
    private static final LolHeroService lolHeroService = LolHeroServiceImpl.getLolHeroService();
    private static final LootService lootService = LootServiceImpl.getLootService();

    /**
     * 全局的当前已登录召唤师信息，使用该信息不会频繁调用 LCU
     */
    private static SummonerInfoDto summonerInfo = null;
    // TODO 当前神话精粹数量
    private static Integer mythicCount = -1;
    // 默认查询战绩数量
    private static Integer historySize = 9;

    static {
        // 初始化英雄列表
        Integer i = lolHeroService.updateHeroes();
        if(i < 1)
            System.out.println(StandardOutTime.getCurrentTime() + " 同步英雄信息失败 ");
//        if(i > 1)
//            System.out.println("------ 有新增英雄，已更新到数据库 ------");
//        else if(i == 1)
//            System.out.println("------ 未新增英雄 ------");
//        else
//            System.out.println("------ 同步英雄信息失败 ------");
        // 获取召唤师信息
        globalService.getLoginSummoner();
        // 获取神话精粹数量
        globalService.getMythicCount();

    }

    private GlobalService(){}

    public static GlobalService getGlobalService() {
        return globalService;
    }

    public static Integer getHistorySize() {
        return historySize;
    }

    public static void setHistorySize(Integer historySize) {
        GlobalService.historySize = historySize;
    }

    /**
     * @Description: 获取当前神话精粹数量
     * @return java.lang.Integer -1获取失败
     * @Auther: null
     * @Date: 2023/12/18 - 12:50
     */
    public Integer getMythicCount(){
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
    public Integer refreshMythicCount(){
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
