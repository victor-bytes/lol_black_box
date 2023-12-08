package com.qq.lol.app.services.impl;

import com.alibaba.fastjson.JSON;
import com.qq.lol.app.dao.HeroDao;
import com.qq.lol.app.dao.impl.HeroDaoImpl;
import com.qq.lol.app.services.LolHeroService;
import com.qq.lol.dto.HeroDto;
import com.qq.lol.dto.MasteryChampion;
import com.qq.lol.utils.NetRequestUtil;
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

        return heroDao.getHeroByChampionId(championId);
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
     * @param summonerId :
     * @return java.util.List<MasteryChampion> 评分S+、S、7级、6级的前十个英雄，不足返回实际个数
     * @Description: 查询玩家精通的十个英雄
     * @Auther: null
     * @Date: 2023/12/8 - 15:12
     */
    @Override
    public List<MasteryChampion> getMasteryChampion(String summonerId) {
        if(summonerId == null || StringUtils.equals("", summonerId))
            return new ArrayList<>();

        List<MasteryChampion> masteryChampions;
        String json = netRequestUtil.doGet("/lol-collections/v1/inventories/" + summonerId
                + "/champion-mastery/top?limit=10");
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
        return null;
    }
}
