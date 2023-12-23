package com.qq.lol.frame;

import com.qq.lol.core.services.GlobalService;
import com.qq.lol.dto.LolClientDto;
import com.qq.lol.frame.controller.MainWindowController;
import com.qq.lol.utils.ProcessUtil;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApp extends Application {
    private static final GlobalService globalService = GlobalService.getGlobalService();

    public static void main(String[] args) {
        launch(args);
    }

    // 暴露 primaryStage给其他 Controller使用
    private static Stage primaryStage;

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    @Override
    public void start(Stage primaryStage) throws IOException, InterruptedException {
        MainApp.primaryStage = primaryStage;

        if(ProcessUtil.getClientProcess().equals(new LolClientDto())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("客户端未启动！");
            alert.setContentText("未获取到 Port或 Token");
            alert.show();
            Thread.sleep(3000); // 等待 3s 自动关闭程序
            System.exit(0);
        }

        // 加载主窗口
        FXMLLoader mainLoader = new FXMLLoader(getClass().getResource("/com/qq/lol/frame/view/main_window.fxml")); // 必须使用绝对路径
        Parent mainRoot = mainLoader.load();
        // 主窗口控制器
        MainWindowController mainWindowController = mainLoader.getController();

        // 加载主页
        FXMLLoader mainPageLoader = new FXMLLoader(getClass().getResource("/com/qq/lol/frame/view/main_page.fxml")); // 必须使用绝对路径
        Parent mainPageRoot = mainPageLoader.load();

        // 加载对局页
        FXMLLoader gameHistoryLoader = new FXMLLoader(getClass().getResource("/com/qq/lol/frame/view/game_history.fxml")); // 必须使用绝对路径
        Parent gameHistoryPageRoot = gameHistoryLoader.load();

        // 加载 拉黑窗口
        FXMLLoader addBlackListLoader = new FXMLLoader(getClass().getResource("/com/qq/lol/frame/view/addBlackList.fxml")); // 必须使用绝对路径
        Parent addBlackListPageRoot = addBlackListLoader.load();

        // 主窗口加入 scene
        Scene scene = new Scene(mainRoot);
        primaryStage.setTitle("LOL Black Fox");
//        primaryStage.getIcons().add(new Image(getClass().getResource("/") + "com/qq/lol/frame/static/icon.jpg"));
        primaryStage.getIcons().add(new Image("com/qq/lol/frame/static/icon.jpg"));
        primaryStage.setScene(scene);

        primaryStage.show();
        // 显示主页内容
        mainWindowController.showMainPage(globalService.getLoginSummoner());

    }
}
