package com.qq.lol.dto;

import lombok.Data;

import java.util.List;

/**
 * @Date: 2023/11/29 - 11 - 29 - 19:01
 * @Description: 红蓝方玩家 puuid
 * @version: 1.0
 */
@Data
public class TeamPuuidDto {
    private List<String> teamPuuidOne;
    private List<String> teamPuuidTwo;
}
