package com.qq.lol.dto;

import lombok.Data;

/**
 * @Auther: null
 * @Date: 2023/12/2 - 12 - 02 - 11:30
 * @Description: 段位实体类
 * @version: 1.0
 */
@Data
public class RankDto {
    // 当前小段位
    private String division;

    // 最高小段位
    private String highestDivision;

    // 最高大段位
    private String highestTier;

    // 是否在打定位赛？
    private boolean isProvisional;

    // 当前胜点
    private long leaguePoints;

    // 负场数量
    private long losses;
//    private String miniSeriesProgress;
//    private String previousSeasonAchievedDivision;
//    private String previousSeasonAchievedTier;
//    private String previousSeasonEndDivision;
//    private String previousSeasonEndTier;
//    private long provisionalGamesRemaining;
//    private long provisionalGameThreshold;

    // 队列类型，用于区分段位类型
    private String queueType;

//    private long ratedRating;
//    private String ratedTier;
    //当前大段位
    private String tier;

    // 连败掉段警告，null则为 没有警告
    private String warnings;

    // 胜场
    private long wins;


}
