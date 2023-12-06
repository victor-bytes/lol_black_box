package com.qq.lol.app.services;

import com.qq.lol.app.services.impl.LolPlayerServiceImpl;
import com.qq.lol.dto.SummonerInfoDto;
import com.qq.lol.utils.NetRequestUtil;

/**
 * @Auther: null
 * @Date: 2023/12/6 - 12 - 06 - 14:23
 * @Description: 控制一些全局信息
 * @version: 1.0
 */
public class GlobalService {
    private final NetRequestUtil netRequestUtil = NetRequestUtil.getNetRequestUtil();
    private static final GlobalService globalService = new GlobalService();
    private static final LolPlayerService lolPlayerService = LolPlayerServiceImpl.getLolPlayerService();
    /**
     * 全局的当前已登录召唤师信息，使用该信息不会频繁调用 LCU
     */
    private static SummonerInfoDto summonerInfo = null;

    private GlobalService(){}

    public static GlobalService getGlobalService() {
        return globalService;
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
            System.out.println("====第一次获取当前已登录游戏客户端的召唤师信息===");
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

        return summonerInfo;
    }

}
