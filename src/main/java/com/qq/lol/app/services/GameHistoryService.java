package com.qq.lol.app.services;

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

//    /**
//     * @Description: 通过 gameId查询对局信息
//     * @return com.qq.lol.dto.GameScoreInfoDto
//     * @throws
//     * @Auther: null
//     * @Date: 2023/12/4 - 20:48
//     */
//    GameScoreInfoDto getGameScoreById();

    /**
     * @Description: 根据  GameQueueType查询队伍玩家近期 30场战绩
     * @param queueType:
     * @param puuids:
     * @return java.util.Map<java.lang.String,java.util.List<com.qq.lol.dto.GameScoreInfoDto>> 最近 30场战绩中 queueType类型战绩
     * @Auther: null
     * @Date: 2023/12/8 - 16:45
     */
    Map<String, List<GameScoreInfoDto>> recentGameScoreByQueueType(GameQueueType queueType, List<String> puuids);

    /**
     * @Description: 查询队伍五个玩家的近 30场战绩中的匹配战绩
     * @param puuids: 队伍玩家的 puuid
     * @return java.util.Map<java.lang.String,java.util.List<com.qq.lol.dto.GameScoreInfoDto>> 队伍每个玩家最近 30场战绩中的匹配战绩
     *     key：puuid，value：战绩List
     * @Auther: null
     * @Date: 2023/12/8 - 11:17
     */
    Map<String, List<GameScoreInfoDto>> recentNormalScores(List<String> puuids);

    /**
     * @Description: 通过 puuid查询玩家最近 30场匹配绩
     * @param puuid:
     * @return java.util.List<com.qq.lol.dto.GameScoreInfoDto> 玩家最近 30场战绩中的匹配战绩
     * @Auther: null
     * @Date: 2023/12/8 - 15:22
     */
    List<GameScoreInfoDto> recentNormalScore(String puuid);

    /**
     * @Description: 查询队伍五个玩家的近 30场战绩中的大乱斗战绩
     * @param puuids: 队伍玩家的 puuid
     * @return java.util.Map<java.lang.String,java.util.List<com.qq.lol.dto.GameScoreInfoDto>> 队伍每个玩家最近 30场战绩中的大乱斗战绩
     *     key：puuid，value：战绩List
     * @Auther: null
     * @Date: 2023/12/8 - 11:17
     */
    Map<String, List<GameScoreInfoDto>> recentARAMScores(List<String> puuids);

    /**
     * @Description: 通过 puuid查询玩家最近 30场大乱斗战绩
     * @param puuid:
     * @return java.util.List<com.qq.lol.dto.GameScoreInfoDto> 玩家最近 30场战绩中的大乱斗战绩
     * @Auther: null
     * @Date: 2023/12/8 - 15:22
     */
    List<GameScoreInfoDto> recentARAMScore(String puuid);

    /**
     * @Description: 查询队伍五个玩家的近 30场战绩中的单排战绩
     * @param puuids: 队伍玩家的 puuid
     * @return java.util.Map<java.lang.String,java.util.List<com.qq.lol.dto.GameScoreInfoDto>> 队伍每个玩家最近 30场战绩中的单排战绩
     *     key：puuid，value：战绩List
     * @Auther: null
     * @Date: 2023/12/8 - 11:17
     */
    Map<String, List<GameScoreInfoDto>> recentSoloRankScores(List<String> puuids);

    /**
     * @Description: 通过 puuid查询玩家最近 30场单排战绩
     * @param puuid:
     * @return java.util.List<com.qq.lol.dto.GameScoreInfoDto> 玩家最近 30场战绩中的单排战绩
     * @Auther: null
     * @Date: 2023/12/8 - 15:22
     */
    List<GameScoreInfoDto> recentSoloRankScore(String puuid);

    /**
     * @Description: 查询队伍五个玩家最近 30场战绩中的组排战绩
     * @param puuids:
     * @return java.util.Map<java.lang.String,java.util.List<com.qq.lol.dto.GameScoreInfoDto>> 队伍每个玩家最近 30场战绩中的组排战绩
     *     key：puuid，value：战绩List
     * @Auther: null
     * @Date: 2023/12/8 - 12:13
     */
    Map<String, List<GameScoreInfoDto>> recentFlexRankScores(List<String> puuids);

    /**
     * @Description: 通过 puuid查询玩家最近 30场战绩中的组排战绩
     * @param puuid:
     * @return java.util.List<com.qq.lol.dto.GameScoreInfoDto> 玩家最近 30场战绩中的组排战绩
     * @Auther: null
     * @Date: 2023/12/8 - 15:20
     */
    List<GameScoreInfoDto> recentFlexRankScore(String puuid);

    /**
     * @Description: 通过 puuid查询玩家近期 30 场战绩
     * @param puuid: puuid
     * @return java.util.List<com.qq.lol.dto.GameScoreInfoDto> 最近 30场战绩
     * @throws
     * @Auther: null
     * @Date: 2023/12/4 - 14:22
     */
    List<GameScoreInfoDto> recentThirtyScores(String puuid);

    /**
     * @Description: 通过 puuid查询玩家战绩（所有模式）
     * @param puuid: puuid
     * @param begIndex: 0代表最近一场
     * @param endIndex: 截止到第几场战绩（不包含）
     * @return java.util.List<com.qq.lol.dto.GameScoreInfoDto> 所有模式战绩 List
     * @throws
     * @Auther: null
     * @Date: 2023/12/4 - 14:21
     */
    List<GameScoreInfoDto> getScoreInfoByPuuid(String puuid, int begIndex, int endIndex);
}
