package com.qq.lol.dto;

import lombok.Data;

/**
 * @Date: 2023/11/29 - 11 - 29 - 16:01
 * @Description: 召唤师信息
 * @version: 1.0
 */
@Data
public class SummonerInfoDto {

    // TW2台服，HN1艾欧尼亚
    private String platformId;

    private String puuid;

    private String accountId;

    // 游戏中显示的 id，就是现在的 riot id（不显示 tagLine）
    private String gameName;

    // 13.23版本之前没更改的游戏 id
    private String displayName;

    // summonerId 与 accountId 相同
    private String summonerId;

    // LOL 等级
    private String summonerLevel;

    // 升到下一级已经获得的经验值百分比
    private String percentCompleteForNextLevel;

    // 当前已获得经验值
    private String xpSinceLastLevel;

    // PUBLIC、PRIVATE
    private String privacy;

    // 是否允许设置隐藏个人信息
    private String enabledState;

    // 升到下一级一共需要的经验值
    private String xpUntilNextLevel;

    // riot id后缀标签，13.23版本之前国服过去的基本都是 TW2
    private String tagLine;

}
