package com.qq.lol.frame.controller;

import com.qq.lol.core.services.LolHeroService;
import com.qq.lol.core.services.impl.LolHeroServiceImpl;
import com.qq.lol.dto.BlackPlayerDto;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Rectangle;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.Set;

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
    private HBox hBox;

    @FXML
    public void initialize() {
        ControllerManager.addBlackListController = this;
        AnchorPane.setLeftAnchor(gridPane, 15.0);
        AnchorPane.setLeftAnchor(reason, 15.0);
        AnchorPane.setLeftAnchor(hBox, 15.0);
        // 初始化
        position.getItems().addAll("上单", "打野", "中单", "ADC", "辅助", "NONE");
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
        meetCount.getValueFactory().setValue(1);

        ObservableList<Node> children = hBox.getChildren();
        for (Node child : children) {
            CheckBox cb = (CheckBox)child;
            checkBoxLis(cb);
        }

    }

    // 根据页面信息构造拉黑玩家对象
    public BlackPlayerDto getBlackPlayer() {
        BlackPlayerDto blackPlayer = new BlackPlayerDto();
        String[] split = idName.getText().split("#");   // 有风险，但不重要
        blackPlayer.setGameName(split[0]);  // 只能作为参考，如果玩家 id中含有 # 则会出 bug
        blackPlayer.setTagLine(split[1]);
        blackPlayer.setSelectedPosition(position.getValue());
        blackPlayer.setKills(kill.getValue());
        blackPlayer.setDeaths(death.getValue());
        blackPlayer.setAssists(assistant.getValue());
        blackPlayer.setWin(StringUtils.equals("赢", win.getValue()) ? 1 : 0);
        blackPlayer.setMeetCount(meetCount.getValue().toString());
        blackPlayer.setPlatformId(platform.getText());
        blackPlayer.setReason(reason.getText());

        return blackPlayer;
    }

    // 设置复选框监听事件
    private void checkBoxLis(CheckBox cb) {
        cb.selectedProperty().addListener((observable, oldValue, newValue) -> {
            setReasonWard(cb, newValue);
        });
    }

    // 原因词复选框 选择 或 不选择 的 reasonText设置
    private void setReasonWard(CheckBox ward, boolean flag) {
        String oldReason = reason.getText();
        if(flag)
            reason.setText(oldReason + " " + ward.getText());   // 添加原因词
        else {
            String newReason = oldReason.replace(ward.getText(), "");   // 删除原因词
            reason.setText(newReason);
        }
    }

    // 填充拉黑页面的数据
    public void showAddBlackPlayer(BlackPlayerDto blackPlayer) {
        // 设置英雄头像
        Image icon = lolHeroService.getChampionIcon(blackPlayer.getChampionId());
        Rectangle rectangle = new Rectangle(heroIcon.getFitWidth(), heroIcon.getFitHeight());
        rectangle.setArcHeight(100);  // 矩形对称轴到拐角的距离为 圆的半径
        rectangle.setArcWidth(100);
        heroIcon.setClip(rectangle);
        heroIcon.setImage(icon);
        // 设置 ID
        String id = blackPlayer.getGameName() + "#" + blackPlayer.getTagLine();
        idName.setText(id);
        idName.setTooltip(new Tooltip(id)); // 设置鼠标悬停显示
        // 设置大区
        String platformID = blackPlayer.getPlatformId();
        platform.setText(platformID);
        platform.setTooltip(new Tooltip(platformID));
        // 清空 reasonText
        reason.clear();
        // 重置原因词复选框
        Set<Node> checkBox = hBox.lookupAll("CheckBox");
        for (Node box : checkBox) {
            CheckBox cb = (CheckBox)box;
            cb.setSelected(false);
        }

        // 重置 KDA数量
        kill.getValueFactory().setValue(0);
        death.getValueFactory().setValue(0);
        assistant.getValueFactory().setValue(0);

        // 重置遇见次数
        meetCount.getValueFactory().setValue(1);
    }

    public void showInBlackPlayer(BlackPlayerDto blackPlayer) {
        showAddBlackPlayer(blackPlayer);
        // 设置位置
        position.setValue(blackPlayer.getSelectedPosition());
        // 设置 KDA
        kill.getValueFactory().setValue(blackPlayer.getKills());
        death.getValueFactory().setValue(blackPlayer.getDeaths());
        assistant.getValueFactory().setValue(blackPlayer.getAssists());
        // 设置 win
        win.setValue((blackPlayer.getWin() == 1) ? "赢" : "输");
        // 设置遇见次数
        int count = Integer.parseInt(blackPlayer.getMeetCount());
        meetCount.getValueFactory().setValue(count + 1);
        // 设置拉黑原因
        String oldReason = blackPlayer.getReason();
        String newReason = oldReason + "\n第【" + count + "】次遇见时，对局【" + ((blackPlayer.getWin() == 1) ? "赢】 " : "输】 ")
                + "K/D/A =【" + blackPlayer.getKills() + "/" + blackPlayer.getDeaths() + "/" + blackPlayer.getAssists()
                + "】时间：" + blackPlayer.getLast_update_time();
        reason.setText(newReason);
    }


}
