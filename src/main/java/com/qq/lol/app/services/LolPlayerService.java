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
     * 获取当前已登录游戏客户端的召唤师信息
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
     * 获取游戏中十个玩家的信息,不包含战绩
     * 战绩、信息分开获取
     * @return
     */
    List<PlayerInfoDto> getAllInGamePlayerInfo(TeamPuuidDto teamPuuidDto);

    /**
     * 获取十个玩家近期 20场战绩中前 6场排位战绩（不区分单排、组排）
     * @param teamPuuidDto
     * @return
     */
    Map<String, List<GameScoreInfoDto>> getRecentSixRankScoreInfo(TeamPuuidDto teamPuuidDto);

    /**
     * 获取十个玩家近期 20场战绩中前 6场匹配战绩
     * @param teamPuuidDto
     * @return
     */
    Map<String, List<GameScoreInfoDto>> getRecentSixNormalScoreInfo(TeamPuuidDto teamPuuidDto);

    /**
     * 获取十个玩家近期 20场战绩中前 6场大乱斗战绩
     * @param teamPuuidDto
     * @return
     */
    Map<String, List<GameScoreInfoDto>> getRecentSixARAMScoreInfo(TeamPuuidDto teamPuuidDto);

    /**
     * 通过puuid获取玩家段位
     * /lol-ranked/v1/ranked-stats/
     * @param puuid
     * @return
     */
    List<RankDto> getRankByPuuid(String puuid);

    /**
     * 通过 puuid查询玩家近期 20 场战绩
     * 如果没有则为 null
     * /lol-match-history/v1/products/lol/" + id + "/matches?begIndex=0&endIndex=" + endIndex
     * @param puuid
     */
    List<GameScoreInfoDto> getRecentTwentyScoreInfoByPuuid(String puuid, int endIndex);

    /**
     * @Description: 通过 puuid查询玩家战绩（所有模式）
     * @param puuid:
     * @param begIndex: 0代表最近一场
     * @param endIndex: 截止到第几场战绩（不包含）
     * @return java.util.List<com.qq.lol.dto.ScoreInfoDto>
     *     包含大区
     * @throws
     * @Auther: null
     * @Date: 2023/11/29 - 19:51
     */
    List<GameScoreInfoDto> getScoreInfoByPuuid(String puuid, int begIndex, int endIndex);


}
