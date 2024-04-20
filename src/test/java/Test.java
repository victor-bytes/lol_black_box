import com.qq.lol.core.services.GlobalService;
import com.qq.lol.core.services.LolHeroService;
import com.qq.lol.core.services.impl.LolHeroServiceImpl;

import java.text.ParseException;

/**
 * @Auther: null
 * @Date: 2023/12/2 - 12 - 02 - 13:25
 * @Description: TODO
 * @version: 1.0
 */
public class Test {

    public static void main(String[] args) throws ParseException {
        LolHeroService lolHeroService = LolHeroServiceImpl.getLolHeroService();
        System.out.println(lolHeroService.getHeroCount());
        System.out.println(lolHeroService.getOwnedHeroCount(GlobalService.getGlobalService().getLoginSummoner().getSummonerId()));
    }
}
