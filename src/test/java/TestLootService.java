import com.qq.lol.core.services.LootService;
import com.qq.lol.core.services.impl.LootServiceImpl;

/**
 * @Auther: null
 * @Date: 2023/12/17 - 12 - 17 - 12:31
 * @Description: TODO
 * @version: 1.0
 */
public class TestLootService {
    private static final LootService lootService = LootServiceImpl.getLootService();

    public static void main(String[] args) {
//        System.out.println("剩余神话精粹：" + lootService.mythicToOrange(20));
        System.out.println("神话精粹数量：" + lootService.getMythicCount());
    }
}
