package com.qq.lol.core.dao;

import com.qq.lol.dto.HeroDto;
import javafx.scene.image.Image;

import java.io.InputStream;
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

    /**
     * @Description: 保存召唤师头像到数据库
     * @param inputStream:
     * @param imgId: 图片名
     * @return java.lang.Integer -1表示保存失败
     * @Auther: null
     * @Date: 2023/12/20 - 17:52
     */
    Integer saveProfileIcon(InputStream inputStream, String imgId);

    /**
     * @Description: 保存英雄头像到数据库
     * @param inputStream:
     * @param imgId:
     * @return java.lang.Integer 返回 -1 失败
     * @Auther: null
     * @Date: 2023/12/20 - 17:59
     */
    Integer saveChampionIcon(InputStream inputStream, String imgId);

    /**
     * @Description: 获取召唤师头像
     * @param imgId:
     * @return javafx.scene.image.Image
     * @Auther: null
     * @Date: 2023/12/20 - 18:36
     */
    Image getProfileIcon(String imgId);

    /**
     * @Description: 获取英雄头像
     * @param imgId:
     * @return javafx.scene.image.Image
     * @Auther: null
     * @Date: 2023/12/20 - 18:37
     */
    Image getChampionIcon(String imgId);


}
