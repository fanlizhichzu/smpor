package com.spring.smpor.common.util;

/**
 * @Description:
 * @Auther: fanlz
 * @Date: 2021/12/3 14:35
 */
public class RegionUtil {
    public static void main(String[] args) {
        String regionCode = "330000000000";
        System.out.println(RegionUtil.getRegionCode(regionCode));
    }

    public static String getRegionCode(String regionCode) {
        if (regionCode.matches("[1-9][1-7]0{4}|[1-9][1-7]0{10}")) {
            //全省的汇总
            regionCode = regionCode.substring(0, 2);
        } else if (regionCode.matches("[1-9][1-7]\\d{2}0{2}|[1-9][1-7]\\d{2}0{8}")) {
            //全市的汇总
            regionCode = regionCode.substring(0, 4);
        } else if (regionCode.matches("[1-9][1-7]\\d{4}0{6}")) {
            //全县的汇总
            regionCode = regionCode.substring(0, 6);
        } else if (regionCode.matches("[1-9][1-7]\\d{4}\\d{3}0{3}")) {
            //全乡镇的汇总
            regionCode = regionCode.substring(0, 9);
        } else if (regionCode.matches("[1-9][1-7]\\d{4}\\d{6}")) {
            //社区、村汇总
            regionCode = regionCode.substring(0, 12);
        }
        return regionCode;
    }
}
