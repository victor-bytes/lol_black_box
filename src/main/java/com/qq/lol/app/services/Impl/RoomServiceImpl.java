package com.qq.lol.app.services.Impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qq.lol.app.services.LolClientService;
import com.qq.lol.app.services.RoomService;
import com.qq.lol.dto.GameRoomInfoDto;
import com.qq.lol.dto.PlayerInfoDto;
import com.qq.lol.dto.TeamPuuidDto;
import com.qq.lol.enums.ClientStatusEnum;
import com.qq.lol.enums.GameMode;
import com.qq.lol.enums.GameQueueType;
import com.qq.lol.utils.NetRequestUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Auther: null
 * @Date: 2023/12/3 - 12 - 03 - 11:47
 * @Description: TODO
 * @version: 1.0
 */
public class RoomServiceImpl implements RoomService {
    private final NetRequestUtil netRequestUtil = NetRequestUtil.getNetRequestUtil();
    private static final RoomService roomService = new RoomServiceImpl();
    private static final LolClientService lolClientService = LolClientServiceImpl.getLolClientService();

    private RoomServiceImpl() {}

    public static RoomService getRoomService() {
        return roomService;
    }

    /**
     * 获取游戏进行后房间信息
     * 包含玩家信息
     * @return
     */
    @Override
    public GameRoomInfoDto getRoomInfo() {
        GameRoomInfoDto gameRoomInfoDto = new GameRoomInfoDto();

        System.out.println("------正在获取对局信息--------");

        // 判断是否正在对局中
        ClientStatusEnum clientStatus = lolClientService.getClientStatus();
        if(ClientStatusEnum.InProgress != clientStatus) {
            System.out.println("对局尚未开始");
            // 不在对局中
            //TODO
            return null;
        }
        String json = netRequestUtil.doGet("/lol-gameflow/v1/session");
        JSONObject gameData = JSON.parseObject(json).getJSONObject("gameData");
        // gameId
        String gameId = gameData.getString("gameId");
        JSONObject queue = gameData.getJSONObject("queue");
        // gameMode
        String gameMode = queue.getString("gameMode");
        // gameQueueId
        String gameQueueId = queue.getString("id");
        // gameQueueType
        String gameQueueType = queue.getString("type");

        gameRoomInfoDto.setGameId(gameId);
        gameRoomInfoDto.setGameMode(GameMode.getEnumIfPresent(gameMode));
        gameRoomInfoDto.setGameQueueId(gameQueueId);
        gameRoomInfoDto.setGameQueueType(GameQueueType.getEnumIfPresent(gameQueueType));

        System.out.println("gameId --> " + gameId);
        System.out.println("gameMode --> " + gameMode);
        System.out.println("gameQueueId --> " + gameQueueId);
        System.out.println("gameQueueType --> " + gameQueueType);

        List<String> teamPuuidOne = new ArrayList<>();
        List<PlayerInfoDto> teamOne;
        List<String> teamPuuidTwo = new ArrayList<>();
        List<PlayerInfoDto> teamTwo = new ArrayList<>();


        // 队伍一
        teamOne = gameData.getJSONArray("teamOne")
                .toJavaList(JSONObject.class)
                .stream()
                .map(player -> {
                    String championId = player.getString("championId");
                    String profileIconId = player.getString("profileIconId");
                    String puuid = player.getString("puuid");
                    String summonerId = player.getString("summonerId");
                    String summonerName = player.getString("summonerName");

                    teamPuuidOne.add(puuid);
                    PlayerInfoDto playerInfoDto = new PlayerInfoDto();
                    playerInfoDto.setChampionId(championId);
                    playerInfoDto.setProfileIconId(profileIconId);
                    playerInfoDto.setSummonerId(summonerId);
                    playerInfoDto.setSummonerName(summonerName);

                    return playerInfoDto;
                })
                .collect(Collectors.toList());


        return null;
    }
}
