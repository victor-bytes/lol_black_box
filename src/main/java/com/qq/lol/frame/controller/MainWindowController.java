package com.qq.lol.frame.controller;

import com.qq.lol.core.services.GlobalService;
import com.qq.lol.dto.SummonerInfoDto;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

/**
 * @Auther: null
 * @Date: 2023/12/18 - 12 - 18 - 16:45
 * @Description: 主窗口控制器
 * @version: 1.0
 */
public class MainWindowController {
    private static final GlobalService globalService = GlobalService.getGlobalService();

    @FXML
    public AnchorPane mainCenterPage;

    @FXML
    private BorderPane borderPane;

    @FXML
    private Button mainButtonOne;

    @FXML
    private Button mainButtonTwo;

    @FXML
    private Button mainButtonThree;

    @FXML
    private Button mainButtonFour;

    @FXML
    private Button mainButtonFive;

    /**
     * initialize()在 fxml加载后实例化 MainWindowController 之后执行
     * FXMLLoader 首先调用默认构造函数，然后调用 initialize 方法，从而创建相应控制器的实例。
     * 首先调用构造函数，然后填充所有 @FXML 带注释的字段，最后调用 initialize ()。
     * 因此，构造函数无法访问引用在 fxml 文件中定义的组件的 @FXML 注解字段，而 initialize 方法可以访问这些注解字段。
     *
     * @throws IOException
     */
    @FXML
    public void initialize() {
/*        Image image = new Image("com/qq/lol/frame/static/icon.jpg");
        BackgroundImage backgroundImage = new BackgroundImage(image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                BackgroundSize.DEFAULT);
        Background background = new Background(backgroundImage);
        tabPane.setBackground(background)*/;

//        mainCenterPane.setStyle("-fx-background-color:#4F4F4F");
        ControllerManager.mainWindowController = this;
    }

    /**
     * @Description: 主页 按钮
     * @return void
     * @Auther: null
     * @Date: 2023/12/19 - 18:07
     */
    @FXML
    public void mainButtonOne() {
//        mainButtonOne.setOnMouseClicked(e -> {
            System.out.println("主页 按钮被选择");
            showMainPage(globalService.getLoginSummoner());
//        });
    }

    // 显示主页内容
    public void showMainPage(SummonerInfoDto loginSummoner) {
        ControllerManager.mainPageController.showMainPage(loginSummoner);
        // 先清除控件再添加，否则会引发异常
        mainCenterPage.getChildren().clear();
        AnchorPane anchorPane = ControllerManager.mainPageController.getAnchorPane();
        AnchorPane.setLeftAnchor(anchorPane, 20.0);
        mainCenterPage.getChildren().add(anchorPane);
    }

    /**
     * @Description: 对局 按钮
     * @return void
     * @Auther: null
     * @Date: 2023/12/19 - 18:08
     */
    @FXML
    public void mainButtonTwo() {
//        mainButtonTwo.setOnMouseClicked(e -> System.out.println("对局 按钮被选择"));
        System.out.println("对局 按钮被选择");
        mainCenterPage.getChildren().clear();
        mainCenterPage.getChildren().add(ControllerManager.gameHistoryPageController.getGridPane());
        // 填充内容
        ControllerManager.gameHistoryPageController.showGameHistory();
    }

    /**
     * @Description: 黑名单 按钮
     * @return void
     * @Auther: null
     * @Date: 2023/12/19 - 18:08
     */
    @FXML
    public void mainButtonThree() {
//        mainButtonThree.setOnMouseClicked(e -> System.out.println("黑名单 按钮被选择"));
        System.out.println("黑名单 按钮被选择");
        mainCenterPage.getChildren().clear();
    }

    /**
     * @Description: 工具 按钮
     * @return void
     * @Auther: null
     * @Date: 2023/12/19 - 18:08
     */
    @FXML
    public void mainButtonFour() {
//        mainButtonFour.setOnMouseClicked(e -> System.out.println("工具 按钮被选择"));
        System.out.println("工具 按钮被选择");
        mainCenterPage.getChildren().clear();
    }

    /**
     * @Description: 设置 按钮
     * @return void
     * @Auther: null
     * @Date: 2023/12/19 - 18:08
     */
    @FXML
    public void mainButtonFive() {
//        mainButtonFive.setOnMouseClicked(e -> System.out.println("设置 按钮被选择"));
        System.out.println("设置 按钮被选择");
        mainCenterPage.getChildren().clear();
    }




}
