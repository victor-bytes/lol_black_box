package com.qq.lol.core.services;

import com.qq.lol.dto.PlayerInfoDto;
import com.qq.lol.dto.ReportStat;
import com.qq.lol.dto.SummonerInfoDto;

/**
 * @Date: 2023/11/29 - 11 - 29 - 18:56
 * @Description: 玩家 api
 * @version: 1.0
 */
public interface LolPlayerService {


    /**
     * @Description: 获取当前已登录游戏客户端的召唤师信息
     * @return com.qq.lol.dto.SummonerInfoDto
     * @throws
     * @Auther: null
     * @Date: 2023/12/4 - 14:24
     */
    SummonerInfoDto getCurrentSummoner();

    /**
     * @Description: 通过 puuid获取玩家信息
     * @param puuid:
     * @return com.qq.lol.dto.PlayerInfoDto
     * @throws
     * @Auther: null
     * @Date: 2023/12/4 - 14:27
     */
    PlayerInfoDto getPlayerInfoByPuuid(String puuid);

    /**
     * @Description: 游戏结束举报玩家
     * @param reportStat:
     * @return void
     * @Auther: null
     * @Date: 2024/6/12 - 23:42
     */
    void reportPlayer(ReportStat reportStat);


}
