package com.qq.lol.app.services.Impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qq.lol.app.services.LolPlayerService;
import com.qq.lol.app.services.RoomService;
import com.qq.lol.dto.*;
import com.qq.lol.enums.GameQueueType;
import com.qq.lol.enums.RankTier;
import com.qq.lol.utils.NetRequestUtil;
import com.qq.lol.utils.StandardOutTime;
import com.sun.xml.internal.bind.v2.TODO;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Auther: null
 * @Date: 2023/12/1 - 12 - 01 - 16:17
 * @Description: TODO
 * @version: 1.0
 */
public class LolPlayerServiceImpl implements LolPlayerService {
    private final NetRequestUtil netRequestUtil = NetRequestUtil.getNetRequestUtil();
    private final RoomService roomService = new RoomServiceImpl();

//    游戏队列id，用于识别战绩中游戏类型
    private static final Map<Integer, String> gameGueueId = new HashMap<>();

    static {
        /*    NormalQueueID    GameQueueID = 430 // 匹配
    RankSoleQueueID  GameQueueID = 420 // 单排
    RankFlexQueueID  GameQueueID = 440 // 组排
    ARAMQueueID      GameQueueID = 450 // 大乱斗
    BOTSimpleQueueID GameQueueID = 830 // 人机入门
    BOTNoviceQueueID GameQueueID = 840 // 人机新手
    URFQueueID       GameQueueID = 900 // 无限火力
    BOTNormalQueueID GameQueueID = 850 // 人机一般
    GameQueueID = -1   未知类型
    */
        gameGueueId.put(420, "单排");
        gameGueueId.put(430, "匹配");
        gameGueueId.put(440, "组排");
        gameGueueId.put(450, "大乱斗");
        gameGueueId.put(900, "无限火力");
        gameGueueId.put(830, "人机入门");
        gameGueueId.put(840, "人机新手");
        gameGueueId.put(850, "人机一般");
        gameGueueId.put(-1, "未知队列id");
    }

    /**
     * 获取当前已登录游戏客户端的召唤师信息
     *
     * @return
     */
    @Override
    public SummonerInfoDto getCurrentSummoner() {
        String summonerInfoJson = netRequestUtil.doGet("/lol-summoner/v1/current-summoner");
        SummonerInfoDto summonerInfo = JSON.parseObject(summonerInfoJson, SummonerInfoDto.class);
        // 添加隐私状态数据
        String privacyJson = netRequestUtil.doGet("/lol-summoner/v1/current-summoner/profile-privacy");
        JSONObject jsonObject = JSON.parseObject(privacyJson);  // 不指定class就会转成JSONObject
        summonerInfo.setPrivacy(jsonObject.getString("setting"));
        summonerInfo.setEnabledState(jsonObject.getString("enabledState"));

        // 添加大区
        String json = netRequestUtil.doGet("/lol-chat/v1/me");
        JSONObject parseObject = JSON.parseObject(json);
        // 对比 puuid是否与当前已登录用户相同
        String puuid = parseObject.getString("puuid");
        if(StringUtils.equals(puuid, summonerInfo.getPuuid())) {
            // 获取大区 id
            String platformId = parseObject.getString("platformId");
            // 添加大区
            if(StringUtils.equals("TW2", platformId))
                summonerInfo.setPlatformId("TW2");
            else if(StringUtils.equals("HN1", platformId))
                summonerInfo.setPlatformId("艾欧尼亚");
            else
                summonerInfo.setPlatformId("未知大区");
        }

        return summonerInfo;
    }

    /**
     * 通过 puuid获取玩家基本信息
     * /lol-summoner/v2/summoners/puuid/
     *
     * @param puuid
     * @return
     */
    @Override
    public PlayerInfoDto getPlayerInfoByPuuid(String puuid) {
        String playerInfoJson = netRequestUtil.doGet("/lol-summoner/v2/summoners/puuid/" + puuid);
        PlayerInfoDto playerInfo = JSON.parseObject(playerInfoJson, PlayerInfoDto.class);

        return playerInfo;
    }

    /**
     * 获取游戏中十个玩家的信息,不包含战绩
     * 战绩、信息分开获取
     *默认游戏中玩家大区和已登录用户相同
     *      * 否则，应当自动刷新已登录用户
     * @param teamPuuidDto
     * @return
     */
    @Override
    public List<PlayerInfoDto> getAllInGamePlayerInfo(TeamPuuidDto teamPuuidDto) {
        return null;
    }

    /**
     * 获取十个玩家近期 20场战绩中前 6场排位战绩（不区分单排、组排）
     *默认游戏中玩家大区和已登录用户相同
     *      * 否则，应当自动刷新已登录用户
     * @param teamPuuidDto
     * @return
     */
    @Override
    public Map<String, List<GameScoreInfoDto>> getRecentSixRankScoreInfo(TeamPuuidDto teamPuuidDto) {
        return null;
    }

