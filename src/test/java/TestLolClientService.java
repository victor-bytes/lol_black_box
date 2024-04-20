import com.qq.lol.core.services.impl.LolClientServiceImpl;
import com.qq.lol.core.services.LolClientService;

/**
 * @Auther: null
 * @Date: 2023/12/1 - 12 - 01 - 15:41
 * @Description: TODO
 * @version: 1.0
 */
public class TestLolClientService {
    public static void main(String[] args) throws InterruptedException {
        /*LolClientService lolClientService = new LolClientServiceImpl();
        ClientStatusEnum status = lolClientService.getClientStatus();
        System.out.println(StandardOutTime.getCurrentTime() + "当前客户端状态：" + status.getClientStatusMsg());

        Thread.sleep(1000 * 10);

        ClientStatusEnum status2 = lolClientService.getClientStatus();
        System.out.println(StandardOutTime.getCurrentTime() + "当前客户端状态：" + status2.getClientStatusMsg());*/

        LolClientService lolClientService = LolClientServiceImpl.getLolClientService();
        System.out.println(lolClientService.getClientStatus().getClientStatusMsg());

    }


}
