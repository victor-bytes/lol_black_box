package com.qq.lol.app.services;

import com.qq.lol.dto.GameRoomInfoDto;
import com.qq.lol.dto.TeamPuuidDto;

/**
 * @Auther: null
 * @Date: 2023/11/30 - 11 - 30 - 13:31
 * @Description: 游戏房间对局信息
 * @version: 1.0
 */
public interface RoomService {

    /**
     * 获取当前游戏房间内两队人的puuid
     */
//    TeamPuuidDto getTeamPuuid();

    /**
     * 获取游戏进行后房间信息
     * @return
     */
    GameRoomInfoDto getRoomInfo();

}
