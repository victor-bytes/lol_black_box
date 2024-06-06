package com.qq.lol.core.services;

import com.qq.lol.dto.BlackPlayerDto;
import com.qq.lol.dto.PageResult;

/**
 * @Date: 2023/11/29 - 11 - 29 - 19:08
 * @Description: 黑名单
 * @version: 1.0
 */
public interface BlackListService {

    /**
     * @Description: 修改黑名单中玩家信息
     * @param player:
     * @return java.lang.Integer返回 1 成功，0 失败
     * @Auther: null
     * @Date: 2023/12/7 - 18:25
     */
    Integer updateBlackPlayer(BlackPlayerDto player);

    /**
     * @Description: 将玩家加入黑名单
     * @param player:
     * @return  =2添加成功，否则添加失败
     * @throws
     * @Auther: null
     * @Date: 2023/11/29 - 19:25
     */
    Integer addBlackList(BlackPlayerDto player);

    /**
     * @Description: 通过 puuid删除黑名单玩家
     * @param puuid:
     * @return  >1移除成功，<=1移除失败
     * @throws
     * @Auther: null
     * @Date: 2023/11/29 - 19:41
     */
    Integer removeFromBlackList(String puuid);

    /**
     * @Description: 通过 puuid查询玩家是否在黑名单
     * 不存在返回 null
     * @param puuid:
     * @return com.qq.lol.dto.BlackPlayer 若在黑名单中，则返回 BlackPlayerDto
     * @throws
     * @Auther: null
     * @Date: 2023/11/29 - 19:26
     */
    BlackPlayerDto inBlackList(String puuid);

    /**
     * @Description: 分页查询黑名单玩家
     * @param pageNo: 第几页
     * @param pageSize: 每页数量
     * @return java.util.List<com.qq.lol.dto.BlackPlayer>
     * @throws
     * @Auther: null
     * @Date: 2023/11/29 - 19:33
     */
    PageResult<BlackPlayerDto> selectBlackPlayerByPage(long pageNo, long pageSize);
}
