package com.qq.lol.app.services.Impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qq.lol.app.services.LolHeroService;
import com.qq.lol.app.services.LolPlayerService;
import com.qq.lol.app.services.RoomService;
import com.qq.lol.dto.*;
import com.qq.lol.enums.GameQueueType;
import com.qq.lol.enums.RankTier;
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
 * @Date: 2023/12/1 - 12 - 01 - 16:17
 * @Description: TODO
 * @version: 1.0
 */
public class LolPlayerServiceImpl implements LolPlayerService {
    // 饿汉式单例模式
    private static final LolPlayerServiceImpl lolPlayerService = new LolPlayerServiceImpl();
    private final NetRequestUtil netRequestUtil = NetRequestUtil.getNetRequestUtil();
    private final RoomService roomService = RoomServiceImpl.getRoomService();

    private LolPlayerServiceImpl() {}

    public static LolPlayerService getLolPlayerService() {
        return lolPlayerService;
    }

    /**
     * @Description: 获取当前已登录游戏客户端的召唤师信息
     * @return com.qq.lol.dto.SummonerInfoDto
     * @throws
     * @Auther: null
     * @Date: 2023/12/4 - 14:24
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
     * @Description: 通过 puuid获取玩家信息
     * @param puuid:
     * @return com.qq.lol.dto.PlayerInfoDto
     * @throws
     * @Auther: null
     * @Date: 2023/12/4 - 14:27
     */
    @Override
    public PlayerInfoDto getPlayerInfoByPuuid(String puuid) {
        String playerInfoJson = netRequestUtil.doGet("/lol-summoner/v2/summoners/puuid/" + puuid);
        PlayerInfoDto playerInfo = JSON.parseObject(playerInfoJson, PlayerInfoDto.class);

        return playerInfo;
    }

    /**
     * @Description: 获取游戏中十个玩家的信息,不包含战绩
     * 战绩、信息分开获取
     * @return java.util.List<com.qq.lol.dto.PlayerInfoDto>
     * @throws
     * @Auther: null
     * @Date: 2023/12/4 - 14:24
     */
    @Override
    public List<PlayerInfoDto> getPlayersInfoByPuuid() {
        return null;
    }

}
