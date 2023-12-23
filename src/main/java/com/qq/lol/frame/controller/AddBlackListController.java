package com.qq.lol.frame.controller;

import com.qq.lol.core.services.LolHeroService;
import com.qq.lol.core.services.impl.LolHeroServiceImpl;
import com.qq.lol.dto.BlackPlayerDto;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import lombok.Data;

@Data
public class AddBlackListController {

    private static final LolHeroService lolHeroService = LolHeroServiceImpl.getLolHeroService();

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private GridPane gridPane;

    @FXML
    private ImageView heroIcon;

    @FXML
    private Label idName;

    @FXML
    private ChoiceBox<String> position;

    @FXML
    private Spinner<Integer> assistant;

    @FXML
    private Spinner<Integer> kill;

    @FXML
    private Spinner<Integer> death;

    @FXML
    private ChoiceBox<String> win;

    @FXML
    private Spinner<Integer> meetCount;

    @FXML
    private Label platform;

    @FXML
    private TextArea reason;

    @FXML
    public void initialize() {
        ControllerManager.addBlackListController = this;
        // 初始化
        position.getItems().addAll("上单", "打野", "中单", "ADC", "辅助");
        position.setValue("上单");    // 默认值
        win.getItems().addAll("输", "赢");
        win.setValue("输");

        kill.setEditable(false);    // 不准编辑
        kill.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 50));
        death.setEditable(false);
        death.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 50));
        assistant.setEditable(false);
        assistant.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 50));
        meetCount.setEditable(false);
        meetCount.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10));

    }

    public void getBlackPlayer(BlackPlayerDto blackPlayer) {
        // 设置英雄头像
        Image icon = lolHeroService.getChampionIcon(blackPlayer.getChampionId());
        Rectangle rectangle = new Rectangle(heroIcon.getFitWidth(), heroIcon.getFitHeight());
        rectangle.setArcHeight(50);  // 矩形对称轴到拐角的距离为 圆的半径
        rectangle.setArcWidth(50);
        heroIcon.setClip(rectangle);
        heroIcon.setImage(icon);
        // 设置 ID
        idName.setText(blackPlayer.getGameName() + "#" + blackPlayer.getTagLine());
        // 设置大区
        platform.setText(blackPlayer.getPlatformId());

    }


}
