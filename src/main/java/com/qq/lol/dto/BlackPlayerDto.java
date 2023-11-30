package com.qq.lol.dto;

import lombok.Data;

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

    private String heroName;

    private String selectedPosition;

    private String kda;

    // 拉黑原因
    private String reason;

    // 对局 id
    private String gameId;

    // 遇见次数
    private String meetCount;
}
