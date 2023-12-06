package com.qq.lol.app.services.impl;

import com.alibaba.fastjson.JSON;
import com.qq.lol.app.services.LolClientService;
import com.qq.lol.enums.ClientStatusEnum;
import com.qq.lol.utils.NetRequestUtil;

/**
 * @Auther: null
 * @Date: 2023/12/1 - 12 - 01 - 12:27
 * @Description: TODO
 * @version: 1.0
 */
public class LolClientServiceImpl implements LolClientService {
    private static final LolClientService lolClientService = new LolClientServiceImpl();
    private static final NetRequestUtil netRequestUtil = NetRequestUtil.getNetRequestUtil();

    private LolClientServiceImpl() {}

    public static LolClientService getLolClientService() {
        return lolClientService;
    }

    /**
     * 查看客户端当前状态
     * 游戏大厅:None
     * 房间内:Lobby
     * 匹配中:Matchmaking
     * 找到对局:ReadyCheck
     * 选英雄中:ChampSelect
     * 游戏中:InProgress
     * 游戏即将结束:PreEndOfGame
     * 等待结算页面:WaitingForStats
     * 游戏结束:EndOfGame
     * 等待重新连接:Reconnect
     *TODO：客户端状态是否改变的标志位
     */
    @Override
    public ClientStatusEnum getClientStatus() {
        System.out.println("ssss");
        // 获取客户端返回的 json
        String statusJson = netRequestUtil.doGet("/lol-gameflow/v1/gameflow-phase");
        // 解析到 status
        String status = JSON.parseObject(statusJson, String.class);

        return ClientStatusEnum.getEnumIfPresent(status);
    }
}
