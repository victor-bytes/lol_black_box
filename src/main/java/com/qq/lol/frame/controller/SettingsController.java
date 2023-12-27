package com.qq.lol.frame.controller;

import com.qq.lol.core.services.GlobalService;
import com.qq.lol.core.services.LolHeroService;
import com.qq.lol.core.services.impl.LolHeroServiceImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import lombok.Data;

/**
 * @Auther: null
 * @Date: 2023/12/27 - 12 - 27 - 14:58
 * @Description: TODO
 * @version: 1.0
 */
@Data
public class SettingsController {
    private static final LolHeroService lolHeroService = LolHeroServiceImpl.getLolHeroService();

    @FXML
    private BorderPane borderPane;

    @FXML
    public void initialize() {
        ControllerManager.settingsController = this;
    }

    @FXML
    void historyChange(ActionEvent event) {
        System.out.println("修改默认查询战绩数量 按钮被点击");
        int oldSize = GlobalService.getHistorySize() + 1;
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("修改默认查询战绩数量");

        HBox hBox = new HBox();
        // 最多查询 30条
        Spinner<Integer> spinner = new Spinner<>(10, 30, oldSize);
        hBox.getChildren().addAll(new Label("修改查询战绩数量（最多30条）："), spinner);
        alert.getDialogPane().setContent(hBox);

        Button okBtn = (Button)alert.getDialogPane().lookupButton(ButtonType.OK);
        okBtn.setOnAction(event1 -> {
            Integer count = spinner.getValue();
            GlobalService.getGlobalService().setHistorySize(count - 1);
            alert.close();
        });

        alert.show();
    }

    @FXML
    void refreshHero(ActionEvent event) {
        System.out.println("更新数据库英雄列表 按钮被点击");
        Integer i = lolHeroService.updateHeroes();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("更新数据库英雄列表");
        if(i > 1)
            alert.setContentText("本次更新新增了英雄数据");
        else if(i == 1)
            alert.setContentText("本次更新未新增英雄");
        else
            alert.setContentText("更新失败");
        alert.show();
    }
}
