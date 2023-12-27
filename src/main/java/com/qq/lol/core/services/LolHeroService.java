package com.qq.lol.core.services;

import com.qq.lol.dto.HeroDto;
import com.qq.lol.dto.MasteryChampion;
import javafx.scene.image.Image;

import java.util.List;

/**
 * @Date: 2023/11/29 - 11 - 29 - 18:56
 * @Description: 英雄信息 api
 * @version: 1.0
 */
public interface LolHeroService {

    /**
     * @Description: 获取英雄头像
     * @param imgId:
     * @return javafx.scene.image.Image
     * @Auther: null
     * @Date: 2023/12/22 - 13:55
     */
    Image getChampionIcon(String imgId);

    /**
     * @Description: 根据 championId 获取英雄信息
     * @param championId:
     * @return com.qq.lol.dto.HeroDto
     * @Auther: null
     * @Date: 2023/12/6 - 19:12
     */
    HeroDto getHeroInfoByChampionId(String championId);

    /**
     * @Description: 从客户端获取所有英雄基础信息，包含英雄名称和头像 url,保存到数据库
     * @return java.util.List<com.qq.lol.dto.HeroDto> >1英雄信息更新到了数据库，=1英雄数量未改变，<1更新数据库失败
     * @Auther: null
     * @Date: 2023/12/6 - 19:12
     */
    Integer updateHeroes();

    /**
     * @Description: 查询玩家精通的十个英雄
     * @param summonerId:
     * @return java.util.List<MasteryChampion> 评分S+、S、7级、6级的前十个英雄，不足返回实际个数
     * @Auther: null
     * @Date: 2023/12/8 - 15:12
     */
    List<MasteryChampion> getMasteryChampion(String summonerId);

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
