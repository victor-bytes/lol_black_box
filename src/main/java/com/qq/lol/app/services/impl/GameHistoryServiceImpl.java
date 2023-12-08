package com.qq.lol.app.services.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qq.lol.app.services.GameHistoryService;
import com.qq.lol.app.services.LolHeroService;
import com.qq.lol.dto.GameScoreInfoDto;
import com.qq.lol.enums.GameMode;
import com.qq.lol.enums.GameQueueType;
import com.qq.lol.utils.InitGameData;
import com.qq.lol.utils.NetRequestUtil;
import com.qq.lol.utils.StandardOutTime;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Auther: null
 * @Date: 2023/12/4 - 12 - 04 - 16:53
 * @Description: TODO
 * @version: 1.0
 */
public class GameHistoryServiceImpl implements GameHistoryService {
    private static final GameHistoryService gameHistoryService = new GameHistoryServiceImpl();
    private static final NetRequestUtil netRequestUtil = NetRequestUtil.getNetRequestUtil();
    private static final LolHeroService lolHeroService = LolHeroServiceImpl.getLolHeroService();

    private GameHistoryServiceImpl(){}

    public static GameHistoryService getGameHistoryService(){
        return gameHistoryService;
    }

    /**
     * @Description: 根据  GameQueueType查询队伍玩家近期 30场战绩
     * @param queueType :
     * @param puuids    :
     * @return java.util.Map<java.lang.String, java.util.List < com.qq.lol.dto.GameScoreInfoDto>> 最近 30场战绩中 queueType类型战绩
     * @Auther: null
     * @Date: 2023/12/8 - 16:45
     */
    @Override
    public Map<String, List<GameScoreInfoDto>> recentGameScoreByQueueType(GameQueueType queueType, List<String> puuids) {
        if(queueType == null || puuids == null || puuids.size() == 0)
            return new HashMap<>();

        Map<String, List<GameScoreInfoDto>> playerGameScore = new HashMap<>();
        switch (queueType) {
            case NORMAL :
                playerGameScore = recentNormalScores(puuids);
                break;
            case RANKED_SOLO_5x5 :
                playerGameScore = recentSoloRankScores(puuids);
                break;
            case RANKED_FLEX_SR :
                playerGameScore = recentFlexRankScores(puuids);
                break;
            case ARAM_UNRANKED_5x5 :
                playerGameScore = recentARAMScores(puuids);
                break;
        }

        return playerGameScore;
    }

    /**
     * @param puuids : 队伍玩家的 puuid
     * @return java.util.Map<java.lang.String, java.util.List < com.qq.lol.dto.GameScoreInfoDto>> 队伍每个玩家最近 30场战绩中的匹配战绩
     * key：puuid，value：战绩List
     * @Description: 查询队伍五个玩家的近 30场战绩中的匹配战绩
     * @Auther: null
     * @Date: 2023/12/8 - 11:17
     */
    @Override
    public Map<String, List<GameScoreInfoDto>> recentNormalScores(List<String> puuids) {
        if(puuids == null || puuids.size() == 0)
            return new HashMap<>();
        Map<String, List<GameScoreInfoDto>> recentNormalScores = new HashMap<>();
        for (String puuid : puuids) {
            recentNormalScores.put(puuid, recentNormalScore(puuid));
        }

        return recentNormalScores;
    }

    /**
     * @Description: 通过 puuid查询玩家最近 30场匹配绩
     * @param puuid :
     * @return java.util.List<com.qq.lol.dto.GameScoreInfoDto> 玩家最近 30场战绩中的匹配战绩
     * @Auther: null
     * @Date: 2023/12/8 - 15:22
     */
    @Override
    public List<GameScoreInfoDto> recentNormalScore(String puuid) {
        if(puuid == null || StringUtils.equals("", puuid))
            return new ArrayList<>();
        List<GameScoreInfoDto> gameScoreInfoDtos = recentThirtyScores(puuid);

        return gameScoreInfoFilter("430", gameScoreInfoDtos);
    }

