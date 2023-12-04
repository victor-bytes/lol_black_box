package com.qq.lol.dto;

import lombok.Data;

import java.util.List;

/**
 * @Date: 2023/11/29 - 11 - 29 - 17:05
 * @Description: 英雄的基本信息，id为 -1 表示未选择英雄
 * @version: 1.0
 */
@Data
public class HeroDto {

    // championId
    private String id;

    private String name;

    // 英雄小名
    private String alias;

    // 英雄头像 url
    private String squarePortraitPath;

    // 英雄定位
//    private List<String> roles;
}
