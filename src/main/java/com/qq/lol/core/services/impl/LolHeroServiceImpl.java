package com.qq.lol.core.services.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.qq.lol.core.dao.HeroDao;
import com.qq.lol.core.dao.impl.HeroDaoImpl;
import com.qq.lol.core.services.LolHeroService;
import com.qq.lol.dto.HeroDto;
import com.qq.lol.dto.MasteryChampion;
import com.qq.lol.utils.NetRequestUtil;
import javafx.scene.image.Image;
import okhttp3.Response;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Auther: null
 * @Date: 2023/12/4 - 12 - 04 - 11:41
 * @Description: TODO
 * @version: 1.0
 */
public class LolHeroServiceImpl implements LolHeroService {
    private static final LolHeroServiceImpl lolHeroService = new LolHeroServiceImpl();
    private static final HeroDao heroDao = HeroDaoImpl.getHeroDao();
    private final NetRequestUtil netRequestUtil = NetRequestUtil.getNetRequestUtil();

    private LolHeroServiceImpl() {}

    public static LolHeroService getLolHeroService() {
        return lolHeroService;
    }

    /**
     * @Description: 根据 championId 获取英雄信息
     * @param championId:
     * @return com.qq.lol.dto.HeroDto
     * @Auther: null
     * @Date: 2023/12/6 - 19:12
     */
    @Override
    public HeroDto getHeroInfoByChampionId(String championId) {
        if(championId == null || StringUtils.equals("", championId))
            return new HeroDto();

        HeroDto hero = heroDao.getHeroByChampionId(championId);
        // 设置英雄头像
        hero.setChampionIcon(getChampionIcon(championId));

        return hero;
    }

    /**
     * @Description: 获取英雄头像
     * @param imgId:
     * @return javafx.scene.image.Image
     * @Auther: null
     * @Date: 2023/12/22 - 13:55
     */
    @Override
    public Image getChampionIcon(String imgId) {
        //数据库中没有需要从客户端获取
        Image championIcon = heroDao.getChampionIcon(imgId);
        if(championIcon != null)
            return championIcon;

        Response response = netRequestUtil.doGetV2("/lol-game-data/assets/v1/champion-icons/" + imgId + ".png");
        // 先保存到数据库
        Integer i = heroDao.saveChampionIcon(response.body().byteStream(), imgId);
        if(i == -1) {
            System.out.println("--- 获取英雄头像失败 ---");
            return null;
        }
        // 再获取
        return heroDao.getChampionIcon(imgId);
    }

    /**
     * @Description: 从客户端获取所有英雄基础信息，包含英雄名称和头像 url,保存到数据库
     * @return java.lang.Integer >1英雄信息更新到了数据库，=1英雄数量未改变，<1更新数据库失败
     * @Auther: null
     * @Date: 2023/12/6 - 19:12
     */
    @Override
    public Integer updateHeroes() {
        String json = netRequestUtil.doGet("/lol-game-data/assets/v1/champion-summary.json");
        List<HeroDto> heroes = JSON.parseArray(json).toJavaList(HeroDto.class);

        return heroDao.saveHeroes(heroes);
    }

    /**
     * @Description: 查询玩家精通英雄
     * @param summonerId :
     * @return java.util.List<MasteryChampion> 评分S+、S、7级、6级的英雄
     * @Auther: null
     * @Date: 2023/12/8 - 15:12
     */
    @Override
    public List<MasteryChampion> getMasteryChampion(String summonerId, Integer limit) {
        if(summonerId == null || StringUtils.equals("", summonerId))
            return new ArrayList<>();

        List<MasteryChampion> masteryChampions;
        String json = netRequestUtil.doGet("/lol-collections/v1/inventories/" + summonerId
                + "/champion-mastery/top?limit=" + limit);

        System.out.println(JSON.parseObject(json).getJSONArray("masteries"));

        masteryChampions = JSON.parseObject(json).getJSONArray("masteries")
                .toJavaList(MasteryChampion.class)
                .stream()
                .filter(i -> StringUtils.equals("S+", i.getHighestGrade())
                        || StringUtils.equals("S", i.getHighestGrade())
                        || i.getChampionLevel() == 7
                        || i.getChampionLevel() == 6)
                .collect(Collectors.toList());

        return masteryChampions;
    }

    /**
     * @Description: 查询玩家精通的英雄 ，原来的 api拳头已更新，现在需要 puuid查询精通英雄
     * @param puuid:
     * @return java.util.List<com.qq.lol.dto.MasteryChampion>
     * @Auther: null
     * @Date: 2024/6/5 - 18:06
     */
    @Override
    public List<MasteryChampion> getMasteryChampionV2(String puuid) {
        if(puuid == null || StringUtils.equals("", puuid))
            return new ArrayList<>();

        List<MasteryChampion> masteryChampions;
        String json = netRequestUtil.doGet("/lol-champion-mastery/v1/" + puuid + "/champion-mastery");
        JSONArray jsonArray = JSON.parseArray(json);
        masteryChampions = jsonArray
                .toJavaList(MasteryChampion.class)
                .stream()
                .filter(i -> StringUtils.equals("S+", i.getHighestGrade())
                        || StringUtils.equals("S", i.getHighestGrade())
                        || StringUtils.equals("S-", i.getHighestGrade())
                        || i.getChampionLevel() > 10
                )
                .collect(Collectors.toList());

        return masteryChampions;
    }

    /**
     * @Description: 获取用户已拥有英雄
     * @param summonerId:
     * @return java.util.List<com.qq.lol.dto.HeroDto>
     * @Auther: null
     * @Date: 2023/12/6 - 19:14
     */
    @Override
    public List<HeroDto> getOwnedChampionsBySummonerId(String summonerId) {
        return null;
    }

    /**
     * @Description: 获得已拥有英雄数量
     * @param summonerId:
     * @return java.lang.Integer
     * @Auther: null
     * @Date: 2023/12/6 - 19:14
     */
    @Override
    public Integer getOwnedHeroCount(String summonerId) {
        String json = netRequestUtil.doGet("/lol-champions/v1/inventories/" + summonerId + "/champions-playable-count");
        JSONObject jsonObject = JSON.parseObject(json);

        return jsonObject.getInteger("championsOwned");
    }

    /**
     * @return java.lang.Integer
     * @Description: 获取数据库中英雄总量
     * @Auther: null
     * @Date: 2023/12/28 - 13:23
     */
    @Override
    public Integer getHeroCount() {
        return heroDao.getHeroCount();
    }
}
