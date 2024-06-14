package com.qq.lol.frame.controller;

import com.qq.lol.core.services.GlobalService;
import com.qq.lol.core.services.LolClientService;
import com.qq.lol.core.services.RoomService;
import com.qq.lol.core.services.impl.LolClientServiceImpl;
import com.qq.lol.core.services.impl.RoomServiceImpl;
import com.qq.lol.dto.GameRoomInfoDto;
import com.qq.lol.dto.SummonerInfoDto;
import com.qq.lol.enums.ClientStatusEnum;
import com.qq.lol.utils.StandardOutTime;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import lombok.Data;

import java.io.IOException;

/**
 * @Auther: null
 * @Date: 2023/12/18 - 12 - 18 - 16:45
 * @Description: 主窗口控制器
 * @version: 1.0
 */
@Data
public class MainWindowController {
    private static final GlobalService globalService = GlobalService.getGlobalService();
    private static final RoomService roomService = RoomServiceImpl.getRoomService();
    private static final LolClientService lolClientService = LolClientServiceImpl.getLolClientService();

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

    @FXML
    private Label clientStatus;

    @FXML
    private Label queueType;

    // 侧边栏
    private static final VBox recorder = ControllerManager.runningRecorderController.getVBox();

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
        System.out.println("主页 按钮被选择");
        showMainPage(globalService.getLoginSummoner());

    }

    // 显示主页内容
    public void showMainPage(SummonerInfoDto loginSummoner) {
        refreshClientStatus();
        ControllerManager.mainPageController.showMainPage(loginSummoner);
        // 先清除控件再添加，否则会引发异常
        mainCenterPage.getChildren().clear();
        AnchorPane anchorPane = ControllerManager.mainPageController.getAnchorPane();
        AnchorPane.setLeftAnchor(anchorPane, 20.0);
        mainCenterPage.getChildren().addAll(anchorPane, recorder);
    }

    // 刷新客户端状态 Label
    public void refreshClientStatus() {
        ClientStatusEnum clientStatus = lolClientService.getClientStatus();
        this.clientStatus.setText(clientStatus.getClientStatusMsg());
        // 如果不在游戏中，也需要刷新 queueType
        if(clientStatus != ClientStatusEnum.InProgress
                && clientStatus != ClientStatusEnum.ChampSelect
                && clientStatus != ClientStatusEnum.inGame)
            queueType.setText("未在游戏中");
    }

    /**
     * @Description: 对局 按钮
     * @return void
     * @Auther: null
     * @Date: 2023/12/19 - 18:08
     */
    @FXML
    public void mainButtonTwo() {
        System.out.println("对局 按钮被选择");
        refreshClientStatus();

        mainCenterPage.getChildren().clear();
        GridPane gridPane = ControllerManager.gameHistoryPageController.getGridPane();
        AnchorPane.setLeftAnchor(gridPane, 20.0);
        mainCenterPage.getChildren().addAll(gridPane, recorder);
    }

    /**
     * @Description: 黑名单 按钮
     * @return void
     * @Auther: null
     * @Date: 2023/12/19 - 18:08
     */
    @FXML
    public void mainButtonThree() {
        System.out.println("黑名单 按钮被选择");
        refreshClientStatus();
        mainCenterPage.getChildren().clear();
        BorderPane borderPane = ControllerManager.blackListController.getBorderPane();
        AnchorPane.setLeftAnchor(borderPane, 20.0);
        mainCenterPage.getChildren().addAll(borderPane, recorder);

//        AnchorPane.setLeftAnchor(gridPane, 20.0);

    }

    /**
     * @Description: 工具 按钮
     * @return void
     * @Auther: null
     * @Date: 2023/12/19 - 18:08
     */
    @FXML
    public void mainButtonFour() {
        System.out.println("工具 按钮被选择");
        refreshClientStatus();
        mainCenterPage.getChildren().clear();

        BorderPane borderPane = ControllerManager.toolsController.getBorderPane();
        AnchorPane.setLeftAnchor(borderPane, 20.0);
        mainCenterPage.getChildren().addAll(borderPane, recorder);
    }

    /**
     * @Description: 设置 按钮
     * @return void
     * @Auther: null
     * @Date: 2023/12/19 - 18:08
     */
    @FXML
    public void mainButtonFive() {
        System.out.println("设置 按钮被选择");
        refreshClientStatus();
        mainCenterPage.getChildren().clear();

        BorderPane borderPane = ControllerManager.settingsController.getBorderPane();
        AnchorPane.setLeftAnchor(borderPane, 20.0);
        mainCenterPage.getChildren().addAll(borderPane, recorder);
    }

    /**
     * @Description: 获取对局页信息
     * @param actionEvent:
     * @return void
     * @Auther: null
     * @Date: 2024/1/1 - 13:44
     */
    public void refreshGameHistory(ActionEvent actionEvent) {
        System.out.println("刷新对局 按钮被选择");
        globalService.addRecorderText(StandardOutTime.getCurrentTime() + " 获取对局信息--");
        refreshClientStatus();
        GameRoomInfoDto roomInfo = roomService.getRoomInfo();
        if(roomInfo == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("客户端未进入游戏阶段！");
            alert.show();

            return;
        }

        mainCenterPage.getChildren().clear();
        GridPane gridPane = ControllerManager.gameHistoryPageController.getGridPane();
        AnchorPane.setLeftAnchor(gridPane, 20.0);
        mainCenterPage.getChildren().add(gridPane);
        // 填充内容
        ControllerManager.gameHistoryPageController.showPlayers(roomInfo);
        GlobalService.setCurrGameRoomInfo(roomInfo);

        globalService.addRecorderText(" gameId        --> " + roomInfo.getGameId());
        globalService.addRecorderText(" gameMode      --> " + roomInfo.getGameModeName());
        globalService.addRecorderText(" gameQueueType --> " + roomInfo.getGameQueueTypeName());
        globalService.addRecorderText(" gameQueueName --> " + roomInfo.getGameQueueName());
    }

    @FXML
    public void runningRecord(ActionEvent event) {
        System.out.println("运行记录 按钮被点击");
        ControllerManager.runningRecorderController.showController();
    }
}
