package com.qq.lol.enums;

/**
 * 游戏客户端状态
 *
 */
public enum ClientStatusEnum {
    /*
    GameStatusInQueue        GameStatus = "inQueue"                   // 队列中
     GameStatusInGame         GameStatus = "inGame"                    // 游戏中
     GameStatusChampionSelect GameStatus = "championSelect"            // 英雄选择中
     GameStatusOutOfGame      GameStatus = "outOfGame"                 // 退出游戏中
     GameStatusHostNormal     GameStatus = "hosting_NORMAL"            // 匹配组队中-队长
     GameStatusHostRankSolo   GameStatus = "hosting_RANKED_SOLO_5x5"   // 单排组队中-队长
     GameStatusHostRankFlex   GameStatus = "hosting_RANKED_FLEX_SR"    // 组排组队中-队长
     GameStatusHostARAM       GameStatus = "hosting_ARAM_UNRANKED_5x5" // 大乱斗5v5组队中-队长
     GameStatusHostURF        GameStatus = "hosting_URF"               // 无限火力组队中-队长
     GameStatusHostBOT        GameStatus = "hosting_BOT"               // 人机组队中-队长
     GameFlowChampionSelect   GameFlow   = "ChampSelect"               // 英雄选择中
     GameFlowReadyCheck       GameFlow   = "ReadyCheck"                // 等待接受对局
     GameFlowInProgress       GameFlow   = "InProgress"                // 进行中
     GameFlowMatchmaking      GameFlow   = "Matchmaking"               // 匹配中
     GameFlowNone             GameFlow   = "None"                      // 无
     */

    None("客户端大厅中"),
    Lobby("游戏房间内"),
    Matchmaking("正在排队"),
    ReadyCheck("找到对局"),
    ChampSelect("英雄选择中"),
    InProgress("InProgress游戏中"),
    PreEndOfGame("游戏即将结束"),
    WaitingForStats("等待结算"),
    EndOfGame("游戏结束"),
    hosting_BOT("人机组队中-队长"),
    hosting_URF("无限火力组队中-队长"),
    hosting_ARAM_UNRANKED_5x5("大乱斗5v5组队中-队长"),
    hosting_RANKED_FLEX_SR("组排组队中-队长"),
    hosting_RANKED_SOLO_5x5("单排组队中-队长"),
    hosting_NORMAL("匹配组队中-队长"),
    outOfGame("退出游戏中"),
    inGame("inGame游戏中"),
    inQueue("队列中"),
    Reconnect("等待重新连接"),
    DEFAULT_STATUS("游戏客户端未知状态");

    private String clientStatusMsg;

    ClientStatusEnum(String clientStatusMsg) {
        this.clientStatusMsg = clientStatusMsg;
    }

    public String getClientStatusMsg() {
        return this.clientStatusMsg;
    }



}
