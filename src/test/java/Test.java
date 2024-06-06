import com.qq.lol.frame.controller.BlackListController;
import com.qq.lol.frame.controller.ControllerManager;

import java.text.ParseException;

/**
 * @Auther: null
 * @Date: 2023/12/2 - 12 - 02 - 13:25
 * @Description: TODO
 * @version: 1.0
 */
public class Test {

    public static void main(String[] args) throws ParseException {
        BlackListController blackListController = ControllerManager.blackListController;

        System.out.println(blackListController);
    }
}
