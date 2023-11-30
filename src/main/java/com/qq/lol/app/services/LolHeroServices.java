package com.qq.lol.app.services;

import com.qq.lol.dto.HeroDto;

import java.util.List;

/**
 * @Date: 2023/11/29 - 11 - 29 - 18:56
 * @Description: 英雄信息 api
 * @version: 1.0
 */
public interface LolHeroServices {
    /**
     * 获取所有英雄基础信息，包含英雄名称和头像 url
     * /lol-game-data/assets/v1/champion-summary.json
     * @return
     */
    List<HeroDto> getHero();

    /**
     * 获取用户已拥有英雄
     */
    List<HeroDto> getChampionsBySummonerId(String summonerId);

    /**
     * 获得已拥有英雄数量
     * @return
     */
    Integer getOwnedHeroCount(String summonerId);
}
