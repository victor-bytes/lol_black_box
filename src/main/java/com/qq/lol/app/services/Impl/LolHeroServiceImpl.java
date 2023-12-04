package com.qq.lol.app.services.Impl;

import com.qq.lol.app.services.LolHeroService;
import com.qq.lol.dto.HeroDto;

import java.util.List;

/**
 * @Auther: null
 * @Date: 2023/12/4 - 12 - 04 - 11:41
 * @Description: TODO
 * @version: 1.0
 */
public class LolHeroServiceImpl implements LolHeroService {
    private static final LolHeroServiceImpl lolHeroService = new LolHeroServiceImpl();

    private LolHeroServiceImpl() {}

    public static LolHeroService getLolHeroService() {
        return lolHeroService;
    }

    /**
     * 根据 championId 从数据库中获取英雄信息
     *
     * @param championId
     * @return
     */
    @Override
    public HeroDto getHeroInfoByChampionId(String championId) {
        // 先弄个假数据
        HeroDto heroDto = new HeroDto();
        heroDto.setId(championId);
        heroDto.setAlias("Nunu");
        heroDto.setName("雪原双子");
        heroDto.setSquarePortraitPath("/lol-game-data/assets/v1/champion-icons/20.png");

        return heroDto;
    }

    /**
     * 获取所有英雄基础信息，包含英雄名称和头像 url
     * /lol-game-data/assets/v1/champion-summary.json
     *
     * @return
     */
    @Override
    public List<HeroDto> getHero() {
        return null;
    }

    /**
     * 获取用户已拥有英雄
     *
     * @param summonerId
     */
    @Override
    public List<HeroDto> getOwnedChampionsBySummonerId(String summonerId) {
        return null;
    }

    /**
     * 获得已拥有英雄数量
     *
     * @param summonerId
     * @return
     */
    @Override
    public Integer getOwnedHeroCount(String summonerId) {
        return null;
    }
}
