package com.qq.lol.enums;

/**
 * @Auther: null
 * @Date: 2023/12/2 - 12 - 02 - 12:53
 * @Description: 游戏模式
 * @version: 1.0
 */
public enum GameMode {

    CLASSIC("经典模式"),
    ARAM("大乱斗"),
    TFT("云顶之弈"),
    URF("无限火力"),
    PRACTICETOOL("自定义"),
    DEFAULT_MODE("未知游戏模式");

    private String gameMode;

    GameMode(String gameMode) {
        this.gameMode = gameMode;
    }

    public String getGameMode() {
        return gameMode;
    }
}
