package com.qq.lol.core.services.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qq.lol.core.services.RankService;
import com.qq.lol.dto.RankDto;
import com.qq.lol.enums.GameQueueType;
import com.qq.lol.enums.RankTier;
import com.qq.lol.utils.NetRequestUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: null
 * @Date: 2023/12/4 - 12 - 04 - 16:55
 * @Description: TODO
 * @version: 1.0
 */
public class RankServiceImpl implements RankService {
    private static final RankService rankService = new RankServiceImpl();
    private static final NetRequestUtil netRequestUtil = NetRequestUtil.getNetRequestUtil();

    private RankServiceImpl(){}

    public static RankService getRankService() {
        return rankService;
    }

    /**
     * @Description: 通过puuid获取玩家段位
     * @param puuid: puuid
     * @return java.util.List<com.qq.lol.dto.RankDto>
     * @Auther: null
     * @Date: 2023/12/4 - 14:23
     */
    @Override
    public List<RankDto> getRankByPuuid(String puuid) {
        if(puuid == null || StringUtils.equals("", puuid))
            return new ArrayList<>();

        String rankedJson = netRequestUtil.doGet("/lol-ranked/v1/ranked-stats/" + puuid);
        List<RankDto> list = new ArrayList<>();

        // 解析段位
        JSONObject jsonObject = JSON.parseObject(rankedJson).getJSONObject("queueMap");
        //单双排
        list.add(tierToCN(jsonObject, "RANKED_SOLO_5x5"));
        //组排
        list.add(tierToCN(jsonObject, "RANKED_FLEX_SR"));
        //云顶单排
        list.add(tierToCN(jsonObject, "RANKED_TFT"));
        // 云顶双人模式
        list.add(tierToCN(jsonObject, "RANKED_TFT_DOUBLE_UP"));
        // 云顶狂暴模式
        list.add(tierToCN(jsonObject, "RANKED_TFT_TURBO"));

        return list;
    }

    /**
     * 解析段位
     *
     */
    private static RankDto tierToCN(JSONObject jsonObject, String EnumName){
        if(jsonObject == null || EnumName == null || StringUtils.equals("", EnumName))
            return new RankDto();

        RankDto rankDto = jsonObject.getObject(EnumName, RankDto.class);

//        从枚举中取出段位和游戏模式中文显示
        // 最高段位
        String highestTier = rankDto.getHighestTier();
        // 没有定级，客户端传来段位为空字符串 ""
        rankDto.setHighestTier(RankTier.getEnumIfPresent(highestTier).getTier());

        // 目前段位
        String tier = rankDto.getTier();
        rankDto.setTier(RankTier.getEnumIfPresent(tier).getTier());

        // 队列类型
        String queueType = rankDto.getQueueType();
        rankDto.setQueueType(GameQueueType.getEnumIfPresent(queueType).getGameQueueTypeMsg());

        return rankDto;
    }
}
