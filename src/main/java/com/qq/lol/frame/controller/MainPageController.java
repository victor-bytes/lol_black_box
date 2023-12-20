package com.qq.lol.frame.controller;

import com.qq.lol.app.services.GlobalService;
import com.qq.lol.app.services.LolPlayerService;
import com.qq.lol.app.services.RankService;
import com.qq.lol.app.services.impl.LolPlayerServiceImpl;
import com.qq.lol.app.services.impl.RankServiceImpl;
import com.qq.lol.dto.RankDto;
import com.qq.lol.dto.SummonerInfoDto;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * @Auther: null
 * @Date: 2023/12/18 - 12 - 18 - 22:03
 * @Description: 主页控制器
 * @version: 1.0
 */
public class MainPageController {
    private static final GlobalService globalService = GlobalService.getGlobalService();
    private static final RankService rankService = RankServiceImpl.getRankService();
    private static final LolPlayerService lolPlayerService = LolPlayerServiceImpl.getLolPlayerService();

    @FXML
    public AnchorPane anchorPane;

    @FXML
    private Button refreshSummoner;

    @FXML
    private ImageView summonerIcon;

    @FXML
    private Label summonerID;

    @FXML
    private Label rank;

    @FXML
    private Label summonerLevel;

    @FXML
    private Label levelPercent;

    @FXML
    private Label mythicCount;

    @FXML
    private Label originID;

    @FXML
    private Label platform;

    @FXML
    public void initialize() {
        ControllerManager.mainPageController = this;
    }

    // 填充召唤师信息
    public void showMainPage(SummonerInfoDto loginSummoner) {
        summonerIcon.setImage(loginSummoner.getProfileIcon());

        String platformId = loginSummoner.getPlatformId();
        if (StringUtils.equals("HN1", platformId)) {
            platformId = "艾欧尼亚";
        }
        platform.setText("当前大区-" + platformId);
        summonerID.setText(loginSummoner.getGameName() + "#" + loginSummoner.getTagLine());
        originID.setText(loginSummoner.getDisplayName());
        summonerLevel.setText(loginSummoner.getSummonerLevel());
        levelPercent.setText(loginSummoner.getXpSinceLastLevel() + "/" + loginSummoner.getXpUntilNextLevel()
                + " (" + loginSummoner.getPercentCompleteForNextLevel() + "%)");
        mythicCount.setText("" + globalService.getMythicCount());
        // 填充段位
        List<RankDto> ranks = rankService.getRankByPuuid(loginSummoner.getPuuid());
        String rankSOLO = null;
        String rankFlex = null;
        for (RankDto rankDto : ranks) {
            if(StringUtils.equals("单双排", rankDto.getQueueType())) {
                rankSOLO = "单双排-" + rankDto.getTier() + rankDto.getDivision() + " 历史最高-" +
                        rankDto.getHighestTier() + rankDto.getHighestDivision();
            }
            if(StringUtils.equals("灵活组排", rankDto.getQueueType())) {
                rankFlex = "灵活组排-" + rankDto.getTier() + rankDto.getDivision() + " 历史最高-" +
                        rankDto.getHighestTier() + rankDto.getHighestDivision();
            }
        }
        rank.setText(rankSOLO + " | " + rankFlex);

    }

    // 刷新召唤师信息
    public void refresh() {
//        refreshSummoner.setOnMouseClicked(event -> {
            System.out.println("刷新召唤师信息 按钮被触发");
            showMainPage(globalService.refreshSummoner());
//        });
    }
}
