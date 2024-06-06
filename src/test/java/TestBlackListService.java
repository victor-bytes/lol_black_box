import com.qq.lol.core.services.BlackListService;
import com.qq.lol.core.services.impl.BlackListServiceImpl;
import com.qq.lol.dto.BlackPlayerDto;
import com.qq.lol.dto.PageResult;

/**
 * @Auther: null
 * @Date: 2023/12/7 - 12 - 07 - 17:32
 * @Description: TODO
 * @version: 1.0
 */
public class TestBlackListService {
    private static BlackListService blackListService = BlackListServiceImpl.getBlackListService();

    public static void main(String[] args) {
//        System.out.println("玩家是否在黑名单：" + blackListService.inBlackList("123456789"));
//        Integer integer = blackListService.removeFromBlackList("123456789");
//        System.out.println(integer > 1);

//        BlackPlayerDto blackPlayer = new BlackPlayerDto();
//        blackPlayer.setPuuid("123123123");
//        blackPlayer.setKills(77);
//        blackPlayer.setDeaths(88);
//        blackPlayer.setAssists(99);
//        blackPlayer.setWin(0);
//        blackPlayer.setReason("演员演员演员演员演员演员演员演员演员演员演员演员演员演员演员演员演员演员演员演员演员演员演员演员演员");
//        blackPlayer.setMeetCount("5");
//
//        Integer integer = blackListService.updateBlackPlayer(blackPlayer);
//        System.out.println("是否修改成功：" + (integer == 1));
//        PageResult<BlackPlayerDto> blackPlayerDtoPageResult = blackListService.selectBlackPlayerByPage(0, 5);
//        long counts = blackPlayerDtoPageResult.getCounts();
//        long page = blackPlayerDtoPageResult.getPage();
//        long pageSize = blackPlayerDtoPageResult.getPageSize();
//        System.out.println(counts + "   " +    page + "     " + pageSize);
//        List<BlackPlayerDto> items = blackPlayerDtoPageResult.getItems();
//        for (BlackPlayerDto item : items) {
//            System.out.println(item);
//        }

        // 分页查询黑名单
        PageResult<BlackPlayerDto> result = blackListService.selectBlackPlayerByPage(0, 10);
        System.out.println(result);
//        result.getItems().forEach(item -> {
//            System.out.println(item);
//            System.out.println();
//        });


    }
}
