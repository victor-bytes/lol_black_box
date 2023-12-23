package com.qq.lol.frame.controller;

import com.qq.lol.core.services.*;
import com.qq.lol.core.services.impl.*;
import com.qq.lol.dto.*;
import com.qq.lol.frame.MainApp;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
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
    private static final BlackListService blackListService = BlackListServiceImpl.getBlackListService();

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
    public void showGameHistory(GameRoomInfoDto roomInfo) {
//        ControllerManager.mainWindowController.getQueueType()
//                .setText(roomInfo.getGameModeName() + " " + roomInfo.getGameQueueTypeName());
        // 假数据
        PlayerInfoDto me = lolPlayerService.getPlayerInfoByPuuid("ee639917-6a3c-5726-949f-537d341e5022");
        HeroDto hero = lolHeroService.getHeroInfoByChampionId("888");

        VBox playerOne = (VBox)gridPane.lookup("#player1");

        // 用 css id查找控件
        Button inBlack = (Button) playerOne.lookup("#inBlack");
        Button blackBtn = (Button) playerOne.lookup("#blackBtn");
        // 设置按钮事件---------------------------------------------------
        DialogPane dialogPane = new DialogPane();
        //显示拉黑信息
        inBlack.setOnMouseClicked(event -> {
            System.out.println("显示拉黑信息 按钮被触发");
            Stage stage = new Stage();

            // 设置窗口所有者
            stage.initOwner(MainApp.getPrimaryStage());
            stage.initModality(Modality.WINDOW_MODAL);
            stage.show();

        });

        AddBlackListController addBlackListController = ControllerManager.addBlackListController;
        // 拉黑按钮事件   setOnMouseClicked()鼠标点击(左键、右键、滚轮)就触发事件,setOnAction()是 Button事件，鼠标左键点击触发事件
        blackBtn.setOnMouseClicked(event -> {
            System.out.println("拉黑 按钮被触发");
            // 设置拉黑 DialogPane窗口-----------------------------------
            dialogPane.setContent(addBlackListController.getAnchorPane());    // 放入拉黑页面
            // 构造拉黑玩家
            BlackPlayerDto blackPlayer = new BlackPlayerDto(
                    me.getPuuid(),
                    me.getGameName(),
                    me.getTagLine(),
                    "31",   // me.getChampionId(),
                    "123456", //roomInfo.getGameId(),
                    "TW2"//me.getPlatformId()
            );
            addBlackListController.getBlackPlayer(blackPlayer); // 显示拉黑玩家

            Scene scene = new Scene(dialogPane);
            //Stage --------------------------------------
            Stage stage = new Stage();
            stage.setTitle("拉黑玩家");
            stage.setScene(scene);
            stage.initOwner(MainApp.getPrimaryStage()); // 设置窗口所有者
            stage.initModality(Modality.WINDOW_MODAL);
            stage.setResizable(false);  // 禁止拉伸窗口
            // 设置事件---------------------------------------
            dialogPane.getButtonTypes().add(ButtonType.CANCEL); // 取消 按钮
            Button cancelBtn = (Button)dialogPane.lookupButton(ButtonType.CANCEL);
            cancelBtn.setOnAction(event1 -> {                   // 取消 事件
                System.out.println("取消 添加到黑名单按钮被触发");

                stage.close();
            });
            dialogPane.getButtonTypes().add(ButtonType.OK); // 确认添加 按钮
            Button okBtn = (Button)dialogPane.lookupButton(ButtonType.OK);
            okBtn.setOnAction(event1 -> {                   // 确认 事件
                System.out.println("确认 添加到黑名单按钮被触发");
                blackPlayer.setKills(addBlackListController.getKill().getValue());
                blackPlayer.setDeaths(addBlackListController.getDeath().getValue());
                blackPlayer.setAssists(addBlackListController.getAssistant().getValue());
                blackPlayer.setMeetCount(addBlackListController.getMeetCount().getValue().toString());
                blackPlayer.setSelectedPosition(addBlackListController.getPosition().getValue());
                blackPlayer.setPlatformId(addBlackListController.getPlatform().getText());
                blackPlayer.setWin(StringUtils.equals("赢", addBlackListController.getWin().getValue()) ? 1 : 0);
                blackPlayer.setReason(addBlackListController.getReason().getText());
                System.out.println(blackPlayer);
                blackListService.addBlackList(blackPlayer);
                stage.close();
            });

            stage.show();
        });

        // 是否在黑名单--------------------------------------------------------
        if(me.getInBlackList()) {
            // 在黑名单中不显示拉黑按钮，并且显示红色背景用作提示
            playerOne.setStyle("-fx-background-color:red");
            blackBtn.setManaged(false);
        } else {
            // 不在黑名单中不显示已在黑名单按钮
            inBlack.setManaged(false);
        }

        // 填充英雄头像-----------------------------------------------------------
        ImageView imageView = (ImageView) playerOne.lookup("#heroIcon");
        imageView.setImage(hero.getChampionIcon());
        // 填充 id---------------------------------------------------------------
        TextField idText = (TextField) playerOne.lookup("#idText");
        idText.setText(me.getGameName() + "#" + me.getTagLine());
        // 填充 等级和段位----------------------------------------------------------
        Label rankLabel = (Label) playerOne.lookup("#rankLabel");
        List<RankDto> rank = rankService.getRankByPuuid("ee639917-6a3c-5726-949f-537d341e5022");
        String rankSOLO = null;
        String rankFlEX = null;
        for (RankDto rankDto : rank) {
            if(StringUtils.equals("单双排", rankDto.getQueueType())) {
                rankSOLO = "单:" + rankDto.getTier() + rankDto.getDivision() + "(" + rankDto.getLeaguePoints() + ")";
                continue;
            }
            if(StringUtils.equals("灵活组排", rankDto.getQueueType())) {
                rankFlEX = "组:" + rankDto.getTier() + rankDto.getDivision() + "(" + rankDto.getLeaguePoints() + ")";
            }
        }
        rankLabel.setText("Lv." + me.getSummonerLevel() + " " + rankSOLO + " " + rankFlEX);

        // 擅长英雄-------------------------------------------------------------
        ScrollPane masteryHeroPane = (ScrollPane) playerOne.lookup("#masteryHero");
        List<MasteryChampion> masteryChampion = me.getMasteryChampion();
        System.out.println("masteryHero的 size = " + masteryChampion.size());
        if(masteryChampion.size() != 0) {
            VBox masteryBox = showMasteryChampion(masteryChampion);
            masteryHeroPane.setContent(masteryBox);
        }


        // 战绩-----------------------------------------------------------------
//        List<GameScoreInfoDto> scores = gameHistoryService.recentScores("ee639917-6a3c-5726-949f-537d341e5022", "450", 10);
        List<GameScoreInfoDto> scores = gameHistoryService.getAllTypeScore("ee639917-6a3c-5726-949f-537d341e5022",
                0, GlobalService.getHistorySize() + 5);
        ScrollPane historyScrollPane = (ScrollPane) playerOne.lookup("#historyScrollPane");
        System.out.println(scores.get(0).getPuuid() + " 的战绩条数 = " + scores.size());
        if(scores.size() != 0) {
            // 近期没有对应战绩则不必填充数据
            VBox historyBox = showGameHistory(scores);
            historyScrollPane.setContent(historyBox);
        }

    }

    // 生成精通英雄
    private VBox showMasteryChampion(List<MasteryChampion> masteryChampion) {
        // 对 ScrollPane中填充一个 VBox，VBox中存放若干个HBox，每条精通英雄对应一个 HBox
        VBox masteryBox = new VBox(); // 存放精通英雄
        for (MasteryChampion mChampion : masteryChampion) {
            HBox hBox = new HBox(); // 用于存放一条精通的英雄
            hBox.setPrefHeight(22);
            hBox.setStyle("-fx-background-color:#96CDCD");

            Image championIcon = lolHeroService.getChampionIcon(mChampion.getChampionId());
            ImageView icon = new ImageView(championIcon);
            icon.setFitHeight(20);
            icon.setFitWidth(20);
            hBox.getChildren().add(icon);    //放入英雄头像
            // 设置圆角
            Rectangle rectangle = new Rectangle(icon.getFitWidth(), icon.getFitHeight());
            rectangle.setArcHeight(50);  // 矩形对称轴到拐角的距离为 圆的半径
            rectangle.setArcWidth(50);
            icon.setClip(rectangle);

            Label label = new Label();
            label.setAlignment(Pos.CENTER); // Label内文字居中显示
            label.setPrefWidth(70);
            label.setPrefHeight(20);
            String level = mChampion.getChampionLevel() + "级 ";
            if(!StringUtils.equals("", mChampion.getHighestGrade()))
                level = level + mChampion.getHighestGrade();
            else
                level = level + "无";
            label.setText(level);    // 英雄等级等信息
            hBox.getChildren().add(label);

            masteryBox.getChildren().add(hBox);
            Separator separator = new Separator();  // 分割器分开每条战绩 美化
            masteryBox.getChildren().add(separator);
        }
        return masteryBox;
    }

    // 生成一位玩家战绩显示的 VBox
    private VBox showGameHistory(List<GameScoreInfoDto> scores) {
        // 对 ScrollPane中填充一个 VBox，VBox中存放若干个HBox，每条战绩对应一个 HBox
        VBox vBox = new VBox(); // 存放战绩
        for (GameScoreInfoDto score : scores) {
            HBox hBox = new HBox(); // 用于存放一条战绩
//                hBox.setPrefWidth(350);   // 无需设置，自适应即可
            hBox.setPrefHeight(30);

            Image championIcon = score.getHero().getChampionIcon();
            ImageView icon = new ImageView(championIcon);
            icon.setFitHeight(25);
            icon.setFitWidth(25);
            hBox.getChildren().add(icon);    //放入英雄头像
            Rectangle rectangle = new Rectangle(icon.getFitWidth(), icon.getFitHeight());
            rectangle.setArcHeight(50);
            rectangle.setArcWidth(50);
            icon.setClip(rectangle);

            Label label1 = new Label();
            label1.setAlignment(Pos.CENTER); // Label内文字居中显示
            label1.setPrefWidth(50);
            label1.setPrefHeight(25);
            String queueName = score.getQueueName();    // 队列模式
            label1.setText(queueName);
            hBox.getChildren().add(label1);

            Label label2 = new Label();
            label2.setAlignment(Pos.CENTER); // Label内文字居中显示
            label2.setPrefWidth(60);
            label2.setPrefHeight(25);
            String KDA = score.getKills() + "/" + score.getDeaths() + "/" + score.getDeaths();  // KDA
            label2.setText(KDA);
            hBox.getChildren().add(label2);

            Label label3 = new Label();
            label3.setAlignment(Pos.CENTER); // Label内文字居中显示
            label3.setPrefWidth(130);
            label3.setPrefHeight(25);
            String gameCreationDate = score.getGameCreationDate();  // 游戏时间
            label3.setText(gameCreationDate);
            hBox.getChildren().add(label3);

            Boolean win = score.getWin();   // 游戏输赢
            if(win)
                hBox.setStyle("-fx-background-color:#98FB98");  // 赢显示绿色
            else
                hBox.setStyle("-fx-background-color:#FF6347");  // 输显示红色

            vBox.getChildren().add(hBox);
            Separator separator = new Separator();  // 分割器分开每条战绩 美化
            vBox.getChildren().add(separator);
        }
        return vBox;
    }



}
