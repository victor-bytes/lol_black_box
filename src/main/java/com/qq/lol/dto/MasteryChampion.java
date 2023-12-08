package com.qq.lol.dto;

import lombok.Data;

/**
 * @Auther: null
 * @Date: 2023/12/8 - 12 - 08 - 15:06
 * @Description: 玩家精通的英雄
 * @version: 1.0
 */
@Data
public class MasteryChampion {

    private String championId;

    // 英雄成就等级，最高 7级
    private Integer championLevel;

    // 赛季最高评分 S+ s
    private String highestGrade;
}