    /**
     * @Description: 查询队伍五个玩家的近 30场战绩中的大乱斗战绩
     * @param puuids : 队伍玩家的 puuid
     * @return java.util.Map<java.lang.String, java.util.List < com.qq.lol.dto.GameScoreInfoDto>> 队伍每个玩家最近 30场战绩中的大乱斗战绩
     * key：puuid，value：战绩List
     * @Auther: null
     * @Date: 2023/12/8 - 11:17
     */
    @Override
    public Map<String, List<GameScoreInfoDto>> recentARAMScores(List<String> puuids) {
        if(puuids == null || puuids.size() == 0)
            return new HashMap<>();
        Map<String, List<GameScoreInfoDto>> recentARAMScores = new HashMap<>();
        for (String puuid : puuids) {
            recentARAMScores.put(puuid, recentARAMScore(puuid));
        }

        return recentARAMScores;
    }

    /**
     * @Description: 通过 puuid查询玩家最近 30场大乱斗战绩
     * @param puuid :
     * @return java.util.List<com.qq.lol.dto.GameScoreInfoDto> 玩家最近 30场战绩中的大乱斗战绩
     * @Auther: null
     * @Date: 2023/12/8 - 15:22
     */
    @Override
    public List<GameScoreInfoDto> recentARAMScore(String puuid) {
        if(puuid == null || StringUtils.equals("", puuid))
            return new ArrayList<>();
        List<GameScoreInfoDto> gameScoreInfoDtos = recentThirtyScores(puuid);

        return gameScoreInfoFilter("450", gameScoreInfoDtos);
    }

    /**
     * @Description: 查询队伍五个玩家的近 30场战绩中的单排战绩
     * @param puuids : 队伍玩家的 puuid
     * @return java.util.Map<java.lang.String, java.util.List < com.qq.lol.dto.GameScoreInfoDto>> 队伍每个玩家最近 30场战绩中的单排战绩
     * key：puuid，value：战绩List
     * @Auther: null
     * @Date: 2023/12/8 - 11:17
     */
    @Override
    public Map<String, List<GameScoreInfoDto>> recentSoloRankScores(List<String> puuids) {
        if(puuids == null || puuids.size() == 0)
            return new HashMap<>();
        Map<String, List<GameScoreInfoDto>> recentSoloRankScores = new HashMap<>();
        for (String puuid : puuids) {
            recentSoloRankScores.put(puuid, recentSoloRankScore(puuid));
        }

        return recentSoloRankScores;
    }

    /**
     * @Description: 通过 puuid查询玩家最近 30场单排战绩
     * @param puuid :
     * @return java.util.List<com.qq.lol.dto.GameScoreInfoDto> 玩家最近 30场战绩中的单排战绩
     * @Auther: null
     * @Date: 2023/12/8 - 15:22
     */
    @Override
    public List<GameScoreInfoDto> recentSoloRankScore(String puuid) {
        if(puuid == null || StringUtils.equals("", puuid))
            return new ArrayList<>();
        List<GameScoreInfoDto> gameScoreInfoDtos = recentThirtyScores(puuid);

        return gameScoreInfoFilter("420", gameScoreInfoDtos);
    }

    /**
     * @Description: 通过 puuid查询玩家最近 30场战绩中的组排战绩
     * @param puuids : 队伍玩家的 puuid
     * @return java.util.Map<java.lang.String, java.util.List < com.qq.lol.dto.GameScoreInfoDto>>
     * key：puuid，value：10场战绩
     * @Auther: null
     * @Date: 2023/12/8 - 11:17
     */
    @Override
    public Map<String, List<GameScoreInfoDto>> recentFlexRankScores(List<String> puuids) {
        if(puuids == null || puuids.size() == 0)
            return new HashMap<>();

        Map<String, List<GameScoreInfoDto>> recentFlexRankScores = new HashMap<>();
        for (String puuid : puuids) {
            recentFlexRankScores.put(puuid, recentFlexRankScore(puuid));
        }

        return recentFlexRankScores;
    }

    /**
     * @Description: 通过 puuid查询玩家最近 30场战绩中的组排战绩
     * @param puuid :
     * @return java.util.List<com.qq.lol.dto.GameScoreInfoDto> 玩家最近 30场战绩中的组排战绩
     * @Auther: null
     * @Date: 2023/12/8 - 15:20
     */
    @Override
    public List<GameScoreInfoDto> recentFlexRankScore(String puuid) {
        if(puuid == null || StringUtils.equals("", puuid))
            return new ArrayList<>();
        List<GameScoreInfoDto> gameScoreInfoDtos = recentThirtyScores(puuid);

        return gameScoreInfoFilter("440", gameScoreInfoDtos);
    }

