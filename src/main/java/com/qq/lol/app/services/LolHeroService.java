package com.qq.lol.app.services;

import com.qq.lol.dto.HeroDto;

import java.util.List;

/**
 * @Date: 2023/11/29 - 11 - 29 - 18:56
 * @Description: 英雄信息 api
 * @version: 1.0
 */
public interface LolHeroService {

    /**
     * @Description: 根据 championId 获取英雄信息
     * @param championId:
     * @return com.qq.lol.dto.HeroDto
     * @Auther: null
     * @Date: 2023/12/6 - 19:12
     */
    HeroDto getHeroInfoByChampionId(String championId);

    /**
     * @Description: 从客户端获取所有英雄基础信息，包含英雄名称和头像 url
     * 保存到数据库
     * @return java.util.List<com.qq.lol.dto.HeroDto> >1英雄信息更新到了数据库，=1英雄数量未改变，<1更新数据库失败
     * @Auther: null
     * @Date: 2023/12/6 - 19:12
     */
    Integer updateHeroes();

    /**
     * @Description: 获取用户已拥有英雄
     * @param summonerId:
     * @return java.util.List<com.qq.lol.dto.HeroDto>
     * @Auther: null
     * @Date: 2023/12/6 - 19:14
     */
    List<HeroDto> getOwnedChampionsBySummonerId(String summonerId);

    /**
     * @Description: 获得已拥有英雄数量
     * @param summonerId:
     * @return java.lang.Integer
     * @Auther: null
     * @Date: 2023/12/6 - 19:14
     */
    Integer getOwnedHeroCount(String summonerId);
}
