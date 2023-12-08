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
     * 只获取 排位、匹配、大乱斗玩家信息,其他游戏模式获取房间信息，但不获取队伍信息
     * @return com.qq.lol.dto.GameRoomInfoDto 返回 null表明客户端未进入选英雄或游戏阶段，
     * @Auther: null
     * @Date: 2023/12/5 - 17:48
     */
    GameRoomInfoDto getRoomInfo();

}
