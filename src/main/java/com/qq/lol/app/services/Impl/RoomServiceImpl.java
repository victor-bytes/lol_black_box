package com.qq.lol.app.services.Impl;

import com.qq.lol.app.services.RoomService;
import com.qq.lol.dto.GameRoomDto;
import com.qq.lol.dto.TeamPuuidDto;
import com.qq.lol.utils.NetRequestUtil;

/**
 * @Auther: null
 * @Date: 2023/12/3 - 12 - 03 - 11:47
 * @Description: TODO
 * @version: 1.0
 */
public class RoomServiceImpl implements RoomService {
    private final NetRequestUtil netRequestUtil = NetRequestUtil.getNetRequestUtil();

    /**
     * 获取当前游戏房间内两队人的puuid
     */
    @Override
    public TeamPuuidDto getTeamPuuid() {
        return null;
    }

    /**
     * 获取游戏进行后房间信息
     *
     * @return
     */
    @Override
    public GameRoomDto getRoomInfo() {
        return null;
    }
}
