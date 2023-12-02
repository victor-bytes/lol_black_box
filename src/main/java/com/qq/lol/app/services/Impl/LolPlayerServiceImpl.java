package com.qq.lol.app.services.Impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qq.lol.app.services.LolPlayerService;
import com.qq.lol.dto.*;
import com.qq.lol.enums.GameQueueType;
import com.qq.lol.enums.RankTier;
import com.qq.lol.utils.NetRequestUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: null
 * @Date: 2023/12/1 - 12 - 01 - 16:17
 * @Description: TODO
 * @version: 1.0
 */
public class LolPlayerServiceImpl implements LolPlayerService {
    private NetRequestUtil netRequestUtil = NetRequestUtil.getNetRequestUtil();

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
     * 获取游戏中十个玩家的信息
     *
     * @return
     */
    @Override
    public PlayerInfoDto getAllInGamePlayerInfo() {
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
     * 解析段位
     *
     */
    private static RankDto tierToCN(JSONObject jsonObject, String EnumName){
        RankDto rankDto = jsonObject.getObject(EnumName, RankDto.class);

//        从枚举中取出段位和游戏模式中文显示
        // 最高段位
        String highestTier = rankDto.getHighestTier();
        // 没有顶级，客户端传来段位为空字符串 ""
        if("".equals(highestTier))
            rankDto.setHighestTier(RankTier.valueOf("NONE").getTier());
        else
            rankDto.setHighestTier(RankTier.valueOf(highestTier).getTier());
        // 目前段位
        String tier = rankDto.getTier();
        if("".equals(tier))
            rankDto.setTier(RankTier.valueOf("NONE").getTier());
        else
            rankDto.setTier(RankTier.valueOf(tier).getTier());
        // 队列类型
        String queueType = rankDto.getQueueType();
        rankDto.setQueueType(GameQueueType.valueOf(queueType).getGameQueueType());

        return rankDto;
    }

    /**
     * 通过 puuid查询玩家近期 20 场游戏中的前 6 场排位战绩，
     * 如果没有则为 null
     * /lol-match-history/v1/products/lol/" + id + "/matches?begIndex=0&endIndex=" + endIndex
     *
     * @param puuid
     * @param endIndex
     */
    @Override
    public List<GameScoreInfoDto> getRecentScoreInfoByPuuid(String puuid, int endIndex) {
        return null;
    }

    /**
     * @param puuid    :
     * @param begIndex :
     * @param endIndex :
     * @return java.util.List<com.qq.lol.dto.ScoreInfoDto>
     * 包含大区
     * @throws
     * @Description: 通过 puuid查询玩家近 20 场战绩（所有模式）
     * @Auther: null
     * @Date: 2023/11/29 - 19:51
     */
    @Override
    public List<GameScoreInfoDto> getScoreInfoByPuuid(String puuid, int begIndex, int endIndex) {
        return null;
    }

    /**
     * 获取当前游戏两队人的puuid
     */
    @Override
    public TeamPuuidDto getTeamPuuid() {
        return null;
    }
}
