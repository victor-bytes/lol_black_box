package com.qq.lol.frame.controller;

import com.qq.lol.core.services.GlobalService;
import com.qq.lol.core.services.LolHeroService;
import com.qq.lol.core.services.LolPlayerService;
import com.qq.lol.core.services.RankService;
import com.qq.lol.core.services.impl.LolHeroServiceImpl;
import com.qq.lol.core.services.impl.LolPlayerServiceImpl;
import com.qq.lol.core.services.impl.RankServiceImpl;
import com.qq.lol.dto.RankDto;
import com.qq.lol.dto.SummonerInfoDto;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * @Auther: null
 * @Date: 2023/12/18 - 12 - 18 - 22:03
 * @Description: 主页控制器
 * @version: 1.0
 */
@Data
public class MainPageController {
    private static final GlobalService globalService = GlobalService.getGlobalService();
    private static final RankService rankService = RankServiceImpl.getRankService();
    private static final LolPlayerService lolPlayerService = LolPlayerServiceImpl.getLolPlayerService();
    private static final LolHeroService lolHeroService = LolHeroServiceImpl.getLolHeroService();

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Button refreshSummoner;

    @FXML
    private ImageView summonerIcon;

    @FXML
    private TextField summonerID;

    @FXML
    private Label rankSolo;

    @FXML
    private Label rankFlex;

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
    private Label ownedHeroCount;

    @FXML
    public void initialize() {
        ControllerManager.mainPageController = this;
    }

    // 填充召唤师信息
    public void showMainPage(SummonerInfoDto loginSummoner) {
        summonerIcon.setImage(loginSummoner.getProfileIcon());  // Icon

        String platformId = loginSummoner.getPlatformId();
        if (StringUtils.equals("HN1", platformId)) {
            platformId = "艾欧尼亚";
        }
        platform.setText("当前大区-" + platformId);             // 大区
        summonerID.setText(loginSummoner.getGameName() + "#" + loginSummoner.getTagLine()); // ID
        originID.setText(loginSummoner.getDisplayName());                                   // 原 ID
        summonerLevel.setText("Lv." + loginSummoner.getSummonerLevel());                    // 等级
        levelPercent.setText(loginSummoner.getXpSinceLastLevel() + "/" + loginSummoner.getXpUntilNextLevel()
                + " (" + loginSummoner.getPercentCompleteForNextLevel() + "%)");            // 等级百分比
        mythicCount.setText("" + GlobalService.getMythicCount());                           // 神话精粹数量
        // 填充段位
        List<RankDto> ranks = rankService.getRankByPuuid(loginSummoner.getPuuid());
        String rankSOLO = null;
        String rankFlEX = null;
        for (RankDto rankDto : ranks) {
            if(StringUtils.equals("单双排", rankDto.getQueueType())) {
                rankSOLO = "单双排-" + getRankString(rankDto);
                continue;
            }
            if(StringUtils.equals("灵活组排", rankDto.getQueueType())) {
                rankFlEX = "灵活组排-" + getRankString(rankDto);
            }
        }
        rankSolo.setText(rankSOLO);     // 段位
        rankFlex.setText(rankFlEX);

        Integer ownedCount = lolHeroService.getOwnedHeroCount(loginSummoner.getSummonerId());
        Integer heroCount = lolHeroService.getHeroCount();
        ownedHeroCount.setText("已拥有英雄数量：" + ownedCount + "/" + (heroCount - 1));  // 已拥有英雄总数
    }

    private String getRankString(RankDto rankDto) {
        String rank;
        double wins = rankDto.getWins();
        double losses = rankDto.getLosses();
        // 是否再打定位赛
        if(rankDto.isProvisional()) {
            rank = "定位赛-" + rankDto.getTier() + rankDto.getDivision()
                    + " " + rankDto.getLeaguePoints() + "胜点"
                    + "（" + rankDto.getWins() + "胜" + rankDto.getLosses() + "负）";
        } else {
            rank = rankDto.getTier() + rankDto.getDivision()
                    + " " + rankDto.getLeaguePoints() + "胜点"
                    + "  历史最高-" + rankDto.getHighestTier() + rankDto.getHighestDivision() +
                    " (" + (int)wins + "胜" + (int)losses + "负 胜率"
                    + (int)((wins / (wins + losses)) * 100) + "%)";
        }
        if(!StringUtils.equals(null, rankDto.getWarnings()))
            rank = rank.concat(" 即将掉段警告！");

        return rank;
    }

    // 刷新召唤师信息
    public void refresh() {
            System.out.println("刷新召唤师信息 按钮被触发");
            showMainPage(globalService.refreshSummoner());
    }
}
