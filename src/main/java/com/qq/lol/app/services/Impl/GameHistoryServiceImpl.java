package com.qq.lol.app.services.Impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qq.lol.app.services.GameHistoryService;
import com.qq.lol.app.services.LolHeroService;
import com.qq.lol.app.services.RoomService;
import com.qq.lol.dto.GameRoomInfoDto;
import com.qq.lol.dto.GameScoreInfoDto;
import com.qq.lol.dto.TeamPuuidDto;
import com.qq.lol.enums.GameMode;
import com.qq.lol.utils.InitGameData;
import com.qq.lol.utils.NetRequestUtil;
import com.qq.lol.utils.StandardOutTime;
import org.apache.commons.lang3.StringUtils;

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
    private final NetRequestUtil netRequestUtil = NetRequestUtil.getNetRequestUtil();
    private static final LolHeroService lolHeroService = LolHeroServiceImpl.getLolHeroService();

    private GameHistoryServiceImpl(){}

    public static GameHistoryService getGameHistoryService(){
        return gameHistoryService;
    }

    /**
     * @return java.util.Map<java.lang.String, java.util.List < com.qq.lol.dto.GameScoreInfoDto>>
     * @throws
     * @Description: 获取十个玩家近期 20场战绩中前 6场组排战绩
     * @Auther: null
     * @Date: 2023/12/4 - 14:24
     */
    @Override
    public Map<String, List<GameScoreInfoDto>> getRecentSixRankFlexScoresInfo(TeamPuuidDto teamPuuidDto) {
        List<String> teamPuuidOne = teamPuuidDto.getTeamPuuidOne();
        List<String> teamPuuidTwo = teamPuuidDto.getTeamPuuidTwo();

        Map<String, List<GameScoreInfoDto>> recentSixRankFlexScoresInfo = new HashMap<>();
        for (String puuid : teamPuuidOne) {
            recentSixRankFlexScoresInfo.put(puuid, getRecentSixRankFlexScoreInfo(puuid));
        }
        for (String puuid : teamPuuidTwo) {
            recentSixRankFlexScoresInfo.put(puuid, getRecentSixRankFlexScoreInfo(puuid));
        }

        return recentSixRankFlexScoresInfo;
    }

    /**
     * @Description: 通过puuid获取 玩家近期 20场战绩中前 6场组排战绩
     * @param puuid : puuid
     * @return java.util.List<com.qq.lol.dto.GameScoreInfoDto>
     * @throws
     * @Auther: null
     * @Date: 2023/12/4 - 16:48
     */
    @Override
    public List<GameScoreInfoDto> getRecentSixRankFlexScoreInfo(String puuid) {
        List<GameScoreInfoDto> twentyScoreInfo = getRecentTwentyScoreInfoByPuuid(puuid);
        return getRecentSixScoreInfo("440",twentyScoreInfo);
    }

    /**
     * @Description: 获取十个玩家近期 20场战绩中前 6场单排战绩
     * @return java.util.Map<java.lang.String,java.util.List<com.qq.lol.dto.GameScoreInfoDto>>
     * @throws
     * @Auther: null
     * @Date: 2023/12/4 - 14:24
     */
    @Override
    public Map<String, List<GameScoreInfoDto>> getRecentSixRankSOLOScoresInfo(TeamPuuidDto teamPuuidDto) {
        List<String> teamPuuidOne = teamPuuidDto.getTeamPuuidOne();
        List<String> teamPuuidTwo = teamPuuidDto.getTeamPuuidTwo();

        Map<String, List<GameScoreInfoDto>> recentSixRankSOLOScoresInfo = new HashMap<>();
        for (String puuid : teamPuuidOne) {
            recentSixRankSOLOScoresInfo.put(puuid, getRecentSixRankSOLOScoreInfo(puuid));
        }
        for (String puuid : teamPuuidTwo) {
            recentSixRankSOLOScoresInfo.put(puuid, getRecentSixRankSOLOScoreInfo(puuid));
        }

        return recentSixRankSOLOScoresInfo;
    }

    /**
     * @Description: 通过puuid获取 玩家近期 20场战绩中前 6场单排战绩
     * @param puuid : puuid
     * @return java.util.List<com.qq.lol.dto.GameScoreInfoDto>
     * @throws
     * @Auther: null
     * @Date: 2023/12/4 - 16:48
     */
    @Override
    public List<GameScoreInfoDto> getRecentSixRankSOLOScoreInfo(String puuid) {
        List<GameScoreInfoDto> twentyScoreInfo = getRecentTwentyScoreInfoByPuuid(puuid);
        return getRecentSixScoreInfo("420",twentyScoreInfo);
    }

    /**
     * @Description: 获取十个玩家近期 20场战绩中前 6场匹配战绩
     * @return java.util.Map<java.lang.String,java.util.List<com.qq.lol.dto.GameScoreInfoDto>>
     * @Auther: null
     * @Date: 2023/12/4 - 14:23
     */
    @Override
    public Map<String, List<GameScoreInfoDto>> getRecentSixNormalScoresInfo(TeamPuuidDto teamPuuidDto) {
        List<String> teamPuuidOne = teamPuuidDto.getTeamPuuidOne();
        List<String> teamPuuidTwo = teamPuuidDto.getTeamPuuidTwo();

        Map<String, List<GameScoreInfoDto>> recentSixNormalScoresInfo = new HashMap<>();
        for (String puuid : teamPuuidOne) {
            recentSixNormalScoresInfo.put(puuid, getRecentSixNormalScoreInfo(puuid));
        }
        for (String puuid : teamPuuidTwo) {
            recentSixNormalScoresInfo.put(puuid, getRecentSixNormalScoreInfo(puuid));
        }

        return recentSixNormalScoresInfo;
    }

    /**
     * @Description: 通过puuid获取玩家近期 20场战绩中前 6场匹配战绩
     * @return java.util.List<com.qq.lol.dto.GameScoreInfoDto>
     * @throws
     * @Auther: null
     * @Date: 2023/12/4 - 16:49
     */
    @Override
    public List<GameScoreInfoDto> getRecentSixNormalScoreInfo(String puuid) {
        List<GameScoreInfoDto> twentyScoreInfo = getRecentTwentyScoreInfoByPuuid(puuid);
        return getRecentSixScoreInfo("430",twentyScoreInfo);
    }

    /**
     * @Description: 获取十个玩家近期 20场战绩中前 6场大乱斗战绩
     * @return java.util.Map<java.lang.String,java.util.List<com.qq.lol.dto.GameScoreInfoDto>>
     * @throws
     * @Auther: null
     * @Date: 2023/12/4 - 14:23
     */
    @Override
    public Map<String, List<GameScoreInfoDto>> getRecentSixARAMScoresInfo(TeamPuuidDto teamPuuidDto) {
        List<String> teamPuuidOne = teamPuuidDto.getTeamPuuidOne();
        List<String> teamPuuidTwo = teamPuuidDto.getTeamPuuidTwo();

        Map<String, List<GameScoreInfoDto>> recentSixARAMScoresInfo = new HashMap<>();
        for (String puuid : teamPuuidOne) {
            recentSixARAMScoresInfo.put(puuid, getRecentSixARAMScoreInfo(puuid));
        }
        for (String puuid : teamPuuidTwo) {
            recentSixARAMScoresInfo.put(puuid, getRecentSixARAMScoreInfo(puuid));
        }

        return recentSixARAMScoresInfo;
    }

    /**
     * @Description: 通过puuid获取玩家近期 20场战绩中前 6场大乱斗战绩
     * 没有6场显示实际场数
     * @return java.util.List<com.qq.lol.dto.GameScoreInfoDto>
     * @Auther: null
     * @Date: 2023/12/4 - 16:50
     */
    @Override
    public List<GameScoreInfoDto> getRecentSixARAMScoreInfo(String puuid) {
        List<GameScoreInfoDto> twentyScoreInfo = getRecentTwentyScoreInfoByPuuid(puuid);
        return getRecentSixScoreInfo("450",twentyScoreInfo);
    }

    /**
     * @Description: 根据 queueid筛选近20场战绩中的前 6场
     * @param queueId:
     * @param recentTwentyScore:
     * @return java.util.List<com.qq.lol.dto.GameScoreInfoDto>
     * @Auther: null
     * @Date: 2023/12/4 - 17:42
     */
    private static List<GameScoreInfoDto> getRecentSixScoreInfo(String queueId,
                                                                List<GameScoreInfoDto> recentTwentyScore) {
        // 过滤战绩
        recentTwentyScore = recentTwentyScore.stream().limit(6).filter(aramScore
                -> StringUtils.equals(queueId, aramScore.getQueueId()))
                .collect(Collectors.toList());

        return recentTwentyScore;
    }

    /**
     * @Description: 通过 puuid查询玩家近期 20 场战绩
     * @param puuid: puuid
     * @return java.util.List<com.qq.lol.dto.GameScoreInfoDto>
     * @Auther: null
     * @Date: 2023/12/4 - 14:22
     */
    @Override
    public List<GameScoreInfoDto> getRecentTwentyScoreInfoByPuuid(String puuid) {
        return getScoreInfoByPuuid(puuid, 0, 19);
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
