package com.qq.lol.frame.controller;

/**
 * @Auther: null
 * @Date: 2023/12/19 - 12 - 19 - 21:01
 * @Description: 保存所有控制器唯一实例
 * @version: 1.0
 */
public class ControllerManager {

//    private static final Map<String, Object> controllerMap = new HashMap<>(); 使用 map存放所有 controller也可以

    public static MainPageController mainPageController;

    public static GameHistoryPageController gameHistoryPageController;

    public static MainWindowController mainWindowController;

    public static AddBlackListController addBlackListController;

    public static ToolsController toolsController;

    public static SettingsController settingsController;

    public static BlackListController blackListController;

    public static RunningRecorderController runningRecorderController;

    /**
     * TODO 目前采用的是启动 App时一次性初始化全部 fxml，页面比较多的时候可以选在 ControllerManager中初始化，
     * 在第一次获取 Controller时初始化 fxml，类似于懒汉式单例模式
     */

    private ControllerManager(){}

}
