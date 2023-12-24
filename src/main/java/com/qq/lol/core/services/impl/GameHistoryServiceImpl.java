package com.qq.lol.core.services.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qq.lol.core.services.GameHistoryService;
import com.qq.lol.core.services.LolHeroService;
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
import java.util.function.Function;
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
     * @Description: 根据  GameQueueType查询队伍玩家近期战绩
     * @param queueType :
     * @param puuids    :
     * @return java.util.Map<java.lang.String, java.util.List < com.qq.lol.dto.GameScoreInfoDto>>
     * @Auther: null
     * @Date: 2023/12/8 - 16:45
     */
    @Override
    public Map<String, List<GameScoreInfoDto>> recentGameScoreByQueueType(GameQueueType queueType, Integer size, List<String> puuids) {
        if(queueType == null || puuids == null || puuids.size() == 0)
            return new HashMap<>();

        Map<String, List<GameScoreInfoDto>> playerGameScore = new HashMap<>();
        switch (queueType) {
            case NORMAL :
                playerGameScore = recentScores(puuids, "430", size);
                break;
            case RANKED_SOLO_5x5 :
                playerGameScore = recentScores(puuids, "420", size);
                break;
            case RANKED_FLEX_SR :
                playerGameScore = recentScores(puuids, "440", size);
                break;
            case ARAM_UNRANKED_5x5 :
                playerGameScore = recentScores(puuids, "450", size);
                break;
            case DEAAULT_TYPE: // 其他类型不查战绩
                playerGameScore = recentScores(puuids, "-1", size);
                break;
        }

        return playerGameScore;
    }

    /**
     * @Description: 通过 queueId查询玩家最近战绩
     * @param puuids : 队伍玩家的 puuid
     * @return java.util.Map<java.lang.String, java.util.List < com.qq.lol.dto.GameScoreInfoDto>>
     * key：puuid，value：10场战绩
     * @Auther: null
     * @Date: 2023/12/8 - 11:17
     */
    @Override
    public Map<String, List<GameScoreInfoDto>> recentScores(List<String> puuids, String queueId, Integer size) {
        if(puuids == null || puuids.size() == 0)
            return new HashMap<>();

        Map<String, List<GameScoreInfoDto>> recentFlexRankScores = new HashMap<>();
        for (String puuid : puuids) {
            recentFlexRankScores.put(puuid, recentScores(puuid, queueId, size));
        }

        return recentFlexRankScores;
    }

    /**
     * @Description: 通过 queueId查询玩家最近战绩
     * @param puuid :
     * @return java.util.List<com.qq.lol.dto.GameScoreInfoDto>
     * @Auther: null
     * @Date: 2023/12/8 - 15:20
     */
    @Override
    public List<GameScoreInfoDto> recentScores(String puuid, String queueId, Integer size) {
        if(puuid == null || StringUtils.equals("", puuid) || queueId == null || StringUtils.equals("", queueId))
            return new ArrayList<>();

        List<GameScoreInfoDto> gameScoreInfoDtos = recentScores(puuid, size);
        if(gameScoreInfoDtos == null)
            return new ArrayList<>();

        return recentScores(gameScoreInfoDtos, scores -> gameScoreInfoFilter(queueId, gameScoreInfoDtos));
    }

    /**
     * @Description: 过滤战绩
     * @param scores:
     * @param filter: 过滤规则
     * @return java.util.List<com.qq.lol.dto.GameScoreInfoDto>
     * @Auther: null
     * @Date: 2023/12/14 - 17:22
     */
    private List<GameScoreInfoDto> recentScores(List<GameScoreInfoDto> scores, Function<List<GameScoreInfoDto>, List<GameScoreInfoDto>> filter) {
        return filter.apply(scores);
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
     * @Description: 通过 puuid查询玩家近期 size 场战绩
     * @param puuid :
     * @param size  : 要查询的场数
     * @return java.util.List<com.qq.lol.dto.GameScoreInfoDto>
     * @Auther: null
     * @Date: 2023/12/14 - 16:38
     */
    @Override
    public List<GameScoreInfoDto> recentScores(String puuid, Integer size) {
        if(puuid == null || StringUtils.equals("", puuid))
            return new ArrayList<>();

        return getAllTypeScore(puuid, 0, size);
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
    public List<GameScoreInfoDto> getAllTypeScore(String puuid, int begIndex, int endIndex) {
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
