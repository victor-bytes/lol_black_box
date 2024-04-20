package frame;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * @Auther: null
 * @Date: 2023/12/1 - 12 - 01 - 21:26
 * @Description: TODO
 * @version: 1.0
 */
public class WindowDemo01 extends Application {
    Scene scene1, scene2; // 两个场景

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("场景切换");

        // 场景切换
        Button button01 = new Button("场景1");
        // 添加事件，切换到场景2
        button01.setOnMouseClicked(event -> primaryStage.setScene(scene2));

        VBox vBox = new VBox(); // 布局
        vBox.getChildren().add(button01);
        scene1 = new Scene(vBox, 200, 200);

        Button button02 = new Button("场景2");
        // 添加事件，切换到场景1
        button02.setOnMouseClicked(event -> primaryStage.setScene(scene1));
        StackPane stackPane = new StackPane(); //布局
        stackPane.getChildren().add(button02);
        scene2 = new Scene(stackPane, 800, 800);

        primaryStage.setScene(scene1);
        primaryStage.show();

    }

    public static void main(String[] args) {
        // launch方法会默认依次执行 init()，start()，stop()方法
        launch(args);
    }

}
