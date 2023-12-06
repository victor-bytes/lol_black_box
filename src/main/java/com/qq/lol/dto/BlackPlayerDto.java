package com.qq.lol.dto;

import lombok.Data;

import java.sql.Timestamp;


/**
 * @Date: 2023/11/29 - 11 - 29 - 19:14
 * @Description: 拉黑的玩家
 * @version: 1.0
 */
@Data
public class BlackPlayerDto {

    // 主键
    private String id;

    private String puuid;

    private String gameName;

    private String tagLine;

    private String championId;

    private String championName;

    // 默认预选位置，加入黑名单时应当可以改
    private String selectedPosition;

    private Integer kills;

    private Integer deaths;

    private Integer assists;

    // 1 赢 0 输
    private Integer win;

    // 拉黑原因
    private String reason;

    // 对局 id
    private String gameId;

    private String platformId;

    // 遇见次数
    private String meetCount;

    // 是否双排
    private String isPlayWithFriend;

    // 双排着的 puuid
    private String friendPuuid;

    //拉黑时间
    private Timestamp created_at;

    // 最近一次遇到时间
    private Timestamp last_update_time;

}
