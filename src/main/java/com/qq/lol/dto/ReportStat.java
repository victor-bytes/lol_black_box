package com.qq.lol.dto;

import lombok.Data;

import java.util.Arrays;
import java.util.List;

/**
 * @Auther: null
 * @Date: 2024/6/12 - 06 - 12 - 23:14
 * @Description: 举报玩家参数
 * @version: 1.0
 */
@Data
public class ReportStat {

    // ee639917-6a3c-5726-949f-537d341e5022用和横杠隔开了，有可能提交不隔开，返回隔开
    // 7f40c39d-e41f-5b8e-bbcb-11995b2ba858
    private String offenderPuuid;

    // 经过混淆处理后的违规者的 PUUID
    private String obfuscatedOffenderPuuid;

    public ReportStat(String offenderPuuid, String obfuscatedOffenderPuuid, 
                      List<String> categories, String gameId, String offenderSummonerId, String comment) {
        this.offenderPuuid = offenderPuuid;
        this.obfuscatedOffenderPuuid = obfuscatedOffenderPuuid;
        this.categories = categories;
        this.gameId = gameId;
        this.offenderSummonerId = offenderSummonerId;
        this.comment = comment;
    }

    public ReportStat(String offenderPuuid, String gameId, String offenderSummonerId) {
        this(offenderPuuid, "", Arrays.asList(
                "NEGATIVE_ATTITUDE",
                "VERBAL_ABUSE",
                "LEAVING_AFK",
                "INTENTIONAL_FEEDING",
                "HATE_SPEECH",
                "CHEATING",
                "INAPPROPRIATE_NAME",
                "THIRD_PARTY_TOOLS",
                "ASSISTING_ENEMY_TEAM"
                ), gameId, offenderSummonerId, "");
    }

    /**
     * 举报理由多选
     * Negative Attitude 消极态度
     * Verbal Abuse 言语辱骂
     *
     *  "categories": [
     *   "NEGATIVE_ATTITUDE", // 消极态度
     *   "VERBAL_ABUSE", // 言语辱骂
     *   "LEAVING_AFK", // 离开游戏 / 挂机
     *   "INTENTIONAL FEEDING", // 蓄意送人头
     *   "HATE SPEECH", // 仇恨言论
     *   "CHEATING", // 作弊
     *   "OFFENSIVE OR INAPPROPRIATE NAME" // 攻击性或不适当的名字
     *
     *   "NEGATIVE_ATTITUDE", 消极态度
     *             "VERBAL_ABUSE", 言语辱骂
     *             "LEAVING_AFK", 离开游戏 / 挂机
     *             "ASSISTING_ENEMY_TEAM", 协助敌方团队
     *             "THIRD_PARTY_TOOLS", 使用第三方工具
     *             "INAPPROPRIATE_NAME" 不适当的名字
     * ]
     */
    private List<String> categories;

    private String gameId;

    private String offenderSummonerId;

    // 额外的举报理由
    private String comment;
}