    /**
     * @Description: 根据 queueId过滤战绩
     * @param queueId:
     * "420", "单排"
     * "430", "匹配"
     * "440", "组排"
     * "450", "大乱斗"
     * @param gameScore:
     * @return java.util.List<com.qq.lol.dto.GameScoreInfoDto>
     * @Auther: null
     * @Date: 2023/12/8 - 15:49
     */
    private static List<GameScoreInfoDto> gameScoreInfoFilter(String queueId, List<GameScoreInfoDto> gameScore) {
        if(queueId == null || StringUtils.equals("", queueId) || gameScore == null || gameScore.size() == 0)
            return new ArrayList<>();

        // 过滤战绩
        gameScore = gameScore.stream().filter(score
                -> StringUtils.equals(queueId, score.getQueueId()))
                .collect(Collectors.toList());

        return gameScore;
    }

    /**
     * @Description: 通过 puuid查询玩家近期 30 场战绩
     * @param puuid: puuid
     * @return java.util.List<com.qq.lol.dto.GameScoreInfoDto>
     * @Auther: null
     * @Date: 2023/12/4 - 14:22
     */
    @Override
    public List<GameScoreInfoDto> recentThirtyScores(String puuid) {
        if(puuid == null || StringUtils.equals("", puuid))
            return new ArrayList<>();

        return getScoreInfoByPuuid(puuid, 0, 29);
    }

    /**
     * @Description: 通过 puuid查询玩家战绩（所有模式）
     * @param puuid: puuid
     * @param begIndex: 0代表最近一场
     * @param endIndex: 截止到第几场战绩（不包含）
     * @return java.util.List<com.qq.lol.dto.GameScoreInfoDto>
     * @Auther: null
     * @Date: 2023/12/4 - 14:21
     */
    @Override
    public List<GameScoreInfoDto> getScoreInfoByPuuid(String puuid, int begIndex, int endIndex) {
        if(puuid == null || StringUtils.equals("", puuid))
            return new ArrayList<>();

        List<GameScoreInfoDto> collect;

        String json = netRequestUtil.doGet("/lol-match-history/v1/products/lol/"
                + puuid + "/matches?begIndex=" + begIndex + "&endIndex=" + endIndex);
        // 解析 json
        JSONObject jsonObject = JSON.parseObject(json);
        List<JSONObject> jsonList = jsonObject.getJSONObject("games").getJSONArray("games")
                // 转成 Java的 List
                .toJavaList(JSONObject.class);
        // 再使用 stream进一步处理
        collect = jsonList.stream().map(game -> {
            GameScoreInfoDto gameScore = game.toJavaObject(GameScoreInfoDto.class);
            // 设置 puuid
            gameScore.setPuuid(puuid);
            // 格式化时间
            String beijingTime = StandardOutTime.utcToBeijingTime(gameScore.getGameCreationDate());
            gameScore.setGameCreationDate(beijingTime);
            // 设置 gameModeName
            String gameMode = game.getString("gameMode");
            gameScore.setGameModeName(GameMode.getEnumIfPresent(gameMode).getGameModeMsg());

            JSONObject participants = game.getJSONArray("participants").getJSONObject(0);

            // 获取KDA
            JSONObject stats = participants.getJSONObject("stats");
            Integer assists = stats.getInteger("assists");
            Integer deaths = stats.getInteger("deaths");
            Integer kills = stats.getInteger("kills");
            gameScore.setAssists(assists);
            gameScore.setDeaths(deaths);
            gameScore.setKills(kills);
            // 是否赢
            Boolean win = stats.getBoolean("win");
            gameScore.setWin(win);

            // 设置queueName
            gameScore.setQueueName(InitGameData.gameQueueIdToName.get(gameScore.getQueueId()));
            // 设置championId、hero和 teamId
            String championId = participants.getString("championId");
            gameScore.setChampionId(championId);
            gameScore.setHero(lolHeroService.getHeroInfoByChampionId(championId));
            gameScore.setTeamId(participants.getString("teamId"));

            return gameScore;
        }).collect(Collectors.toList()); // 再次转成 List

        return collect;
    }
}
