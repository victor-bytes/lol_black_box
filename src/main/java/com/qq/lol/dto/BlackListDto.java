package com.qq.lol.dto;

import javafx.scene.image.ImageView;
import lombok.Data;

/**
 * @Auther: null
 * @Date: 2024/6/4 - 06 - 04 - 18:53
 * @Description: TODO
 * @version: 1.0
 */
@Data
public class BlackListDto {
    String puuid;

    Long order;

    String name;

    ImageView icon;

    String kda;

    String reason;

    String meetCount;

    String platformId;

    String selectedPosition;

    String last_update_time;

    Integer win;

    public BlackListDto(){

    }

    public BlackListDto(String puuid, Long order, String name, ImageView icon, String kda, String reason, String meetCount,
                        String platformId, String selectedPosition, String last_update_time, Integer win) {
        this.puuid = puuid;
        this.order = order;
        this.name = name;
        this.icon = icon;
        this.kda = kda;
        this.reason = reason;
        this.meetCount = meetCount;
        this.platformId = platformId;
        this.selectedPosition = selectedPosition;
        this.last_update_time = last_update_time;
        this.win = win;
    }
}
