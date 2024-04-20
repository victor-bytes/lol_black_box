package frame;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LOLPlayerStatsApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("JavaFX BorderPane Example");

        BorderPane borderPane = new BorderPane();

        // 创建菜单栏
        MenuBar menuBar = new MenuBar();
        Menu menu1 = new Menu("Menu 1");
        Menu menu2 = new Menu("Menu 2");

        // 添加菜单项
        MenuItem menuItem1 = new MenuItem("Page 1");
        MenuItem menuItem2 = new MenuItem("Page 2");

        // 设置菜单项点击事件
        menuItem1.setOnAction(event -> setCenterContent(borderPane, "Page 1 Content"));
        menuItem2.setOnAction(event -> setCenterContent(borderPane, "Page 2 Content"));

        // 将菜单项添加到菜单
        menu1.getItems().add(menuItem1);
        menu2.getItems().add(menuItem2);

        // 将菜单添加到菜单栏
        menuBar.getMenus().addAll(menu1, menu2);

        // 设置左侧菜单栏
        borderPane.setTop(menuBar);

        // 初始显示的内容
        setCenterContent(borderPane, "Initial Content");

        // 创建场景
        Scene scene = new Scene(borderPane, 400, 300);

        // 设置场景到主舞台
        primaryStage.setScene(scene);

        // 显示主舞台
        primaryStage.show();
    }

    private void setCenterContent(BorderPane borderPane, String content) {
        // 创建右侧内容
        VBox rightContent = new VBox();
        rightContent.getChildren().add(new javafx.scene.control.Label(content));

        // 设置右侧内容
        BorderPane.setAlignment(rightContent, javafx.geometry.Pos.CENTER);
        borderPane.setCenter(rightContent);
    }
}



