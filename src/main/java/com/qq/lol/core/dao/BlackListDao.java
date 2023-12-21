package com.qq.lol.core.dao;

import com.qq.lol.dto.BlackPlayerDto;
import com.qq.lol.dto.PageResult;

/**
 * @Auther: null
 * @Date: 2023/12/6 - 12 - 06 - 20:12
 * @Description: 持久化黑名单信息
 * @version: 1.0
 */
public interface BlackListDao {

    /**
     * @Description: 将玩家信息添加到黑名单
     * @param blackPlayer:
     * @return java.lang.Integer =2添加成功，否则添加失败
     * @Auther: null
     * @Date: 2023/12/7 - 13:45
     */
    Integer addBlackList(BlackPlayerDto blackPlayer);

    /**
     * @Description: 查询玩家是否在黑名单中
     * @param puuid:
     * @return com.qq.lol.dto.BlackPlayerDto 若在黑名单中，则返回 BlackPlayerDto
     * @Auther: null
     * @Date: 2023/12/7 - 13:46
     */
    BlackPlayerDto queryPlayerByPuuid(String puuid);

    /**
     * @Description: 将玩家从黑名单移除
     * @param puuid:
     * @return java.lang.Integer >1移除成功，<=1移除失败
     * @Auther: null
     * @Date: 2023/12/7 - 13:47
     */
    Integer removePlayer(String puuid);

    /**
     * @Description: 修改黑名单中玩家信息
     * 只准修改 meetCount、kill、deaths、assists、reason
     * @param blackPlayer:
     * @return java.lang.Integer
     * @Auther: null
     * @Date: 2023/12/7 - 13:50
     */
    Integer updateBlackList(BlackPlayerDto blackPlayer);

    /**
     * @Description: 分页查询所有黑名单玩家信息
     * @param pageNo: 第几页，第一页为 0
     * @param pageSize: 每页数量
     * @return com.qq.lol.dto.PageResult<com.qq.lol.dto.BlackPlayerDto>
     * @Auther: null
     * @Date: 2023/12/7 - 16:59
     */
    PageResult<BlackPlayerDto> queryAllPlayer(long pageNo, long pageSize);
}
