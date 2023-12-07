package com.qq.lol.app.dao;

import com.qq.lol.dto.HeroDto;

import java.util.List;

/**
 * @Auther: null
 * @Date: 2023/12/6 - 12 - 06 - 20:12
 * @Description: 持久化英雄信息
 * @version: 1.0
 */
public interface HeroDao {

    /**
     * @Description: 保存所有英雄信息
     * @return java.lang.Integer >1英雄信息更新到了数据库，=1英雄数量未改变，<1更新数据库失败
     * @Auther: null
     * @Date: 2023/12/6 - 20:19
     */
    Integer saveHeroes(List<HeroDto> heroes);

    /**
     * @Description: 获取数据库中英雄数量
     * @return java.lang.Integer
     * @Auther: null
     * @Date: 2023/12/6 - 20:23
     */
    Integer getHeroCount();

    /**
     * @Description: 根据championId获取英雄信息
     * @param championId:
     * @return com.qq.lol.dto.HeroDto
     * @Auther: null
     * @Date: 2023/12/6 - 20:21
     */
    HeroDto getHeroByChampionId(String championId);


}
