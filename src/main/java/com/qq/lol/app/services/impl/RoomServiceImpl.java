package com.qq.lol.app.services.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qq.lol.app.services.GlobalService;
import com.qq.lol.app.services.LolClientService;
import com.qq.lol.app.services.LolHeroService;
import com.qq.lol.app.services.RoomService;
import com.qq.lol.dto.GameRoomInfoDto;
import com.qq.lol.dto.PlayerInfoDto;
import com.qq.lol.enums.ClientStatusEnum;
import com.qq.lol.enums.GameMode;
import com.qq.lol.enums.GameQueueType;
import com.qq.lol.utils.InitGameData;
import com.qq.lol.utils.NetRequestUtil;
import org.apache.commons.lang3.StringUtils;

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
    private static final NetRequestUtil netRequestUtil = NetRequestUtil.getNetRequestUtil();
    private static final RoomService roomService = new RoomServiceImpl();
    private static final LolClientService lolClientService = LolClientServiceImpl.getLolClientService();
    private static final LolHeroService lolHeroService = LolHeroServiceImpl.getLolHeroService();
    private static final GlobalService global_service = GlobalService.getGlobalService();

    private RoomServiceImpl() {}

    public static RoomService getRoomService() {
        return roomService;
    }

    /**
     * @Description: 获取对局开始后房间信息，包含玩家信息（只适配台服、马服）
     * 包含玩家信息
     * @return com.qq.lol.dto.GameRoomInfoDto
     * @Auther: null
     * @Date: 2023/12/5 - 17:48
     */
    @Override
    public GameRoomInfoDto getRoomInfo() {
        // 获取当前客户端状态
        ClientStatusEnum clientStatus = lolClientService.getClientStatus();
        /**
         * 并且台服在选英雄阶段，会返回上一局的 gameData数据，选英雄阶段的 gameId是 0
         *  只适配台服、马服
         */
        if(clientStatus != ClientStatusEnum.ChampSelect && ClientStatusEnum.InProgress != clientStatus) {
            System.out.println("------ 未进入游戏房间 ------");
            return null;
        }

        return parseRoomInfo();
    }

    private GameRoomInfoDto parseRoomInfo() {
        GameRoomInfoDto gameRoomInfoDto = new GameRoomInfoDto();
        // 队伍一 puuid
        List<String> teamPuuidOne = new ArrayList<>();
        // 队伍一玩家信息
        List<PlayerInfoDto> teamOnePlayers = new ArrayList<>();
        // 队伍二 puuid
        List<String> teamPuuidTwo = new ArrayList<>();
        // 队伍二玩家信息
        List<PlayerInfoDto> teamTwoPlayers = new ArrayList<>();
        // 先初始化，以免调用处引发 NullIndexException
        gameRoomInfoDto.setTeamOnePlayers(teamOnePlayers);
        gameRoomInfoDto.setTeamTwoPlayers(teamTwoPlayers);
        gameRoomInfoDto.setTeamPuuidOne(teamPuuidOne);
        gameRoomInfoDto.setTeamPuuidTwo(teamPuuidTwo);

        // 开始解析
        String json = netRequestUtil.doGet("/lol-gameflow/v1/session");
        JSONObject jsonObject = JSON.parseObject(json);
        JSONObject gameData = jsonObject.getJSONObject("gameData");
        // gameId
        String gameId = gameData.getString("gameId");
        // 设置 gameId
        gameRoomInfoDto.setGameId(gameId);

        // queue里面可以获取到房间的信息
        JSONObject queue = gameData.getJSONObject("queue");
        // gameQueueType
        String gameQueueType = queue.getString("type");
        // 设置 gameQueueType、gameQueueTypeName
        GameQueueType gQT = GameQueueType.getEnumIfPresent(gameQueueType);
        gameRoomInfoDto.setGameQueueType(gQT);
        gameRoomInfoDto.setGameQueueTypeName(gQT.getGameQueueTypeMsg());
        // gameMode
        String gameMode = queue.getString("gameMode");
        // 设置 gameMode、gameModeName
        // 需要判断是否有该枚举
        GameMode gM = GameMode.getEnumIfPresent(gameMode);
        gameRoomInfoDto.setGameMode(gM);
        gameRoomInfoDto.setGameModeName(gM.getGameModeMsg());
        // 设置 gameQueueId、gameQueueName
        String gameQueueId = queue.getString("id");
        String gameQueueName = InitGameData.gameQueueIdToName.get(gameQueueId);
        gameRoomInfoDto.setGameQueueId(gameQueueId);
        gameRoomInfoDto.setGameQueueName(gameQueueName);

        System.out.println("----------当前游戏对局信息-------------");
        System.out.println("gameId            --> " + gameId);
        System.out.println("gameMode          --> " + gameMode);
        System.out.println("gameModeName      --> " + gM.getGameModeMsg());
        System.out.println("gameQueueType     --> " + gameQueueType);
        System.out.println("gameQueueTypeName --> " + gQT.getGameQueueTypeMsg());
        System.out.println("gameQueueId       --> " + gameQueueId);
        System.out.println("gameQueueName     --> " + gameQueueName);

        // 房间信息解析完毕，开始解析双方玩家信息
        if(gQT != GameQueueType.RANKED_SOLO_5x5 && gQT != GameQueueType.RANKED_FLEX_SR
                && gQT != GameQueueType.NORMAL && gQT != GameQueueType.ARAM_UNRANKED_5x5) {
            /**
             * 只获取 排位、匹配、大乱斗玩家信息
             * 其他游戏模式获取房间信息，但不获取队伍信息
             * 返回 gameRoomInfoDto，List属性 size() = 0，以免引发 NullIndexException
             */
            return gameRoomInfoDto;
        }
        /**
         * 台服 排位 在选英雄阶段无法获取双方信息,马服在选英雄阶段可以获取到我方队员信息
         */
        // 获取当前客户端状态
        ClientStatusEnum clientStatus = lolClientService.getClientStatus();
        // 获取当前大区 TW2台服 HN1 艾欧尼亚
        String platformId = global_service.getLoginSummoner().getPlatformId();
        if((gQT == GameQueueType.RANKED_FLEX_SR || gQT == GameQueueType.RANKED_SOLO_5x5) &&
                ClientStatusEnum.InProgress != clientStatus && StringUtils.equals("TW2", platformId)) {
            System.out.println("------ 台服排位必须进入游戏才可以获取玩家信息 ------");
            return gameRoomInfoDto;
        }

        // 获取 teamOne玩家信息
        teamOnePlayers = gameData.getJSONArray("teamOne")
                .toJavaList(JSONObject.class)
                .stream()
                .map(RoomServiceImpl::parsePlayer)
                .collect(Collectors.toList());
        // 获取 teamOne的puuid
        for (PlayerInfoDto player : teamOnePlayers) {
            teamPuuidOne.add(player.getPuuid());
        }

        // 获取 teamTwo玩家信息
        teamTwoPlayers = gameData.getJSONArray("teamTwo")
                .toJavaList(JSONObject.class)
                .stream()
                .map(RoomServiceImpl::parsePlayer)
                .collect(Collectors.toList());
        // 获取 teamTwo的puuid
        for (PlayerInfoDto player : teamTwoPlayers) {
            teamPuuidTwo.add(player.getPuuid());
        }

        // 设置 teamOne teamTwo
        gameRoomInfoDto.setTeamOnePlayers(teamOnePlayers);
        gameRoomInfoDto.setTeamTwoPlayers(teamTwoPlayers);

        // 设置 teamPuuid
        gameRoomInfoDto.setTeamPuuidOne(teamPuuidOne);
        gameRoomInfoDto.setTeamPuuidTwo(teamPuuidTwo);

        return gameRoomInfoDto;
    }

    // 解析 teamOne、teamTwo
    private static PlayerInfoDto parsePlayer(JSONObject player) {
        if(player == null)
            return new PlayerInfoDto();

        String championId = player.getString("championId");
        String profileIconId = player.getString("profileIconId");
        String puuid = player.getString("puuid");
        String summonerId = player.getString("summonerId");
        String summonerName = player.getString("summonerName");
        String selectedPosition = player.getString("selectedPosition");

//        teamPuuid.add(puuid);
        PlayerInfoDto playerInfoDto = new PlayerInfoDto();
        playerInfoDto.setPuuid(puuid);
        playerInfoDto.setChampionId(championId);
        playerInfoDto.setProfileIconId(profileIconId);
        playerInfoDto.setSummonerId(summonerId);
        playerInfoDto.setSummonerName(summonerName);
        playerInfoDto.setSelectedPosition(selectedPosition);
        playerInfoDto.setHero(lolHeroService.getHeroInfoByChampionId(championId));
        playerInfoDto.setMasteryChampion(lolHeroService.getMasteryChampion(summonerId));

        return playerInfoDto;
    }


}
