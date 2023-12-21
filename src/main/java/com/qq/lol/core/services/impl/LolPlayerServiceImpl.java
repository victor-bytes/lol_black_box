package com.qq.lol.core.services.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qq.lol.core.dao.HeroDao;
import com.qq.lol.core.dao.impl.HeroDaoImpl;
import com.qq.lol.core.services.BlackListService;
import com.qq.lol.core.services.LolHeroService;
import com.qq.lol.core.services.LolPlayerService;
import com.qq.lol.dto.BlackPlayerDto;
import com.qq.lol.dto.PlayerInfoDto;
import com.qq.lol.dto.SummonerInfoDto;
import com.qq.lol.utils.NetRequestUtil;
import javafx.scene.image.Image;
import okhttp3.Response;
import org.apache.commons.lang3.StringUtils;

/**
 * @Auther: null
 * @Date: 2023/12/1 - 12 - 01 - 16:17
 * @Description: TODO
 * @version: 1.0
 */
public class LolPlayerServiceImpl implements LolPlayerService {
    // 饿汉式单例模式
    private static final LolPlayerServiceImpl lolPlayerService = new LolPlayerServiceImpl();
    private static final NetRequestUtil netRequestUtil = NetRequestUtil.getNetRequestUtil();
    private static final LolHeroService lolHeroService = LolHeroServiceImpl.getLolHeroService();
    private static final HeroDao heroDao = HeroDaoImpl.getHeroDao();
    private static final BlackListService blackListService = BlackListServiceImpl.getBlackListService();

    private LolPlayerServiceImpl() {}

    public static LolPlayerService getLolPlayerService() {
        return lolPlayerService;
    }

    /**
     * @Description: 获取当前已登录游戏客户端的召唤师信息
     * 建议使用 com.qq.lol.app.services.GlobalService getLoginSummoner()
     * @return com.qq.lol.dto.SummonerInfoDto
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

        // 添加召唤师头像
        summonerInfo.setProfileIcon(getProfileIcon(summonerInfo.getProfileIconId().toString()));

        return summonerInfo;
    }

    /**
     * @Description: 通过 puuid获取玩家信息
     * @param puuid:
     * @return com.qq.lol.dto.PlayerInfoDto
     * @Auther: null
     * @Date: 2023/12/4 - 14:27
     */
    @Override
    public PlayerInfoDto getPlayerInfoByPuuid(String puuid) {
        if(puuid == null || StringUtils.equals("", puuid))
            return new PlayerInfoDto();

        String playerInfoJson = netRequestUtil.doGet("/lol-summoner/v2/summoners/puuid/" + puuid);
        PlayerInfoDto playerInfo = JSON.parseObject(playerInfoJson, PlayerInfoDto.class);
        // 查询精通英雄
        playerInfo.setMasteryChampion(lolHeroService.getMasteryChampion(playerInfo.getSummonerId()));
        // 是否在黑名单中
        BlackPlayerDto blackPlayerDto = blackListService.inBlackList(puuid);
        playerInfo.setInBlackList(StringUtils.equals(blackPlayerDto.getPuuid(), puuid));

        return playerInfo;
    }

    /**
     * @Description: 获取召唤师头像
     * @param imgId :
     * @return javafx.scene.image.Image
     * @Auther: null
     * @Date: 2023/12/20 - 19:08
     */
    private Image getProfileIcon(String imgId) {
        //数据库中没有需要从客户端获取
        Image profileIcon = heroDao.getProfileIcon(imgId);
        if(profileIcon != null)
            return profileIcon;

        Response response = netRequestUtil.doGetV2("/lol-game-data/assets/v1/profile-icons/" + imgId + ".jpg");
        // 先保存到数据库
        Integer i = heroDao.saveProfileIcon(response.body().byteStream(), imgId);
        if(i == -1) {
            System.out.println("--- 获取召唤师头像失败 ---");
            return null;
        }
        // 再获取
        return heroDao.getProfileIcon(imgId);
    }


}
