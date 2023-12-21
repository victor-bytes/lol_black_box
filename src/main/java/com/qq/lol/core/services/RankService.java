package com.qq.lol.core.services;

import com.qq.lol.dto.RankDto;

import java.util.List;

/**
 * @Auther: null
 * @Date: 2023/12/4 - 12 - 04 - 16:53
 * @Description: 段位
 * @version: 1.0
 */
public interface RankService {
    /**
     * @Description: 通过puuid获取玩家段位
     * @param puuid: puuid
     * @return java.util.List<com.qq.lol.dto.RankDto>
     * @Auther: null
     * @Date: 2023/12/4 - 14:23
     */
    List<RankDto> getRankByPuuid(String puuid);
}
