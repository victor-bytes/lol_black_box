package com.qq.lol.frame.controller;

import com.qq.lol.core.services.GlobalService;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;

/**
 * @Auther: null
 * @Date: 2023/12/18 - 12 - 18 - 18:42
 * @Description: 主页按钮控制器
 * @version: 1.0
 */
public class ButtonOnePageController {
    private static final GlobalService globalService = GlobalService.getGlobalService();

    @FXML
    private AnchorPane buttonOnePage;

    @FXML
    private ListView<String> listView;

/*    public void initialize() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/qq/lol/frame/view/buttonOnePage.fxml"));
        Parent root = loader.load();
        buttonOnePageController = loader.getController();
    }*/

    // 显示已登录召唤师信息
    public void showLoginSummoner() {
        // 初始化一个 可以显示的 List
//        ObservableList<String> summonerList = FXCollections.observableArrayList();
//        SummonerInfoDto loginSummoner = globalService.getLoginSummoner();
//        summonerList.add("    头像ID      " + loginSummoner.getProfileIconId());
//        summonerList.add("    游戏中ID     " + loginSummoner.getGameName() + "#" + loginSummoner.getTagLine());
//        summonerList.add("    原始ID       " + loginSummoner.getDisplayName());
//        summonerList.add("    当前等级      " + loginSummoner.getSummonerLevel());
//        summonerList.add("    升到下一级经验值百分比   ："
//                + loginSummoner.getXpSinceLastLevel() + "/" + loginSummoner.getXpUntilNextLevel()
//                + " (" + loginSummoner.getPercentCompleteForNextLevel() + "%)");
//        summonerList.add("    神话精粹数量：" + globalService.getMythicCount());
//
//        listView.setItems(summonerList);

    }
}
