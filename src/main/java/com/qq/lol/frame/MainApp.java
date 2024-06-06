package com.qq.lol.frame;

import com.qq.lol.core.services.GlobalService;
import com.qq.lol.dto.LolClientDto;
import com.qq.lol.frame.controller.MainWindowController;
import com.qq.lol.utils.JdbcUtils;
import com.qq.lol.utils.ProcessUtil;
import javafx.application.Application;
import javafx.application.HostServices;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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

    private static HostServices hostServices;

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    public static HostServices getHost() {
        return hostServices;
    }

    @Override
    public void start(Stage primaryStage) throws IOException, InterruptedException {
        MainApp.primaryStage = primaryStage;
        hostServices = MainApp.this.getHostServices();
        primaryStage.setResizable(false);

        // 客户端是否启动检测
        if(ProcessUtil.getClientProcess().equals(new LolClientDto())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("客户端未启动！");
            alert.setContentText("未获取到 Port或 Token");
            alert.show();
            Thread.sleep(2500); // 等待 3s 自动关闭程序
            System.exit(0);
        }
        // 数据库连接检测
        if(JdbcUtils.getConnection() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("未获取到数据库连接！");
            alert.setContentText("可能是数据库未启动！");
            alert.show();
            Thread.sleep(2500); // 等待 3s 自动关闭程序
            System.exit(0);
        }

        // 设置关闭主窗口提示
        Platform.setImplicitExit(false);
        primaryStage.setOnCloseRequest(event -> {
            event.consume();    // 阻止窗口关闭，阻止事件传递
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("确定退出？");
            alert.show();

            Button okBtn = (Button)alert.getDialogPane().lookupButton(ButtonType.OK);
            okBtn.setOnAction(event1 -> {
                // 关闭线程池
//                GlobalService.fixedThreadPool.shutdown();
                Platform.exit();
            });
        });

        // 加载主窗口
        FXMLLoader mainLoader = new FXMLLoader(getClass().getResource("/com/qq/lol/frame/view/main_window.fxml")); // 必须使用绝对路径
        Parent mainRoot = mainLoader.load();
        // 主窗口控制器
        MainWindowController mainWindowController = mainLoader.getController();

        // 加载主页
        FXMLLoader mainPageLoader = new FXMLLoader(getClass().getResource("/com/qq/lol/frame/view/main_page.fxml")); // 必须使用绝对路径
        mainPageLoader.load();

        // 加载对局页
        FXMLLoader gameHistoryLoader = new FXMLLoader(getClass().getResource("/com/qq/lol/frame/view/game_history.fxml")); // 必须使用绝对路径
        gameHistoryLoader.load();

        // 加载 拉黑窗口
        FXMLLoader addBlackListLoader = new FXMLLoader(getClass().getResource("/com/qq/lol/frame/view/addBlackList.fxml")); // 必须使用绝对路径
        addBlackListLoader.load();

        // 加载 工具页
        FXMLLoader ToolsLoader = new FXMLLoader(getClass().getResource("/com/qq/lol/frame/view/tools.fxml")); // 必须使用绝对路径
        ToolsLoader.load();

        // 加载 设置页
        FXMLLoader settingsLoader = new FXMLLoader(getClass().getResource("/com/qq/lol/frame/view/settings.fxml")); // 必须使用绝对路径
        settingsLoader.load();

        // 加载 黑名单页面
        FXMLLoader blackListLoader = new FXMLLoader(getClass().getResource("/com/qq/lol/frame/view/blackList.fxml")); // 必须使用绝对路径
        blackListLoader.load();

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