    /**
     * 获取十个玩家近期 20场战绩中前 6场匹配战绩
     *默认游戏中玩家大区和已登录用户相同
     *      * 否则，应当自动刷新已登录用户
     * @param teamPuuidDto
     * @return
     */
    @Override
    public Map<String, List<GameScoreInfoDto>> getRecentSixNormalScoreInfo(TeamPuuidDto teamPuuidDto) {
        return null;
    }

    /**
     * 获取十个玩家近期 20场战绩中前 6场大乱斗战绩
     * 默认游戏中玩家大区和已登录用户相同
     * 否则，应当自动刷新已登录用户
     * @param teamPuuidDto
     * @return
     */
    @Override
    public Map<String, List<GameScoreInfoDto>> getRecentSixARAMScoreInfo(TeamPuuidDto teamPuuidDto) {
        return null;
    }

    /**
     * 通过puuid获取玩家段位
     * /lol-ranked/v1/ranked-stats/
     *
     * @param puuid
     * @return
     */
    @Override
    public List<RankDto> getRankByPuuid(String puuid) {
        String rankedJson = netRequestUtil.doGet("/lol-ranked/v1/ranked-stats/" + puuid);
        List<RankDto> list = new ArrayList<>();

        // 解析段位
        JSONObject jsonObject = JSON.parseObject(rankedJson).getJSONObject("queueMap");
        //单双排
        list.add(LolPlayerServiceImpl.tierToCN(jsonObject, "RANKED_SOLO_5x5"));
        //组排
        list.add(LolPlayerServiceImpl.tierToCN(jsonObject, "RANKED_FLEX_SR"));
        //云顶单排
        list.add(LolPlayerServiceImpl.tierToCN(jsonObject, "RANKED_TFT"));
        // 云顶双人模式
        list.add(LolPlayerServiceImpl.tierToCN(jsonObject, "RANKED_TFT_DOUBLE_UP"));
        // 云顶狂暴模式
        list.add(LolPlayerServiceImpl.tierToCN(jsonObject, "RANKED_TFT_TURBO"));

        return list;
    }

    /**
     * 通过 puuid查询玩家近期 20 场战绩
     * 默认查询登录用户
     * 如果没有则为 null
     * /lol-match-history/v1/products/lol/" + id + "/matches?begIndex=0&endIndex=" + endIndex
     *
     * @param puuid
     * @param endIndex
     */
    @Override
    public List<GameScoreInfoDto> getRecentTwentyScoreInfoByPuuid(String puuid, int endIndex) {
        return null;
    }

    /**
     * 解析段位
     *
     */
    private static RankDto tierToCN(JSONObject jsonObject, String EnumName){
        RankDto rankDto = jsonObject.getObject(EnumName, RankDto.class);

//        从枚举中取出段位和游戏模式中文显示
        // 最高段位
        String highestTier = rankDto.getHighestTier();
        // 没有定级，客户端传来段位为空字符串 ""
        rankDto.setHighestTier(RankTier.getEnumIfPresent(highestTier).getTier());

        // 目前段位
        String tier = rankDto.getTier();
        rankDto.setTier(RankTier.getEnumIfPresent(tier).getTier());

        // 队列类型
        String queueType = rankDto.getQueueType();
        rankDto.setQueueType(GameQueueType.getEnumIfPresent(queueType).getGameQueueType());

        return rankDto;
    }


    /**
     * @Description: 通过 puuid查询玩家战绩（所有模式）
     * @param puuid:
     * @param begIndex: 0代表最近一场
     * @param endIndex: 截止到第几场战绩（包含）
     * @return java.util.List<com.qq.lol.dto.ScoreInfoDto>
     *     包含大区
     * @throws
     * @Auther: null
     * @Date: 2023/11/29 - 19:51
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
            // 格式化时间
            String beijingTime = StandardOutTime.utcToBeijingTime(gameScore.getGameCreationDate());
            gameScore.setGameCreationDate(beijingTime);
            // 再设置 KDA值
            JSONObject participants = game.getJSONArray("participants").getJSONObject(0);
            String championId = participants.getString("championId");
            gameScore.setChampionId(championId);
            // 获取KDA
            JSONObject stats = participants.getJSONObject("stats");
            Integer assists = stats.getInteger("assists");
            Integer deaths = stats.getInteger("deaths");
            Integer kills = stats.getInteger("kills");
            // 是否赢
            Boolean win = stats.getBoolean("win");

            gameScore.setAssists(assists);
            gameScore.setDeaths(deaths);
            gameScore.setKills(kills);
            gameScore.setWin(win);

            // 从枚举中替换中文
//            TODO: 替换中文，并且在GameScoreInfoDto新增queueName championName，其他Dto同理

            return gameScore;
        }).collect(Collectors.toList()); // 再次转成 List

        return collect;
    }


}
