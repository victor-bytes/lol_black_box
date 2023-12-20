package com.qq.lol.app.services;

/**
 * @Auther: null
 * @Date: 2023/12/14 - 12 - 14 - 14:48
 * @Description: 战利品
 * @version: 1.0
 */
public interface LootService {

    /**
     * @Description: 获取当前神话精粹数量
     * @return java.lang.Integer -1获取失败
     * @Auther: null
     * @Date: 2023/12/18 - 12:03
     */
    Integer getMythicCount();

    /**
     * @Description: 神话精粹兑换橙色精粹
     * @param count: 要兑换的神话精粹个数
     * @return java.lang.Integer，返回剩余的神话精粹个数
     * @Auther: null
     * @Date: 2023/12/14 - 14:53
     */
    Integer mythicToOrange(Integer count);

    /**
     * @Description: 神话精粹兑换蓝色精粹
     * @param count: 要兑换的神话精粹个数
     * @return java.lang.Integer 返回剩余的神话精粹个数
     * @Auther: null
     * @Date: 2023/12/14 - 14:54
     */
    Integer mythicToBlue(Integer count);

}
