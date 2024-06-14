package com.qq.lol.frame.controller;

import com.qq.lol.core.services.GlobalService;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import lombok.Data;

/**
 * @Auther: null
 * @Date: 2024/6/13 - 06 - 13 - 1:00
 * @Description: 程序运行记录面板
 * @version: 1.0
 */
@Data
public class RunningRecorderController {

    private static final GlobalService globalService = GlobalService.getGlobalService();

    @FXML
    private VBox vBox;

    @FXML
    private TextArea textArea;

    private TranslateTransition openNav;

    private TranslateTransition closeNav;

    // 用于第一次填充recorder
    private boolean flag = true;

    @FXML
    public void initialize() {
        ControllerManager.runningRecorderController = this;

        vBox.setTranslateX(-300); // 初始位置隐藏在左侧
        vBox.setStyle("-fx-background-color: #333; -fx-padding: 5;");

        // 定义动画效果
        openNav = new TranslateTransition(new Duration(350), vBox);
        openNav.setToX(0);

        closeNav = new TranslateTransition(new Duration(350), vBox);
        closeNav.setToX(-300);

        textArea.setWrapText(true); // 设置多行输入框是否支持自动换行。true表示支持，false表示不支持。
        textArea.setEditable(false); // 设置多行输入框能否编辑
    }

    /**
     * @Description: 向recorder中添加记录
     * @param text:
     * @return void
     * @Auther: null
     * @Date: 2024/6/13 - 15:47
     */
    public void addRecorderText(String text) {
        if(flag) {
            // 添加程序启动期间的记录
            textArea.setText(GlobalService.getInitRecorder());
            flag = false;
            return;
        }
        textArea.setText(textArea.getText(0, textArea.getLength()) + "\n" + text);
        // 添加完毕将光标定位到最后一行
        Platform.runLater(() -> textArea.positionCaret(textArea.getText().length()));
    }

    /**
     * @Description: 控制recorder抽屉的显示和关闭
     * @return void
     * @Auther: null
     * @Date: 2024/6/13 - 13:27
     */
    public void showController() {
        if (vBox.getTranslateX() != 0) {
            // 将侧边栏置于最前面
//            vBox.toFront();   // 不需要了
            openNav.play();
        } else {
            closeNav.play();
        }
    }


}
