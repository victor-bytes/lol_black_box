package frame;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * @Auther: null
 * @Date: 2023/11/29 - 11 - 29 - 21:53
 * @Description: Application是JavaFx的入口，任何JavaFX程序都需要继承该类并重写start方法
 * 通过 main方法执行 Application的 launch方法。
 *
 * 一个JavaFX应用由一个或多个Stage组成，一个Stage对应一个窗口。
 * 但一个JavaFX应用程序有一个主要的Stage（在执行 start(javafx.stage.Stage) 方法时由应用程序线程创建并传入）。
 * 要想在Stage上显示任何东西，就必须为其附加 Scene，在运行时可以交换 Stage的 Scene，但一个Stage同一时间只能展示一个Scene。
 *
 * 几个概念：
 * Stage相当于舞台（主窗口程序本身，有宽高等），，相当于一个容器，Stage里面有 Scene
 * Scene相当于场景（包含在窗口中），可以理解为 stage的子容器，
 * 布局需要绑定场景，布局里面可以放各种控件
 *
 * Stage、Scene、控件都可以有自己的事件
 *
 * javaFX应用的生命周期：INit -> Start -> Running -> Stop
 * @version: 1.0
 */
public class MainWindow extends Application {
    private Button button;

    /**
     *
     * @param primaryStage 表示当前窗体程序对象
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        // 设置窗口标题
        primaryStage.setTitle("第一个窗口");

        // 实例化一个按钮
        // 设置按钮名称，也可以用 set方法
        button = new Button("登录");
        // 按钮绑定一个鼠标单击事件（松开鼠标响应）
        // 实现一个实现处理器
        button.setOnMouseClicked(event -> System.out.println("你点击的是按钮"));

        /**
         *         声明一个布局，布局有很多种
         *          水平布局（HBox）垂直布局（VBox）浮动布局（FlowPane）网格布局（GridPane，TilePane）
         *         布局需要绑定场景
         *        ，布局里可以放各种控件
         */
        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(button);

        // 声明一个场景（子容器），绑定布局
        Scene scene = new Scene(stackPane, 300, 200);
        // 场景绑定一个事件
        scene.setOnMousePressed(event -> System.out.println("你点击的是场景"));  // 鼠标按下事件，需要一个事件处理对象
        primaryStage.setScene(scene);   // 场景放入stage

        // 显示窗口
        primaryStage.show();
    }

    public static void main(String[] args) {
        // launch方法会默认依次执行 init()，start()，stop()方法
        launch(args);
    }


}
