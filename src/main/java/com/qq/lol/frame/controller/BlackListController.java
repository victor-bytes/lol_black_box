package com.qq.lol.frame.controller;

import com.qq.lol.core.services.BlackListService;
import com.qq.lol.core.services.LolHeroService;
import com.qq.lol.core.services.impl.BlackListServiceImpl;
import com.qq.lol.core.services.impl.LolHeroServiceImpl;
import com.qq.lol.dto.BlackListDto;
import com.qq.lol.dto.BlackPlayerDto;
import com.qq.lol.dto.PageResult;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.util.Callback;
import lombok.Data;

import java.util.List;

/**
 * @Auther: null
 * @Date: 2023/12/27 - 12 - 27 - 19:18
 * @Description: 黑名单页
 * @version: 1.0
 */
@Data
public class BlackListController {

    private static final BlackListService blackListService = BlackListServiceImpl.getBlackListService();

    private static final LolHeroService lolHeroService = LolHeroServiceImpl.getLolHeroService();

    @FXML
    private BorderPane borderPane;

    @FXML
    private VBox vBox;

    @FXML
    private HBox hBoxOne;

//    @FXML
//    private TableView<BlackListDto> tableView;

    @FXML
    private Label pageMsg;

    // 当前页码，默认 0页
    private Long currentPageNo = 0L;

    // 总共多少页
    private Long pageCounts;

    // 默认一页查询18条
    private int NumberPagePer = 18;

    @FXML
    public void initialize() {
        ControllerManager.blackListController = this;
        // 首次显示首页
        pageUpdate(currentPageNo);
    }

    // 翻页
    private void pageUpdate(Long pageNo) {
        PageResult<BlackPlayerDto> pageResult = showBlackList(pageNo);
        updatePageMsg(pageResult);
    }

    // 填充黑名单列表
    private PageResult<BlackPlayerDto> showBlackList(Long pageNo) {
        // 填充黑名单
        PageResult<BlackPlayerDto> result = blackListService.selectBlackPlayerByPage(pageNo, NumberPagePer);
        // 当前页有多少条数据
        long pageSize = result.getPageSize();
        // 页面数据
        List<BlackPlayerDto> items = result.getItems();
        ObservableList<BlackListDto> blackObserve = FXCollections.observableArrayList();
        for (int i = 0; i < items.size(); i++) {
            BlackPlayerDto item = items.get(i);
            String puuid = item.getPuuid();
            Integer kills = item.getKills();
            Integer deaths = item.getDeaths();
            Integer assists = item.getAssists();
            Integer win = item.getWin();
            String gameName = item.getGameName();
            String tagLine = item.getTagLine();
            String meetCount = item.getMeetCount();
            String reason = item.getReason();
            String platformId = item.getPlatformId();
            String selectedPosition = item.getSelectedPosition();
//            HeroDto champion = lolHeroService.getHeroInfoByChampionId(item.getChampionId());
            Image championIcon = lolHeroService.getChampionIcon(item.getChampionId());
            String last_update_time = item.getLast_update_time();
            ImageView icon = new ImageView(championIcon);

            BlackListDto blackListDto = new BlackListDto(puuid, i + 1 + currentPageNo * NumberPagePer,
                    gameName + "#" + tagLine, icon, kills + "/" + deaths + "/" + assists,
                    reason, meetCount, platformId, selectedPosition, last_update_time, win);
            blackObserve.add(blackListDto);
        }

        // 先清空，再添加
        TableView<BlackListDto> tableView = new TableView<>();
        vBox.getChildren().clear();
        tableView.getItems().clear();
        tableView.getColumns().clear();
        tableView.getItems().addAll(blackObserve);
        vBox.getChildren().add(tableView);
        tableView.setPrefHeight(570.0);

        {
            // 输赢
            tableView.setRowFactory(new Callback<TableView<BlackListDto>, TableRow<BlackListDto>>() {
                @Override
                public TableRow<BlackListDto> call(TableView<BlackListDto> param) {
                    TableRow<BlackListDto> row = new TableRow<BlackListDto>(){
                        @Override
                        protected void updateItem(BlackListDto item, boolean empty) {
                            super.updateItem(item, empty);
                            if(!empty && item != null) {
                                // 根据输赢，每行显示不同颜色
                                if(item.getWin() == 0) {
                                    this.setStyle("-fx-background-color:#FF6347");
                                } else if(item.getWin() == 1){
                                    this.setStyle("-fx-background-color:#98FB98");
                                }
                                // 添加鼠标悬浮提示
                                this.setTooltip(new Tooltip(item.getName() + " : " +item.getReason()));
                            }
                            // 设置边框
                            this.setBorder(new Border(new BorderStroke(Paint.valueOf("#808080"),
                                    BorderStrokeStyle.SOLID,
                                    new CornerRadii(1),
                                    new BorderWidths(1))));
                        }
                    };

                    return row;
                }
            });

            TableColumn<BlackListDto, Number> tOrder = new TableColumn<>("序号");
            // 两种方法都可以在tableView中显示数据，二选一
            // 采用直接加载数据方式
            tOrder.setCellValueFactory(param -> {
                SimpleLongProperty order = new SimpleLongProperty(param.getValue().getOrder());
                return order;
            });
//            tOrder.setCellValueFactory(new PropertyValueFactory<>("order"));
            tableView.getColumns().add(tOrder);

            TableColumn<BlackListDto, String> tPuuid = new TableColumn<>("puuid");
            tPuuid.setCellValueFactory(new PropertyValueFactory<>("puuid"));
            tableView.getColumns().add(tPuuid);

            TableColumn<BlackListDto, String> tName = new TableColumn<>("游戏内ID");
            tName.setCellValueFactory(new PropertyValueFactory<>("name"));
            tableView.getColumns().add(tName);

            TableColumn<BlackListDto, String> tPosition = new TableColumn<>("位置");
            tPosition.setCellValueFactory(new PropertyValueFactory<>("selectedPosition"));
            tableView.getColumns().add(tPosition);

            TableColumn<BlackListDto, ImageView> tHero = new TableColumn<>("所用英雄");
            tHero.setCellValueFactory(param -> {
                SimpleObjectProperty<ImageView> icon = new SimpleObjectProperty<>(param.getValue().getIcon());
                ImageView imageView = icon.get();   // 拿到头像组件
                // 设置英雄头像大小
                imageView.setFitHeight(25);
                imageView.setFitWidth(25);
                Rectangle rectangle = new Rectangle(imageView.getFitWidth(), imageView.getFitHeight());
                rectangle.setArcHeight(50);
                rectangle.setArcWidth(50);
                imageView.setClip(rectangle);
                imageView.setStyle("-fx-alignment:center");

                return icon;
            });
            tableView.getColumns().add(tHero);

            TableColumn<BlackListDto, String> tKDA = new TableColumn<>("KDA");
            tKDA.setCellValueFactory(new PropertyValueFactory<>("kda"));
            tableView.getColumns().add(tKDA);

            TableColumn<BlackListDto, String> tReason = new TableColumn<>("拉黑原因");
            tReason.setCellValueFactory(new PropertyValueFactory<>("reason"));
            tableView.getColumns().add(tReason);

            TableColumn<BlackListDto, String> tMeet = new TableColumn<>("遇见次数");
            tMeet.setCellValueFactory(new PropertyValueFactory<>("meetCount"));
            tableView.getColumns().add(tMeet);

            TableColumn<BlackListDto, String> tPlatform = new TableColumn<>("大区");
            tPlatform.setCellValueFactory(new PropertyValueFactory<>("platformId"));
            tableView.getColumns().add(tPlatform);

            TableColumn<BlackListDto, String> tTime = new TableColumn<>("上次次遇见时间");
            tTime.setCellValueFactory(new PropertyValueFactory<>("last_update_time"));
            tableView.getColumns().add(tTime);
        }

        return result;
    }

