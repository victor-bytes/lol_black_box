package com.qq.lol.app.services.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qq.lol.app.services.LootService;
import com.qq.lol.utils.NetRequestUtil;

import java.io.IOException;

/**
 * @Auther: null
 * @Date: 2023/12/14 - 12 - 14 - 14:55
 * @Description: TODO
 * @version: 1.0
 */
public class LootServiceImpl implements LootService {
    private static final NetRequestUtil netRequestUtil = NetRequestUtil.getNetRequestUtil();
    private static final LootService lootService = new LootServiceImpl();

    private LootServiceImpl(){}

    public static LootService getLootService(){
        return lootService;
    }

    /**
     * @param count : 要兑换的神话精粹个数
     * @return java.lang.Integer，返回剩余的神话精粹个数
     * @Description: 神话精粹兑换橙色精粹
     * @Auther: null
     * @Date: 2023/12/14 - 14:53
     */
    @Override
    public Integer mythicToOrange(Integer count) {
        try {
            String currency_mythic = netRequestUtil.doPost("/lol-loot/v1/recipes/CURRENCY_mythic_forge_15/craft?repeat=" + count,
                    "CURRENCY_mythic");
            JSONObject jsonObject = JSON.parseObject(currency_mythic);
            String remainedCount = jsonObject.getJSONArray("removed")
                    .getJSONObject(0)
                    .getJSONObject("playerLoot")
                    .getString("count");

            return Integer.parseInt(remainedCount);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return -1;
    }

    /**
     * @param count : 要兑换的神话精粹个数
     * @return java.lang.Integer 返回剩余的神话精粹个数
     * @Description: 神话精粹兑换蓝色精粹
     * @Auther: null
     * @Date: 2023/12/14 - 14:54
     */
    @Override
    public Integer mythicToBlue(Integer count) {
        return null;
    }
}
