package com.qq.lol.enums;

import com.google.common.base.Enums;

/**
 * @Auther: null
 * @Date: 2024/6/13 - 06 - 13 - 23:44
 * @Description: 游戏大区
 * @version: 1.0
 */
public enum ServerName {
//    "NJ100": "联盟一区", "GZ100": "联盟二区", "CQ100": "联盟三区", "TJ100": "联盟四区", "TJ101": "联盟五区",
//    "HN10": "黑色玫瑰", "HN1": "艾欧尼亚", "BGP2": "峡谷之巅"

    NJ100("联盟一区"),
    GZ100("联盟二区"),
    CQ100("联盟三区"),
    TJ100("联盟四区"),
    TJ101("联盟五区"),
    HN10("黑色玫瑰"),
    HN1("艾欧尼亚"),
    BGP2("峡谷之巅"),
    TW2("台服"),
    DEFAULT_SERVER("未知大区")
    ;

    private String serverName;

    public String getServerName() {
        return serverName;
    }

    ServerName(String serverName) {
        this.serverName = serverName;
    }

    public static ServerName getEnumIfPresent(String enumName){
        ServerName serverName = Enums.getIfPresent(ServerName.class, enumName).orNull();
        if(serverName == null) {
            return DEFAULT_SERVER;
        } else {
            return serverName;
        }

    }
}
