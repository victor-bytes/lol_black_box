package com.qq.lol.frame;

import com.qq.lol.core.services.GlobalService;
import com.qq.lol.frame.controller.MainWindowController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
    public void start(Stage primaryStage) throws IOException {
        MainApp.primaryStage = primaryStage;

        // 加载主窗口
        FXMLLoader mainLoader = new FXMLLoader(getClass().getResource("/com/qq/lol/frame/view/main_window.fxml")); // 必须使用绝对路径
        Parent mainRoot = mainLoader.load();
        // 主窗口控制器
        MainWindowController mainWindowController = mainLoader.getController();

        // 加载主页
        FXMLLoader mainPageLoader = new FXMLLoader(getClass().getResource("/com/qq/lol/frame/view/main_page.fxml")); // 必须使用绝对路径
        Parent mainPageRoot = mainPageLoader.load();
        // 主页控制器
//        MainPageController mainPageController = mainPageLoader.getController();

        // 加载对局页
        FXMLLoader gameHistoryLoader = new FXMLLoader(getClass().getResource("/com/qq/lol/frame/view/game_history.fxml")); // 必须使用绝对路径
        Parent gameHistoryPageRoot = gameHistoryLoader.load();
        // 对局页控制器
//        GameHistoryPageController gameHistoryPageController = gameHistoryLoader.getController();

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
