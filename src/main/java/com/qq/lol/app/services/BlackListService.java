package com.qq.lol.app.services;

import com.qq.lol.dto.BlackPlayerDto;

import java.util.List;

/**
 * @Date: 2023/11/29 - 11 - 29 - 19:08
 * @Description: 黑名单
 * @version: 1.0
 */
public interface BlackListService {

    /**
     * @Description: 将玩家加入黑名单
     * @param player:
     * @return int
     * @throws
     * @Auther: null
     * @Date: 2023/11/29 - 19:25
     */
    Integer addBlackList(BlackPlayerDto player);

    /**
     * @Description: 通过 puuid删除黑名单玩家
     * @param puuid:
     * @return void
     * @throws
     * @Auther: null
     * @Date: 2023/11/29 - 19:41
     */
    Integer removeFromBlackList(String puuid);

    /**
     * @Description: 通过 puuid查询玩家是否在黑名单
     * 不存在返回 null
     * @param puuid:
     * @return com.qq.lol.dto.BlackPlayer
     * @throws
     * @Auther: null
     * @Date: 2023/11/29 - 19:26
     */
    BlackPlayerDto inBlackList(String puuid);

    /**
     * @Description: 分页查询黑名单玩家
     * @return java.util.List<com.qq.lol.dto.BlackPlayer>
     * @throws
     * @Auther: null
     * @Date: 2023/11/29 - 19:33
     */
    List<BlackPlayerDto> selectBlackPlayerByPage(int begin, int limit);
}
