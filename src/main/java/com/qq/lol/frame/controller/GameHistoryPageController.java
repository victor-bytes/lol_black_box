package com.qq.lol.frame.controller;

import com.qq.lol.app.services.LolClientService;
import com.qq.lol.app.services.LolHeroService;
import com.qq.lol.app.services.RankService;
import com.qq.lol.app.services.RoomService;
import com.qq.lol.app.services.impl.LolClientServiceImpl;
import com.qq.lol.app.services.impl.LolHeroServiceImpl;
import com.qq.lol.app.services.impl.RankServiceImpl;
import com.qq.lol.app.services.impl.RoomServiceImpl;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

/**
 * @Auther: null
 * @Date: 2023/12/18 - 12 - 18 - 21:32
 * @Description: 对局战绩页面控制器
 * @version: 1.0
 */
public class GameHistoryPageController {
    private static final RoomService roomService = RoomServiceImpl.getRoomService();
    private static final LolClientService lolClientService = LolClientServiceImpl.getLolClientService();
    private static final LolHeroService lolHeroService = LolHeroServiceImpl.getLolHeroService();
    private static final RankService rankService = RankServiceImpl.getRankService();

    @FXML
    private VBox playerOne;

    @FXML
    private VBox playerTwo;

    @FXML
    private VBox playerThree;

    @FXML
    private VBox playerFour;

    @FXML
    private VBox playerFive;

    @FXML
    private VBox playerSix;

    @FXML
    private VBox playerSeven;

    @FXML
    private VBox playerEight;

    @FXML
    private VBox playerNine;

    @FXML
    private VBox playerTen;

    @FXML
    public void initialize() {
        ControllerManager.gameHistoryPageController = this;
    }

    // 填充数据
    public void showGameHistory() {

    }

}
