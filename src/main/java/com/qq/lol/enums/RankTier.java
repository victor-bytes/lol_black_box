package com.qq.lol.enums;

import com.google.common.base.Enums;
import org.apache.commons.lang3.StringUtils;

/**
 * @Auther: null
 * @Date: 2023/11/30 - 11 - 30 - 19:36
 * @Description: 段位
 * @version: 1.0
 */
public enum RankTier {
 /*   RankTierIron        RankTier = "IRON"        // 黑铁
    RankTierBronze      RankTier = "BRONZE"      // 青铜
    RankTierSilver      RankTier = "SILVER"      // 白银
    RankTierGold        RankTier = "GOLD"        // 黄金
    RankTierPlatinum    RankTier = "PLATINUM"    // 白金
    RankTierDiamond     RankTier = "DIAMOND"     // 钻石
    RankTierMaster      RankTier = "MASTER"      // 大师
    RankTierGrandMaster RankTier = "GRANDMASTER" // 宗师
    RankTierChallenger  RankTier = "CHALLENGER"  // 王者*/
    IRON("黑铁"),
    BRONZE("青铜"),
    SILVER("白银"),
    GOLD("黄金"),
    PLATINUM("白金"),
    DIAMOND("钻石"),
    MASTER("大师"),
    GRANDMASTER("宗师"),
    CHALLENGER("王者"),
    NONE("未定级"),
    DEFAULT_TIER("未知段位");
    // 没有段位，客户端会传来空字符串 ""

    private String tier;

    RankTier(String tier) {
        this.tier = tier;
    }

    public String getTier() {
        return this.tier;
    }

    public static RankTier getEnumIfPresent(String enumName) {
       // 未定级判断
       if(StringUtils.equals("", enumName))
          return NONE;

       RankTier rankTier = Enums.getIfPresent(RankTier.class, enumName).orNull();
       if(rankTier == null)
          return DEFAULT_TIER;
       else
          return RankTier.valueOf(enumName);
    }
}
