package com.qq.lol.app.services;

import com.qq.lol.dto.SkinDto;

import java.util.List;

/**
 * @Auther: null
 * @Date: 2023/11/30 - 11 - 30 - 9:37
 * @Description: 皮肤相关的接口
 * @version: 1.0
 */
public interface LolSkinsService {

    /**
     * @Description: 获取指定英雄的所有皮肤信息
     * @param puuid:
     * @param championId:
     * @return java.util.List<com.qq.lol.dto.SkinDto>
     * @Auther: null
     * @Date: 2023/12/5 - 17:48
     */
    List<SkinDto> getSkinsByChampionId(String puuid, String championId);

    /**
     * 获取指定皮肤数据
     * @param puuid
     * @param championId
     * @param championSkinId
     * @return
     */
    SkinDto getSkinByChampionSkinId(String puuid, String championId, String championSkinId);

    /**
     * 获取玩家的所有皮肤精简信息
     * @param summonerId
     * @return
     */
    List<SkinDto> getSkinsBySummonerId(String summonerId);
}
