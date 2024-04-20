import com.qq.lol.core.services.LolHeroService;
import com.qq.lol.core.services.impl.LolHeroServiceImpl;
import com.qq.lol.dto.MasteryChampion;
import org.apache.commons.lang3.StringUtils;

/**
 * @Auther: null
 * @Date: 2023/12/6 - 12 - 06 - 20:43
 * @Description: TODO
 * @version: 1.0
 */
public class TestLolHeroService {
    private static final LolHeroService lolHeroService = LolHeroServiceImpl.getLolHeroService();

    public static void main(String[] args) {
        System.out.println("==== TEST LolHeroService =====");
//        Integer integer = lolHeroService.updateHeroes();
//        if(integer > 1)
//            System.out.println("更新英雄信息成功");
//        else if(integer == 1)
//            System.out.println("未出新英雄");
//        else if(integer < 1)
//            System.out.println("更新英雄信息失败");
//
//        System.out.println(lolHeroService.getHeroInfoByChampionId("498"));
        for (MasteryChampion masteryChampion : lolHeroService.getMasteryChampion("3112401987798752", 20)) {
            System.out.println(lolHeroService.getHeroInfoByChampionId(masteryChampion.getChampionId()).getName());
            System.out.println("赛季最高评分：" + (StringUtils.equals("", masteryChampion.getHighestGrade()) ? "无" : masteryChampion.getHighestGrade()));
            System.out.println("英雄成就等级：" + masteryChampion.getChampionLevel() + "级");
            System.out.println("-----------------");
        }
    }
}
