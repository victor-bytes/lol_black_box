package com.qq.lol.app.services;

import com.qq.lol.dto.GameRoomInfoDto;

/**
 * @Auther: null
 * @Date: 2023/11/30 - 11 - 30 - 13:31
 * @Description: 游戏房间对局信息
 * @version: 1.0
 */
public interface RoomService {

//    /**
//     * 获取当前游戏房间内两队人的puuid
//     */
//    TeamPuuidDto getTeamPuuid();

    /**
     * @Description: 获取对局开始后房间信息，包含玩家信息（只适配台服、马服）
     * 包含玩家信息
     * @return com.qq.lol.dto.GameRoomInfoDto
     * @Auther: null
     * @Date: 2023/12/5 - 17:48
     */
    GameRoomInfoDto getRoomInfo();

}
