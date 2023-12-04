package com.qq.lol.app.services;

import com.qq.lol.dto.*;

import java.util.List;
import java.util.Map;

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
     * @Description: 获取游戏中十个玩家的信息,不包含战绩
     * 战绩、信息分开获取
     * @return java.util.List<com.qq.lol.dto.PlayerInfoDto>
     * @throws
     * @Auther: null
     * @Date: 2023/12/4 - 14:24
     */
    List<PlayerInfoDto> getPlayersInfoByPuuid();

}
