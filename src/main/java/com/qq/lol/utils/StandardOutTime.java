package com.qq.lol.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

/**
 * @Auther: null
 * @Date: 2023/12/1 - 12 - 01 - 15:51
 * @Description: 格式化输出时间样式
 * @version: 1.0
 */
public class StandardOutTime {

    private StandardOutTime() {

    }

    // 返回当前时间
    public static String getCurrentTime() {
        // FormatStyle.MEDIUM: 2020-6-15 15:17:42
        DateTimeFormatter df2 = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);
        LocalDateTime currentTime = LocalDateTime.now();

        String str = df2.format(currentTime);

        return str + "  ";
    }
}
