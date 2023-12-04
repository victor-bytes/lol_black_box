package com.qq.lol.app.services;

import com.qq.lol.dto.GameScoreInfoDto;

import java.util.List;
import java.util.Map;

/**
 * @Auther: null
 * @Date: 2023/12/4 - 12 - 04 - 16:52
 * @Description: 战绩
 * @version: 1.0
 */
public interface GameHistoryService {

    /**
     * @Description: 通过 gameId查询对局信息
     * @return com.qq.lol.dto.GameScoreInfoDto
     * @throws
     * @Auther: null
     * @Date: 2023/12/4 - 20:48
     */
//    GameScoreInfoDto getGameScoreById();

    /**
     * @Description: 获取十个玩家近期 20场战绩中前 6场组排战绩
     * @return java.util.Map<java.lang.String,java.util.List<com.qq.lol.dto.GameScoreInfoDto>>
     * @throws
     * @Auther: null
     * @Date: 2023/12/4 - 14:24
     */
    Map<String, List<GameScoreInfoDto>> getRecentSixRankFlexScoresInfo();

    /**
     * @Description: 通过puuid获取 玩家近期 20场战绩中前 6场组排战绩
     * @param puuid: puuid
     * @return java.util.List<com.qq.lol.dto.GameScoreInfoDto>
     * @throws
     * @Auther: null
     * @Date: 2023/12/4 - 16:48
     */
    List<GameScoreInfoDto> getRecentSixRankFlexScoreInfo(String puuid);

    /**
     * @Description: 获取十个玩家近期 20场战绩中前 6场单排战绩
     * @return java.util.Map<java.lang.String,java.util.List<com.qq.lol.dto.GameScoreInfoDto>>
     * @throws
     * @Auther: null
     * @Date: 2023/12/4 - 14:24
     */
    Map<String, List<GameScoreInfoDto>> getRecentSixRankSOLOScoresInfo();

    /**
     * @Description: 通过puuid获取 玩家近期 20场战绩中前 6场单排战绩
     * @param puuid: puuid
     * @return java.util.List<com.qq.lol.dto.GameScoreInfoDto>
     * @throws
     * @Auther: null
     * @Date: 2023/12/4 - 16:48
     */
    List<GameScoreInfoDto> getRecentSixRankSOLOScoreInfo(String puuid);

    /**
     * @Description: 获取十个玩家近期 20场战绩中前 6场匹配战绩
     * @return java.util.Map<java.lang.String,java.util.List<com.qq.lol.dto.GameScoreInfoDto>>
     * @throws
     * @Auther: null
     * @Date: 2023/12/4 - 14:23
     */
    Map<String, List<GameScoreInfoDto>> getRecentSixNormalScoresInfo();

    /**
     * @Description: 通过puuid获取玩家近期 20场战绩中前 6场匹配战绩
     * @return java.util.List<com.qq.lol.dto.GameScoreInfoDto>
     * @throws
     * @Auther: null
     * @Date: 2023/12/4 - 16:49
     */
    List<GameScoreInfoDto> getRecentSixNormalScoreInfo(String puuid);

    /**
     * @Description: 获取十个玩家近期 20场战绩中前 6场大乱斗战绩
     * @return java.util.Map<java.lang.String,java.util.List<com.qq.lol.dto.GameScoreInfoDto>>
     * @throws
     * @Auther: null
     * @Date: 2023/12/4 - 14:23
     */
    Map<String, List<GameScoreInfoDto>> getRecentSixARAMScoresInfo();

    /**
     * @Description: 通过puuid获取玩家近期 20场战绩中前 6场大乱斗战绩
     * @return java.util.List<com.qq.lol.dto.GameScoreInfoDto>
     * @throws
     * @Auther: null
     * @Date: 2023/12/4 - 16:50
     */
    List<GameScoreInfoDto> getRecentSixARAMScoreInfo(String puuid);

    /**
     * @Description: 通过 puuid查询玩家近期 20 场战绩
     * @param puuid: puuid
     * @return java.util.List<com.qq.lol.dto.GameScoreInfoDto>
     * @throws
     * @Auther: null
     * @Date: 2023/12/4 - 14:22
     */
    List<GameScoreInfoDto> getRecentTwentyScoreInfoByPuuid(String puuid);

    /**
     * @Description: 通过 puuid查询玩家战绩（所有模式）
     * @param puuid: puuid
     * @param begIndex: 0代表最近一场
     * @param endIndex: 截止到第几场战绩（不包含）
     * @return java.util.List<com.qq.lol.dto.GameScoreInfoDto>
     * @throws
     * @Auther: null
     * @Date: 2023/12/4 - 14:21
     */
    List<GameScoreInfoDto> getScoreInfoByPuuid(String puuid, int begIndex, int endIndex);
}
