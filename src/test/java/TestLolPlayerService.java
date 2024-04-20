import com.qq.lol.core.services.impl.LolPlayerServiceImpl;
import com.qq.lol.core.services.LolPlayerService;

/**
 * @Auther: null
 * @Date: 2023/12/1 - 12 - 01 - 17:17
 * @Description: TODO
 * @version: 1.0
 */
public class TestLolPlayerService {
//    static LolPlayerService  lolPlayerService = new LolPlayerServiceImpl();
    private static LolPlayerService lolPlayerService = LolPlayerServiceImpl.getLolPlayerService();

    public static void main(String[] args) {
        System.out.println("--------66666-----------");
        String puuid = "ee639917-6a3c-5726-949f-537d341e5022";
//        System.out.println("rank   " + lolPlayerService.getRankByPuuid(puuid));
        System.out.println(lolPlayerService.getPlayerInfoByPuuid(puuid));
//        List<GameScoreInfoDto> scoreInfoByPuuid = lolPlayerService.getRecentTwentyScoreInfoByPuuid(puuid);
//        for (GameScoreInfoDto gameScoreInfoDto : scoreInfoByPuuid) {
//            System.out.println(gameScoreInfoDto);
//        }

    }

}
