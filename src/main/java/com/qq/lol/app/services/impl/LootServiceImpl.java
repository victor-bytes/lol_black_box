package com.qq.lol.app.services.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qq.lol.app.services.LootService;
import com.qq.lol.utils.NetRequestUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
     * @return java.lang.Integer
     * @Description: 获取当前神话精粹数量
     * @Auther: null
     * @Date: 2023/12/18 - 12:03
     */
    @Override
    public Integer getMythicCount() {
        String json = netRequestUtil.doGet("/lol-loot/v1/player-loot/CURRENCY_mythic");

        Integer count = 0;
        try {
            count = JSON.parseObject(json).getInteger("count");
            return count;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("获取神话精粹异常");
        }

        return -1;
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
            // 构造 body
            List<String> list = new ArrayList<>();
            list.add("CURRENCY_mythic");
            String body = JSON.toJSONString(list);
            String currency_mythic = netRequestUtil
                    .doPost("/lol-loot/v1/recipes/CURRENCY_mythic_forge_15/craft?repeat=" + count, body);

            JSONObject jsonObject = JSON.parseObject(currency_mythic);
            String remainedCount = jsonObject.getJSONArray("removed")
                    .toJavaList(JSONObject.class)
                    .get(0)
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
