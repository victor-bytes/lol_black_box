package com.qq.lol.dto;

import javafx.scene.image.Image;
import lombok.Data;

/**
 * @Date: 2023/11/29 - 11 - 29 - 17:05
 * @Description: 英雄的基本信息，id为 -1 表示未选择英雄
 * @version: 1.0
 */
@Data
public class HeroDto {
    // 数据库主键
    private Integer dbId;

    // championId
    private String id;

    private String name;

    // 英雄小名
    private String alias;

    // 英雄头像 url
    private String squarePortraitPath;

    private Image championIcon;

    // 英雄定位
//    private List<String> roles;
}
