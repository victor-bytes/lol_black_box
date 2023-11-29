package com.qq.lol.dto;

import lombok.Data;

/**
 * @Date: 2023/11/29 - 11 - 29 - 12:46
 * @Description: 拳头客户端Dto
 * @version: 1.0
 */
@Data
public class LolClientDto {

    // 客户端端口号
    private String port;

    // 用户登录后客户端的 token
    private String token;
}
