package com.qq.lol.app.services.impl;

import com.qq.lol.app.dao.BlackListDao;
import com.qq.lol.app.dao.impl.BlackListDaoImpl;
import com.qq.lol.app.services.BlackListService;
import com.qq.lol.dto.BlackPlayerDto;
import com.qq.lol.dto.PageResult;
import org.apache.commons.lang3.StringUtils;

/**
 * @Auther: null
 * @Date: 2023/12/7 - 12 - 07 - 17:28
 * @Description: TODO
 * @version: 1.0
 */
public class BlackListServiceImpl implements BlackListService {
    private static final BlackListDao blackListDao = BlackListDaoImpl.getBlackListDao();
    private static final BlackListService blackListService = new BlackListServiceImpl();

    private BlackListServiceImpl(){}

    public static BlackListService getBlackListService(){return blackListService;}

    /**
     * @Description: 修改黑名单中玩家信息, 返回 1 成功，0 失败
     * @param player :
     * @return java.lang.Integer
     * @Auther: null
     * @Date: 2023/12/7 - 18:25
     */
    @Override
    public Integer updateBlackPlayer(BlackPlayerDto player) {
        if(player == null)
            return 0;

        return blackListDao.updateBlackList(player);
    }

    /**
     * @Description: 将玩家加入黑名单 =2添加成功，否则添加失败
     * @param player :
     * @return int
     * @throws
     * @Auther: null
     * @Date: 2023/11/29 - 19:25
     */
    @Override
    public Integer addBlackList(BlackPlayerDto player) {
        if(player == null)
            return 0;

        return blackListDao.addBlackList(player);
    }

    /**
     * @Description: 通过 puuid删除黑名单玩家 >1移除成功，<=1移除失败
     * @param puuid :
     * @return void
     * @throws
     * @Auther: null
     * @Date: 2023/11/29 - 19:41
     */
    @Override
    public Integer removeFromBlackList(String puuid) {
        if(puuid == null || StringUtils.equals(puuid, ""))
            return 0;

        return blackListDao.removePlayer(puuid);
    }

    /**
     * @Description: 通过 puuid查询玩家是否在黑名单 返回 null则不在黑名单中
     * 不存在返回 null
     * @param puuid :
     * @return com.qq.lol.dto.BlackPlayer
     * @throws
     * @Auther: null
     * @Date: 2023/11/29 - 19:26
     */
    @Override
    public BlackPlayerDto inBlackList(String puuid) {
        if(puuid == null || StringUtils.equals("", puuid))
            return null;

        return blackListDao.queryPlayerByPuuid(puuid);
    }

    /**
     * @Description: 分页查询黑名单玩家
     * @return java.util.List<com.qq.lol.dto.BlackPlayer>
     * @throws
     * @Auther: null
     * @Date: 2023/11/29 - 19:33
     */
    @Override
    public PageResult<BlackPlayerDto> selectBlackPlayerByPage(long pageNo, long pageSize) {
        return blackListDao.queryAllPlayer(pageNo, pageSize);
    }
}
