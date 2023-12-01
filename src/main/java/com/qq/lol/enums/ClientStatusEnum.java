package com.qq.lol.enums;

/**
 * 客户端状态枚举
 *
 */
public enum ClientStatusEnum {
    None("客户端大厅中"),
    Lobby("游戏房间内"),
    Matchmaking("正在排队"),
    ReadyCheck("找到对局"),
    ChampSelect("英雄选择中"),
    InProgress("游戏中"),
    PreEndOfGame("游戏即将结束"),
    WaitingForStats("等待结算"),
    EndOfGame("游戏结束"),
    Reconnect("等待重新连接");

    private String clientStatusMsg;

    ClientStatusEnum(String clientStatusMsg) {
        this.clientStatusMsg = clientStatusMsg;
    }

    public String getClientStatusMsg() {
        return this.clientStatusMsg;
    }



}
