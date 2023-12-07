package com.qq.lol.utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Date;
import java.util.TimeZone;

/**
 * @Auther: null
 * @Date: 2023/12/1 - 12 - 01 - 15:51
 * @Description: 格式化输出时间样式
 * @version: 1.0
 */
public class StandardOutTime {

    // 私有构造，不允许实例化
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

    // 将 UTC 转成北京时间
    public static String utcToBeijingTime(String utcTime) {
        // UTC 时间格式
        SimpleDateFormat timeMode1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        // 北京时间格式
        SimpleDateFormat timeMode2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        timeMode1.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date date = null;
        try {
            date = timeMode1.parse(utcTime);
        } catch (ParseException e) {
            System.out.println("将 UTC 转成北京时间异常");
            e.printStackTrace();
        }

        return timeMode2.format(date);
    }

    /**
     * @Description: java.sql.Timestamp转 String
     * @param time:
     * @param strFormat:
     * @return java.lang.String
     * @Auther: null
     * @Date: 2023/12/7 - 18:09
     */
    public static String timestampToStr(Timestamp time, String strFormat) {
        DateFormat df = new SimpleDateFormat(strFormat);
        String str =df.format(time);
        return str;
    }
}
