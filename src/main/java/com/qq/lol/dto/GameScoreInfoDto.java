package com.qq.lol.dto;

import com.qq.lol.enums.GameMode;
import lombok.Data;

/**
 * @Date: 2023/11/29 - 11 - 29 - 18:21
 * @Description: 战绩
 * @version: 1.0
 */
@Data
public class GameScoreInfoDto {
    // 游戏模式
    private GameMode gameMode;

    private String gameModeName;

    // 谁的战绩
    private String puuid;

    // 游戏队列 id，用于区分是什么游戏模式
    private String queueId;

    private String queueName;

    private String championId;

    // 所用英雄
    private HeroDto hero;

    // 游戏开始时间（UTC） + 8 = 北京时间
    private String gameCreationDate;

    // 游戏持续时间，秒，/60  %60可得到分钟秒数
    private Integer gameDuration;
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

    // 服务器所在地区
    private String platformId;

    // 红蓝方，红 200 ，蓝 100, 0未知
    private String teamId;

}
