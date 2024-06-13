package com.qq.lol.utils;

import com.qq.lol.core.services.GlobalService;
import com.qq.lol.dto.LolClientDto;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Date: 2023/11/29 - 11 - 29 - 14:11
 * @Description: 主要用户获取 riot客户端的 port、token
 * @version: 1.0
 */
public class ProcessUtil {
    public static Pattern appPortPattern = Pattern.compile("--app-port=(\\d+)");
    public static Pattern tokenPattern = Pattern.compile("--remoting-auth-token=([\\w-]+)");

    private static final ProcessUtil processUtil = new ProcessUtil();

    // 私有构造，该类只会被用到 getClientProcess()方法
    private ProcessUtil(){

    }

    // 饿汉式单例模式
    public static ProcessUtil getProcessUtil() {
        return processUtil;
    }

    /**
     * 通过进程名查询出进程的启动命令,解析出需要的客户端token和端口
     * 如果游戏客户端未启动，则 LolClientDto的两个属性值均为 null
     *
     * TODO 国服无法获取 token、port可能是需要管理员权限，也可能是因为输出结果中中文乱码无法解析
     */

    public static LolClientDto getClientProcess() throws IOException {
        String cmd = "WMIC PROCESS WHERE name=\"LeagueClientUx.exe\" GET commandline";
        BufferedReader reader = null;
        Process process = null;
        LolClientDto leagueClientBO = new LolClientDto();
        try {
            process = Runtime.getRuntime().exec(cmd);
            // windows 命令必须gbk编码
            reader = new BufferedReader(new InputStreamReader(process.getInputStream(), "gb2312"));
            String line;

            while ((line = reader.readLine()) != null) {
                Matcher appPortMatcher = appPortPattern.matcher(line);
                Matcher tokenPatternMatcher = tokenPattern.matcher(line);
                if (tokenPatternMatcher.find()) {
                    leagueClientBO.setToken(tokenPatternMatcher.group(1));
                }
                if (appPortMatcher.find()) {
                    leagueClientBO.setPort(appPortMatcher.group(1));
                }

            }
            System.out.print("=== Port ：" + leagueClientBO.getPort());
            System.out.println("=== Token ：" + leagueClientBO.getToken() + " ===");
            GlobalService.setInitRecorder(StandardOutTime.getCurrentTime() + " port=" + leagueClientBO.getPort() +
                    ", token=" + leagueClientBO.getToken() + "--");

            return leagueClientBO;
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException ignored) {
                }
            }
            if (process != null) {
                process.getInputStream().close();
                process.getErrorStream().close();
                process.getOutputStream().close();
            }
        }

    }

}
