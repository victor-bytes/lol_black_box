package com.qq.lol.dto;

import lombok.Data;


/**
 * @Date: 2023/11/29 - 11 - 29 - 19:14
 * @Description: 拉黑的玩家
 * @version: 1.0
 */
@Data
public class BlackPlayerDto {

    public BlackPlayerDto() {
    }

    public BlackPlayerDto(String puuid, String gameName, String tagLine, String championId, String gameId, String platformId) {
        this.puuid = puuid;
        this.gameName = gameName;
        this.tagLine = tagLine;
        this.championId = championId;
        this.gameId = gameId;
        this.platformId = platformId;
    }

    // 主键
    private String id;

    private String puuid;

    private String gameName;

    private String tagLine;

    private String championId;

//    private HeroDao hero;

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

    // 是否双排 1双排，0单排
    private String isPlayWithFriend;

    // 双排着的 puuid
    private String friendPuuid;

    //拉黑时间
    private String created_at;

    // 最近一次遇到时间
    private String last_update_time;

}
