package com.qq.lol.app.services;

import com.qq.lol.enums.ClientStatusEnum;


/**
 * @Date: 2023/11/29 - 11 - 29 - 15:57
 * @Description: 客户端 api
 * @version: 1.0
 */
public interface LolClientService {

    /**
     * 获取选英雄房间信息
     */
//    void getSelectingRoomInfo();

    /**
     * 查看客户端当前状态
     * 游戏大厅:None
     * 房间内:Lobby
     * 匹配中:Matchmaking
     * 找到对局:ReadyCheck
     * 选英雄中:ChampSelect
     * 游戏中:InProgress
     * 游戏即将结束:PreEndOfGame
     * 等待结算页面:WaitingForStats
     * 游戏结束:EndOfGame
     * 等待重新连接:Reconnect
     * /lol-gameflow/v1/gameflow-phase
     */
    ClientStatusEnum getClientStatus();

}
