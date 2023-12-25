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

    public PlayerInfoDto(String puuid, String gameName, String tagLine, String summonerLevel
            , String championId, HeroDto hero, String profileIconId, String selectedPosition
            , String summonerId, String summonerName, String platformId) {
        this.puuid = puuid;
        this.gameName = gameName;
        this.tagLine = tagLine;
        this.summonerLevel = summonerLevel;
        this.championId = championId;
        this.hero = hero;
        this.profileIconId = profileIconId;
        this.selectedPosition = selectedPosition;
        this.summonerId = summonerId;
        this.summonerName = summonerName;
        this.platformId = platformId;
    }

    public PlayerInfoDto() { }

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

    // 是否在黑名单中
    private Boolean inBlackList;

}
