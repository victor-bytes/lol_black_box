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

    private ControllerManager(){}

}
