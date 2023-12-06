package com.qq.lol.dto;

import com.qq.lol.enums.GameMode;
import com.qq.lol.enums.GameQueueType;
import lombok.Data;

import java.util.List;

/**
 * @Auther: null
 * @Date: 2023/12/3 - 12 - 03 - 11:44
 * @Description: 游戏房间信息
 * @version: 1.0
 */
@Data
public class GameRoomInfoDto {
    private String gameId;

    private GameMode gameMode;

    private String gameModeName;

    private GameQueueType gameQueueType;

    private String gameQueueTypeName;

    // gameQueueId
    private String gameQueueId;

    private String gameQueueName;

    // 用于查询十个玩家历史战绩
    private TeamPuuidDto teamPuuidDto;

    // 游戏中双方玩家信息
    private List<PlayerInfoDto> teamOnePlayers;

    private List<PlayerInfoDto> teamTwoPlayers;

}
