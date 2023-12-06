package com.qq.lol.enums;

import com.google.common.base.Enums;
import org.apache.commons.lang3.StringUtils;

/**
 * @Auther: null
 * @Date: 2023/12/2 - 12 - 02 - 17:27
 * @Description: 队列模式,主要用于区分段位类型
 * @version: 1.0
 */
public enum GameQueueType {
    NORMAL("匹配"),
    RANKED_SOLO_5x5("单双排"),
    RANKED_FLEX_SR("组排"),
    ARAM_UNRANKED_5x5("大乱斗5v5"),
    URF("无限火力"),
    BOT("人机"),
    RANKED_TFT("云顶单排"),
    RANKED_TFT_DOUBLE_UP("云顶双人模式"),
    RANKED_TFT_TURBO("云顶狂暴模式"),
    PRACTICETOOL("自定义"),
    PRACTICE_GAME("训练模式"),
    DEAAULT_TYPE("未知队列模式");

    private String gameQueueType;

    GameQueueType(String gameQueueType) {
        this.gameQueueType = gameQueueType;
    }

    public String getGameQueueTypeMsg() {
        return gameQueueType;
    }

    public static GameQueueType getEnumIfPresent(String enumName) {
        GameQueueType gameQueueType = Enums.getIfPresent(GameQueueType.class, enumName).orNull();
        if(null == gameQueueType)
            return DEAAULT_TYPE;
        else
            return GameQueueType.valueOf(enumName);
    }
}