    // 修改页码信息
    private void updatePageMsg(PageResult<BlackPlayerDto> result) {
        long page = result.getPage();   // 当前页码
        long counts = result.getCounts(); // 总共多少数据
        // 总共多少页，页码是 0 开始
        long countPage = (counts % NumberPagePer == 0) ? counts / NumberPagePer : (counts / NumberPagePer) + 1;
        pageCounts = countPage;

        // 要更新的页码
        long newPage = (page + 1) > pageCounts ? pageCounts : (page + 1);
        pageMsg.setText(newPage + "/" + countPage);
    }

    @FXML
    void pageDown(ActionEvent event) {
        System.out.println("下一页按钮 被点击，");
        currentPageNo = (currentPageNo + 1) >= pageCounts ? pageCounts - 1 : (currentPageNo + 1);
        pageUpdate(currentPageNo);
    }

    @FXML
    void pageHome(ActionEvent event) {
        System.out.println("首页按钮 被点击");
        currentPageNo = 0L;
        pageUpdate(currentPageNo);
    }

    @FXML
    void pageUp(ActionEvent event) {
        System.out.println("上一页按钮 被点击");
        currentPageNo = (currentPageNo - 1) < 1 ? 0 : (currentPageNo - 1);
        pageUpdate(currentPageNo);
    }

    @FXML
    void pageEnd(ActionEvent event) {
        System.out.println("末页按钮 被点击");
        currentPageNo = pageCounts - 1;
        pageUpdate(currentPageNo);
    }

    /**
     * @Description: 查询玩家信息，并判断是否在黑名单中
     * @param event:
     * @return void
     * @Auther: null
     * @Date: 2024/6/4 - 17:11
     */
    @FXML
    void queryPlayer(ActionEvent event) {
        System.out.println("检索玩家按钮 被点击");
        // todo 待开发
    }



}
