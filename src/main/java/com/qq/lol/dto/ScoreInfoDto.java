package com.qq.lol.dto;

import lombok.Data;

/**
 * @Date: 2023/11/29 - 11 - 29 - 18:21
 * @Description: 战绩
 * @version: 1.0
 */
@Data
public class ScoreInfoDto {

    // 游戏时间
    private String gameCreationDate;
    /**
     * 击杀
     */
    private Integer kills;
    /**
     * 死亡
     */
    private Integer deaths;
    /**
     * 助攻
     */
    private Integer assists;
    /**
     * 赢了吗?
     */
    private Boolean win;

    // 对局 id
    private String gameId;
}
