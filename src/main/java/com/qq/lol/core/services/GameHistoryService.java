package com.qq.lol.core.services;

import com.qq.lol.dto.GameScoreInfoDto;
import com.qq.lol.enums.GameQueueType;

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
     * @Description: 根据  GameQueueType查询队伍玩家近期战绩
     * @param queueType:
     * @param size: 查询场数
     * @param puuids: 队伍 puuid的 List
     * @return java.util.Map<java.lang.String,java.util.List<com.qq.lol.dto.GameScoreInfoDto>> 除了大乱斗，排位，匹配，其他queueType只返回未筛选的所有类型战绩
     * @Auther: null
     * @Date: 2023/12/21 - 15:33
     */
    Map<String, List<GameScoreInfoDto>> recentGameScoreByQueueType(GameQueueType queueType, Integer size, List<String> puuids);

    /**
     * @Description: 通过 queueId查询队伍玩家最近战绩
     * @param puuids: 队伍 puuid
     * @param queueId: 队列类型
     * @param size: 查询场数
     * @return java.util.Map<java.lang.String,java.util.List<com.qq.lol.dto.GameScoreInfoDto>>
     * @Auther: null
     * @Date: 2023/12/14 - 17:55
     */
    Map<String, List<GameScoreInfoDto>> recentScores(List<String> puuids, String queueId, Integer size);

    /**
     * @Description: 通过 queueId查询玩家最近战绩
     * @param puuid:
     * @param queueId: 队列类型
     * @param size: 查询场数
     * @return java.util.List<com.qq.lol.dto.GameScoreInfoDto>
     * @Auther: null
     * @Date: 2023/12/14 - 17:53
     */
    List<GameScoreInfoDto> recentScores(String puuid, String queueId, Integer size);

    /**
     * @Description: 查询玩家近期 size 场战绩
     * @param puuid:
     * @param size: 要查询的场数
     * @return java.util.List<com.qq.lol.dto.GameScoreInfoDto>
     * @Auther: null
     * @Date: 2023/12/14 - 16:38
     */
    List<GameScoreInfoDto> recentScores(String puuid, Integer size);

    /**
     * @Description: 查询玩家战绩（所有模式）
     * @param puuid: puuid
     * @param begIndex: 0代表最近一场
     * @param endIndex: 截止到第几场战绩（不包含）
     * @return java.util.List<com.qq.lol.dto.GameScoreInfoDto> 所有模式战绩 List
     * @throws
     * @Auther: null
     * @Date: 2023/12/4 - 14:21
     */
    List<GameScoreInfoDto> getAllTypeScore(String puuid, int begIndex, int endIndex);
}
