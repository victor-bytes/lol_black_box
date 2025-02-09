package com.qq.lol.frame.controller;

import com.qq.lol.core.services.GlobalService;
import com.qq.lol.core.services.LootService;
import com.qq.lol.core.services.impl.LootServiceImpl;
import com.qq.lol.frame.MainApp;
import com.qq.lol.utils.StandardOutTime;
import javafx.application.HostServices;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import lombok.Data;

/**
 * @Auther: null
 * @Date: 2023/12/26 - 12 - 26 - 21:58
 * @Description: 工具页管理
 * @version: 1.0
 */
@Data
public class ToolsController {
    private static final LootService lootService = LootServiceImpl.getLootService();

    private static final GlobalService globalService = GlobalService.getGlobalService();

    @FXML
    private BorderPane borderPane;

    @FXML
    private HBox hBoxOne;

    @FXML
    private Label balance;

    @FXML
    public void initialize() {
        ControllerManager.toolsController = this;
        // 填充初始神话精粹数量
        refreshBalance(GlobalService.getMythicCount());
    }

    // 更新神话精粹数量显示
    private void refreshBalance(Integer count) {
        balance.setText(count.toString());
    }

    @FXML
    void exchange(ActionEvent event) {
        globalService.addRecorderText(StandardOutTime.getCurrentTime() + " 兑换神话精粹--");

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("神话精粹兑换为 橙色精粹");
        // 判断神话精粹数量是否大于 0
        int remain = Integer.parseInt(balance.getText());    // 当前神话精粹数量
        if(remain < 1) {
            alert.setHeaderText("神话精粹数量不足！");
            alert.show();
            return;
        }

        HBox hBox = new HBox();
        Spinner<Integer> spinner = new Spinner<>(1, remain, 1);
        hBox.getChildren().addAll(new Label("本次兑换神话精粹数量："), spinner);
        alert.getDialogPane().setContent(hBox);

        Button okBtn = (Button)alert.getDialogPane().lookupButton(ButtonType.OK);
        okBtn.setOnAction(event1 -> {
            Integer count = spinner.getValue();
            Integer i = lootService.mythicToOrange(count);
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            if(i == (remain - count)) {
                a.setHeaderText("兑换成功！");
            } else {
                a.setHeaderText("兑换失败！");
            }
            a.show();
            // 兑换完毕必须刷新神话精粹数量
            refreshBalance(GlobalService.refreshMythicCount());
            alert.close();
        });

        alert.show();

    }

    public void lolQQ(ActionEvent actionEvent) {
        HostServices host = MainApp.getHost();
        host.showDocument("https://lol.qq.com/main.shtml");
    }

    public void opgg(ActionEvent actionEvent) {
        HostServices host = MainApp.getHost();
        host.showDocument("https://www.op.gg/summoners/tw/%E9%9B%BB%E7%AB%B6%E8%98%87%E7%82%B3%E6%B7%BB-2311");
    }

    public void lolTW(ActionEvent actionEvent) {
        HostServices host = MainApp.getHost();
        host.showDocument("https://www.leagueoflegends.com/zh-tw/news/");
    }

    public void lolTWMsg(ActionEvent actionEvent) {
        HostServices host = MainApp.getHost();
        host.showDocument("https://status.riotgames.com/lol?region=tw2&locale=zh_TW");
    }
}
