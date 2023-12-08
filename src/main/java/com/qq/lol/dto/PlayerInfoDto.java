package com.qq.lol.dto;

import lombok.Data;

import java.util.List;

/**
 * @Date: 2023/11/29 - 11 - 29 - 16:21
 * @Description: 玩家信息
 * @version: 1.0
 */
@Data
public class PlayerInfoDto {

    private String puuid;

    // 游戏内的名字
    private String gameName;

    // gameName + tagLine = riot id，游戏内显示拳头id?
    private String tagLine;

    private String summonerLevel;

    // 所选英雄编号
    private String championId;

    // 所选英雄
    private HeroDto hero;

    // 头像编号
    private String profileIconId;

    // 预选位置
    private String selectedPosition;

    // summonerId 与 accountId 相同
    private String summonerId;

    // 13.23版本之前没更改的游戏 id
    private String summonerName;

    // 大区
    private String platformId;

    // 玩家精通的英雄
    private List<MasteryChampion> masteryChampion;

}
