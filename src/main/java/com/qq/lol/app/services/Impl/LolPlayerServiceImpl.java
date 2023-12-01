package com.qq.lol.app.services.Impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qq.lol.app.services.LolPlayerService;
import com.qq.lol.dto.PlayerInfoDto;
import com.qq.lol.dto.ScoreInfoDto;
import com.qq.lol.dto.SummonerInfoDto;
import com.qq.lol.dto.TeamPuuidDto;
import com.qq.lol.utils.NetRequestUtil;

import java.util.List;

/**
 * @Auther: null
 * @Date: 2023/12/1 - 12 - 01 - 16:17
 * @Description: TODO
 * @version: 1.0
 */
public class LolPlayerServiceImpl implements LolPlayerService {
    private NetRequestUtil netRequestUtil = NetRequestUtil.getNetRequestUtil();

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
    public String getRankByPuuid(String puuid) {
        return null;
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
    public List<ScoreInfoDto> getRecentScoreInfoByPuuid(String puuid, int endIndex) {
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
    public List<ScoreInfoDto> getScoreInfoByPuuid(String puuid, int begIndex, int endIndex) {
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
