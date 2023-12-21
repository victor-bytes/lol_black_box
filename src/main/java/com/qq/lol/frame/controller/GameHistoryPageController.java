package com.qq.lol.frame.controller;

import com.qq.lol.core.services.*;
import com.qq.lol.core.services.impl.*;
import com.qq.lol.dto.GameScoreInfoDto;
import com.qq.lol.dto.HeroDto;
import com.qq.lol.dto.PlayerInfoDto;
import com.qq.lol.dto.RankDto;
import com.qq.lol.frame.MainApp;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * @Auther: null
 * @Date: 2023/12/18 - 12 - 18 - 21:32
 * @Description: 对局战绩页面控制器
 * @version: 1.0
 */
@Data
public class GameHistoryPageController {
    private static final RoomService roomService = RoomServiceImpl.getRoomService();
    private static final LolClientService lolClientService = LolClientServiceImpl.getLolClientService();
    private static final LolHeroService lolHeroService = LolHeroServiceImpl.getLolHeroService();
    private static final RankService rankService = RankServiceImpl.getRankService();
    private static final GameHistoryService gameHistoryService = GameHistoryServiceImpl.getGameHistoryService();
    private static final LolPlayerService lolPlayerService = LolPlayerServiceImpl.getLolPlayerService();

    @FXML
    private GridPane gridPane;

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
        // 假数据
        PlayerInfoDto me = lolPlayerService.getPlayerInfoByPuuid("ee639917-6a3c-5726-949f-537d341e5022");
        HeroDto hero = lolHeroService.getHeroInfoByChampionId("888");

        VBox playerOne = (VBox)gridPane.lookup("#playerOne");

        // 用 css id查找控件
        Button inBlack = (Button) playerOne.lookup("#inBlack");
        Button blackBtn = (Button) playerOne.lookup("#blackBtn");
        // 设置按钮事件
        inBlack.setOnMouseClicked(event -> {
            //显示拉黑信息
            System.out.println("在黑名单中 按钮被触发");
            DialogPane dialogPane = new DialogPane();
            Stage stage = new Stage();

            // 设置窗口所有者
            stage.initOwner(MainApp.getPrimaryStage());
            stage.initModality(Modality.WINDOW_MODAL);
            stage.show();

        });

        blackBtn.setOnMouseClicked(event -> {
            System.out.println("拉黑 按钮被触发");
            // 设置拉黑窗口
            DialogPane dialogPane = new DialogPane();
            MouseButton button = event.getButton();
//            button.
            System.out.println(me.getTagLine());

            Scene scene = new Scene(dialogPane);
            Stage stage = new Stage();
            stage.setTitle("拉黑信息");
            stage.setScene(scene);
            stage.initOwner(MainApp.getPrimaryStage()); // 设置窗口所有者
            stage.initModality(Modality.WINDOW_MODAL);
            stage.setResizable(false);  // 禁止拉伸窗口
            stage.show();
        });
        // 是否在黑名单
        if(me.getInBlackList()) {
            // 在黑名单中
            playerOne.setStyle("-fx-background-color:red");
            // 不显示拉黑按钮
            blackBtn.setManaged(false);
        } else {
            // 不显示已在黑名单按钮
            inBlack.setManaged(false);
        }

//        ----------------------------------------------------------
        // 填充英雄头像
        ImageView imageView = (ImageView) playerOne.lookup("#heroIcon");
        imageView.setImage(hero.getChampionIcon());
        TextField idText = (TextField) playerOne.lookup("#idText");
        // 填充 id
        idText.setText(me.getGameName() + "#" + me.getTagLine());
        // 填充 等级和段位
        Label rankLabel = (Label) playerOne.lookup("#rankLabel");
        List<RankDto> rank = rankService.getRankByPuuid("ee639917-6a3c-5726-949f-537d341e5022");
        String rankSOLO = null;
        String rankFlEX = null;
        for (RankDto rankDto : rank) {
            if(StringUtils.equals("单双排", rankDto.getQueueType())) {
                rankSOLO = " 单:" + rankDto.getTier() + rankDto.getDivision() + "(" + rankDto.getLeaguePoints() + ")";
                continue;
            }
            if(StringUtils.equals("灵活组排", rankDto.getQueueType())) {
                rankFlEX = " 组:" + rankDto.getTier() + rankDto.getDivision() + "(" + rankDto.getLeaguePoints() + ")";
            }
        }
        rankLabel.setText("Lv." + me.getSummonerLevel() + rankSOLO + rankFlEX);
//        ----------------------------------------------------------------
        // 擅长英雄
        ComboBox comboBox = (ComboBox) playerOne.lookup("#masteryHero");


        // 战绩
//        List<GameScoreInfoDto> scores = gameHistoryService.recentScores("ee639917-6a3c-5726-949f-537d341e5022", "450", 10);
        List<GameScoreInfoDto> scores = gameHistoryService.getAllTypeScore("ee639917-6a3c-5726-949f-537d341e5022",
                0, GlobalService.getHistorySize() + 10);
        ScrollPane historyScrollPane = (ScrollPane) playerOne.lookup("#historyScrollPane");
        if(scores.size() != 0) {
            System.out.println("战绩size=" + scores.size());
            // 对 ScrollPane中填充一个 VBox，VBox中存放若干个HBox，每条战绩对应一个 HBox
            VBox vBox = new VBox(); // 存放战绩
            for (GameScoreInfoDto score : scores) {
                HBox hBox = new HBox(); // 存放一条战绩
//                hBox.setPadding(new Insets(5,0,5,0));
//                hBox.setPrefWidth(350);
                hBox.setPrefHeight(40);
                hBox.setStyle("-fx-border-color:#000000");  // TODO 未生效

                Image championIcon = score.getHero().getChampionIcon();
                ImageView icon = new ImageView(championIcon);
                icon.setFitHeight(25);
                icon.setFitWidth(25);
                hBox.getChildren().add(icon);    //放入英雄头像

                Label label1 = new Label();
                label1.setAlignment(Pos.CENTER); // Label内文字居中显示
                label1.setPrefWidth(50);
                String queueName = score.getQueueName();    // 队列模式
                label1.setText(queueName);
                hBox.getChildren().add(label1);

                Label label2 = new Label();
                label2.setAlignment(Pos.CENTER); // Label内文字居中显示
                label2.setPrefWidth(60);
                String KDA = score.getKills() + "/" + score.getDeaths() + "/" + score.getDeaths();  // KDA
                label2.setText(KDA);
                hBox.getChildren().add(label2);

                Label label3 = new Label();
                label3.setAlignment(Pos.CENTER); // Label内文字居中显示
                label3.setPrefWidth(150);
                String gameCreationDate = score.getGameCreationDate();  // 游戏时间
                label3.setText(gameCreationDate);
                hBox.getChildren().add(label3);

                Boolean win = score.getWin();   // 游戏输赢
                if(win)
                    hBox.setStyle("-fx-background-color:#98FB98");  // 赢显示绿色
                else
                    hBox.setStyle("-fx-background-color:#FF6347");  // 输显示红色

                vBox.getChildren().add(hBox);
            }
            historyScrollPane.setContent(vBox);

        }

    }

    private PlayerInfoDto getPlayerInfo(){
        return null;
    }

}
