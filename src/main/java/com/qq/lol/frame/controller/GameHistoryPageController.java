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
import javafx.scene.layout.*;
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
    public void showPlayers(GameRoomInfoDto roomInfo) {
        // 设置菜单栏中当前游戏模式显示
        ControllerManager.mainWindowController.getQueueType()
                .setText(roomInfo.getGameModeName() + " " + roomInfo.getGameQueueTypeName());

        List<PlayerInfoDto> teamOne = roomInfo.getTeamOnePlayers();
        List<PlayerInfoDto> teamTwo = roomInfo.getTeamTwoPlayers();

        int i = 1;
        for (PlayerInfoDto player : teamOne) {
            showPlayer(player, i++, roomInfo);
        }
        for (PlayerInfoDto player : teamTwo) {
            showPlayer(player, i++, roomInfo);
        }

    }

    public void showPlayer(PlayerInfoDto player, Integer vBox, GameRoomInfoDto roomInfo) {
        VBox playerVBox = (VBox)gridPane.lookup("#player" + vBox);
        AddBlackListController addBlackListController = ControllerManager.addBlackListController;

        // 用 css id查找控件
        Button inBlack = (Button) playerVBox.lookup("#inBlack");
        Button blackBtn = (Button) playerVBox.lookup("#blackBtn");

        // 是否在黑名单--------------------------------------------------------
        if(player.getInBlackList()) {
            // 在黑名单中   不显示拉黑按钮，并且显示红色背景用作提示
            playerVBox.setStyle("-fx-background-color:#FF4040");
            Image image = new Image("com/qq/lol/frame/static/enemy.png");
            BackgroundImage backgroundImage = new BackgroundImage(image,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundPosition.CENTER,
                    BackgroundSize.DEFAULT);
            Background background = new Background(backgroundImage);
            inBlack.setBackground(background);

            inBlack.setVisible(true);
            blackBtn.setVisible(false);

        } else {
            // 不在黑名单中   不显示已在黑名单按钮
            playerVBox.setStyle("-fx-background-color:#828282");
            inBlack.setVisible(false);
            blackBtn.setVisible(true);
        }

        // 设置按钮事件---------------------------------------------------
        //已在黑名单中，显示拉黑信息
        inBlack.setOnMouseClicked(event -> {
            DialogPane dialogPane = new DialogPane();
            dialogPane.setContent(addBlackListController.getAnchorPane());
            Scene scene = new Scene(dialogPane);
            Stage stage = new Stage();
            stage.getIcons().add(new Image("com/qq/lol/frame/static/icon.jpg"));
            stage.setTitle("修改拉黑信息");
            stage.initOwner(MainApp.getPrimaryStage()); // 设置窗口所有者
            stage.initModality(Modality.WINDOW_MODAL);
            stage.setResizable(false);  // 禁止拉伸窗口
            stage.setScene(scene);
            // 添加 确认 取消 按钮---------------------------------------
            dialogPane.getButtonTypes().add(ButtonType.OK); // 确认添加 按钮
            Button okBtn = (Button)dialogPane.lookupButton(ButtonType.OK);
            dialogPane.getButtonTypes().add(ButtonType.CANCEL); // 取消 按钮
            Button cancelBtn = (Button)dialogPane.lookupButton(ButtonType.CANCEL);
            // 获取已在黑名单中的玩家信息
            BlackPlayerDto blackPlayer = blackListService.inBlackList(player.getPuuid());
            if(blackPlayer != null && !blackPlayer.equals(new BlackPlayerDto())) {
                addBlackListController.showInBlackPlayer(blackPlayer);  // 填充已在黑名单中的玩家信息
            }
            // 设置 确认 取消 按钮事件-------------------------------------
            okBtn.setOnAction(event1 -> {
                // 将修改过的黑名单玩家 再次加入数据库
                BlackPlayerDto bp = addBlackListController.getBlackPlayer();
                // 提取页面中修改后的信息
                blackPlayer.setMeetCount(bp.getMeetCount());
                blackPlayer.setReason(bp.getReason());
                blackPlayer.setKills(bp.getKills());
                blackPlayer.setDeaths(bp.getDeaths());
                blackPlayer.setAssists(bp.getAssists());
                blackPlayer.setWin(bp.getWin());

                Integer i = blackListService.updateBlackPlayer(blackPlayer);
                stage.close();
                // 添加完成后提示框
                showTip(blackPlayer, i, "修改");
            });
            cancelBtn.setOnAction(event1 -> stage.close());

            // 设置窗口所有者
            stage.initOwner(MainApp.getPrimaryStage());
            stage.initModality(Modality.WINDOW_MODAL);
            stage.show();

        });

        // 不在黑名单中，设置拉黑按钮事件   setOnMouseClicked()鼠标点击(左键、右键、滚轮)就触发事件,setOnAction()是 Button事件，鼠标左键点击触发事件
        blackBtn.setOnMouseClicked(event -> {
            DialogPane dialogPane = new DialogPane();
            // 设置拉黑 DialogPane窗口-----------------------------------
            dialogPane.setContent(addBlackListController.getAnchorPane());    // 放入拉黑页面
            // 检查玩家是否已在黑名单中
            BlackPlayerDto blackPlayerDto = blackListService.inBlackList(player.getPuuid());
            if(StringUtils.equals(blackPlayerDto.getPuuid(), player.getPuuid())) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("玩家已在黑名单中！");
                alert.show();
                return;
            }
            // 构造拉黑玩家
            BlackPlayerDto blackPlayer = new BlackPlayerDto(
                    player.getPuuid(),
                    player.getGameName(),
                    player.getTagLine(),
                    player.getChampionId(),
                    roomInfo.getGameId(),
                    player.getPlatformId()
            );
            addBlackListController.showAddBlackPlayer(blackPlayer); // 填充拉黑玩家信息

            Scene scene = new Scene(dialogPane);
            //Stage --------------------------------------
            Stage stage = new Stage();
            stage.getIcons().add(new Image("com/qq/lol/frame/static/icon.jpg"));
            stage.setTitle("拉黑玩家");
            stage.setScene(scene);
            stage.initOwner(MainApp.getPrimaryStage()); // 设置窗口所有者
            stage.initModality(Modality.WINDOW_MODAL);
            stage.setResizable(false);  // 禁止拉伸窗口
            // 设置 确认 取消 按钮事件---------------------------------------
            dialogPane.getButtonTypes().add(ButtonType.CANCEL); // 添加取消 按钮
            Button cancelBtn = (Button)dialogPane.lookupButton(ButtonType.CANCEL);
            cancelBtn.setOnAction(event1 -> stage.close());     // 取消 事件
            dialogPane.getButtonTypes().add(ButtonType.OK);     // 确认添加 按钮
            Button okBtn = (Button)dialogPane.lookupButton(ButtonType.OK);
            okBtn.setOnAction(event1 -> {                       // 确认 事件
                BlackPlayerDto bp = addBlackListController.getBlackPlayer();
                blackPlayer.setKills(bp.getKills());
                blackPlayer.setDeaths(bp.getDeaths());
                blackPlayer.setAssists(bp.getAssists());
                blackPlayer.setMeetCount(bp.getMeetCount());
                blackPlayer.setSelectedPosition(bp.getSelectedPosition());
                blackPlayer.setPlatformId(bp.getPlatformId());
                blackPlayer.setWin(bp.getWin());
                blackPlayer.setReason(bp.getReason());

                Integer i = blackListService.addBlackList(blackPlayer); // 添加到数据库
                stage.close();
                // 添加完成后提示框
                showTip(blackPlayer, i, "添加");
            });

            stage.show();
        });

        // 填充英雄头像-----------------------------------------------------------
        ImageView imageView = (ImageView) playerVBox.lookup("#heroIcon");
        imageView.setImage(player.getHero().getChampionIcon());
        // 填充 id---------------------------------------------------------------
        TextField idText = (TextField) playerVBox.lookup("#idText");
        String name = player.getGameName() + "#" + player.getTagLine();
        idText.setText(name);
        idText.setTooltip(new Tooltip(name));
        // 填充 等级和段位----------------------------------------------------------
        Label rankLabel = (Label) playerVBox.lookup("#rankLabel");
        List<RankDto> rank = rankService.getRankByPuuid(player.getPuuid());
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
        rankLabel.setText("Lv." + player.getSummonerLevel() + " " + rankSOLO + " " + rankFlEX);

        // 擅长英雄-------------------------------------------------------------
        ScrollPane masteryHeroPane = (ScrollPane) playerVBox.lookup("#masteryHero");
        List<MasteryChampion> masteryChampion = player.getMasteryChampion();
        VBox masteryBox = showMasteryChampion(masteryChampion);
        masteryHeroPane.setContent(masteryBox);

        // 战绩-----------------------------------------------------------------
        List<GameScoreInfoDto> scores = gameHistoryService
                .recentScores(player.getPuuid(), roomInfo.getGameQueueId(), GlobalService.getHistorySize());
        ScrollPane historyScrollPane = (ScrollPane) playerVBox.lookup("#historyScrollPane");
        VBox historyBox = showGameHistory(scores);
        historyScrollPane.setContent(historyBox);
    }

    private void showTip(BlackPlayerDto blackPlayer, Integer i, String msg) {
        Alert alert;
        if (i == 2) {
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(msg + "成功！");
            alert.setContentText("玩家：" + blackPlayer.getGameName() + "#" + blackPlayer.getTagLine());
        } else {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(msg + "失败！");
        }
        alert.show();
    }

    // 生成精通英雄
    private VBox showMasteryChampion(List<MasteryChampion> masteryChampion) {
        if(masteryChampion.size() == 0) {
            return new VBox();  // 没有精通的英雄，返回空的 VBox
        }

        // 对 ScrollPane中填充一个 VBox，VBox中存放若干个HBox，每条精通英雄对应一个 HBox
        VBox masteryBox = new VBox(); // 存放精通英雄
        for (MasteryChampion mChampion : masteryChampion) {
            HBox hBox = new HBox(); // 用于存放一条精通的英雄
            hBox.setPrefWidth(108);
            hBox.setPrefHeight(22);
            hBox.setStyle("-fx-background-color:#AEEEEE");

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
        if (scores.size() == 0)
            return new VBox();  // 近期没有对应战绩则不必填充数据

        // 对 ScrollPane中填充一个 VBox，VBox中存放若干个HBox，每条战绩对应一个 HBox
        VBox vBox = new VBox(); // 存放战绩
        for (GameScoreInfoDto score : scores) {
            HBox hBox = new HBox(); // 用于存放一条战绩
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
