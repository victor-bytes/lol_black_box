package frame;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

/**
 * @Auther: null
 * @Date: 2023/11/30 - 11 - 30 - 20:51
 * @Description: TODO
 * @version: 1.0
 */
public class MouseEventHandler implements EventHandler<MouseEvent> {

    /**
     * 实现事件处理
     *
     * @param event the event which occurred
     */
    @Override
    public void handle(MouseEvent event) {
            System.out.println("你点击的是按钮");
    }
}
