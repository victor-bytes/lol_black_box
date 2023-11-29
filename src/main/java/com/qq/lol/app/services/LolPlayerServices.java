package com.qq.lol.app.services;

import com.qq.lol.dto.*;

import java.util.List;

/**
 * @Date: 2023/11/29 - 11 - 29 - 18:56
 * @Description: 玩家 api
 * @version: 1.0
 */
public interface LolPlayerServices {
    /**
     * 获取当前已登录的召唤师信息
     * @return
     */
    SummonerInfoDto getCurrentSummoner();

    /**
     * 通过 puuid获取玩家信息
     * /lol-summoner/v2/summoners/puuid/
     * @param puuid
     * @return
     */
    PlayerInfoDto getPlayerInfoByPuuid(String puuid);

    /**
     * 通过puuid获取玩家段位
     * /lol-ranked/v1/ranked-stats/
     * @param puuid
     * @return
     */
    String getRankByPuuid(String puuid);

    /**
     * 通过 puuid查询玩家近期 20 场游戏中的前 6 场排位战绩，
     * 如果没有则为 null
     * /lol-match-history/v1/products/lol/" + id + "/matches?begIndex=0&endIndex=" + endIndex
     * @param puuid
     */
    List<ScoreInfoDto> getRecentScoreInfoByPuuid(String puuid, int endIndex);

    /**
     * @Description: 通过 puuid查询玩家近 20 场战绩（所有模式）
     * @param puuid:
     * @param begIndex:
     * @param endIndex:
     * @return java.util.List<com.qq.lol.dto.ScoreInfoDto>
     * @throws
     * @Auther: null
     * @Date: 2023/11/29 - 19:51
     */
    List<ScoreInfoDto> getScoreInfoByPuuid(String puuid, int begIndex, int endIndex);

    /**
     * 获取当前游戏两队人的puuid
     */
    TeamPuuidDto getTeamPuuid();



}
