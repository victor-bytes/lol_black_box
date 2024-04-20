import com.qq.lol.core.services.*;
import com.qq.lol.core.services.impl.*;
import com.qq.lol.dto.GameRoomInfoDto;
import com.qq.lol.dto.GameScoreInfoDto;
import com.qq.lol.dto.MasteryChampion;
import com.qq.lol.dto.PlayerInfoDto;
import com.qq.lol.enums.GameQueueType;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * @Auther: null
 * @Date: 2023/12/4 - 12 - 04 - 17:03
 * @Description: TODO
 * @version: 1.0
 */
public class TestGameHistoryService {
    private static final GameHistoryService gameHistoryService = GameHistoryServiceImpl.getGameHistoryService();
    private static final RoomService roomService = RoomServiceImpl.getRoomService();
    private static final LolPlayerService lolPlayerService = LolPlayerServiceImpl.getLolPlayerService();
    private static final LolHeroService lolHeroService = LolHeroServiceImpl.getLolHeroService();
    private static final LolClientService lolClientService = LolClientServiceImpl.getLolClientService();

    public static void main(String[] args) {
        System.out.println("-----TEST Game History Room Service-----");
        /**
         * 获取当前游戏阶段
         * 台服在选英雄阶段无法获取双方信息,马服在选英雄阶段可以获取到我方队员信息
         * 并且台服在选英雄阶段，可能会返回上一局的 gameData数据，选英雄阶段的 gameId是 0
         *
         * TODO 试试接口 /lol-champ-select/v1/session
         */
        GameRoomInfoDto roomInfo = roomService.getRoomInfo();
        if(roomInfo == null) {
            System.out.println("=== 未找到房间信息 ===");
            return;
        }

        System.out.println();
        List<PlayerInfoDto> teamOne = roomInfo.getTeamOnePlayers();
        List<PlayerInfoDto> teamTwo = roomInfo.getTeamTwoPlayers();

        // 根据 gameQueueType获取队伍战绩
        GameQueueType gameQueueType = roomInfo.getGameQueueType();
        Map<String, List<GameScoreInfoDto>> playerScoreOne =
                gameHistoryService.recentGameScoreByQueueType(gameQueueType, 10, roomInfo.getTeamPuuidOne());
        System.out.println("============玩家信息面板=============");
        for (PlayerInfoDto player : teamOne) {
            String puuid = player.getPuuid();
            PlayerInfoDto p = lolPlayerService.getPlayerInfoByPuuid(puuid);
            System.out.println("-----------> 队伍一玩家：" + p.getGameName() + "#" + p.getTagLine());
            System.out.println("puuid              -> " + puuid);
            System.out.println("championId         -> " + player.getChampionId());
            System.out.println("hero               -> " + player.getHero());
            System.out.println("profileIconId      -> " + player.getProfileIconId());
            System.out.println("selectedPosition   -> " + player.getSelectedPosition());
            System.out.println("summonerId         -> " + player.getSummonerId());
            System.out.println("summonerName       -> " + player.getSummonerName());
            System.out.println("精通的英雄           ->");
            for (MasteryChampion masteryChampion : player.getMasteryChampion()) {
                System.out.println("    " + lolHeroService.getHeroInfoByChampionId(masteryChampion.getChampionId()).getName());
                System.out.println("    赛季最高评分：" + (StringUtils.equals("", masteryChampion.getHighestGrade())
                        ? "无" : masteryChampion.getHighestGrade()));
                System.out.println("    英雄成就等级：" + masteryChampion.getChampionLevel() + "级");
                System.out.println("-----------------");
            }
            System.out.println("----- 近期战绩 -----");
            List<GameScoreInfoDto> gameScoreInfoDtos = playerScoreOne.get(puuid);
            if(gameScoreInfoDtos == null || gameScoreInfoDtos.size() == 0)
                continue;
            for (GameScoreInfoDto gameScoreInfoDto : gameScoreInfoDtos) {
                System.out.print("  " + gameScoreInfoDto.getQueueName() + "   ");
                System.out.print (gameScoreInfoDto.getWin() ? "赢    " : "输  ");
                System.out.print(gameScoreInfoDto.getHero().getName() + "      ");
                System.out.print("K/D/A：" + gameScoreInfoDto.getKills() + "/" + gameScoreInfoDto.getDeaths() + "/" +
                        gameScoreInfoDto.getDeaths() + "    ");
                System.out.println(gameScoreInfoDto.getGameCreationDate());
            }

        }

        Map<String, List<GameScoreInfoDto>> playerScoreTwo =
                gameHistoryService.recentGameScoreByQueueType(gameQueueType, 10, roomInfo.getTeamPuuidTwo());
        for (PlayerInfoDto player : teamTwo) {
            String puuid = player.getPuuid();
            PlayerInfoDto p = lolPlayerService.getPlayerInfoByPuuid(puuid);
            System.out.println("-----------> 队伍二玩家：" + p.getGameName() + "#" + p.getTagLine());
            System.out.println("puuid              -> " + puuid);
            System.out.println("championId         -> " + player.getChampionId());
            System.out.println("hero               -> " + player.getHero());
            System.out.println("profileIconId      -> " + player.getProfileIconId());
            System.out.println("selectedPosition   -> " + player.getSelectedPosition());
            System.out.println("summonerId         -> " + player.getSummonerId());
            System.out.println("summonerName       -> " + player.getSummonerName());
            System.out.println("精通的英雄           ->");
            for (MasteryChampion masteryChampion : p.getMasteryChampion()) {
                System.out.println("    " + lolHeroService.getHeroInfoByChampionId(masteryChampion.getChampionId()).getName());
                System.out.println("    赛季最高评分：" + masteryChampion.getHighestGrade());
                System.out.println("    英雄成就等级：" + masteryChampion.getChampionLevel() + "级");
                System.out.println("-----------------");
            }
            System.out.println("----- 近期战绩 -----");
            List<GameScoreInfoDto> gameScoreInfoDtos = playerScoreTwo.get(puuid);
            if(gameScoreInfoDtos == null || gameScoreInfoDtos.size() == 0)
                continue;
            for (GameScoreInfoDto gameScoreInfoDto : gameScoreInfoDtos) {
                System.out.print("  " + gameScoreInfoDto.getQueueName() + "   ");
                System.out.print (gameScoreInfoDto.getWin() ? "赢    " : "输  ");
                System.out.print(gameScoreInfoDto.getHero().getName() + "     ");
                System.out.print("K/D/A：" + gameScoreInfoDto.getKills() + "/" + gameScoreInfoDto.getDeaths() + "/" +
                        gameScoreInfoDto.getDeaths() + "      ");
                System.out.println(gameScoreInfoDto.getGameCreationDate());
            }

        }

    }
}
