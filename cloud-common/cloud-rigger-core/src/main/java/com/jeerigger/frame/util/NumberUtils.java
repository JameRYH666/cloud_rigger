package com.jeerigger.frame.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * @projectName: spring-study-demo
 * @packageName: com.baihy
 * @description:
 * @author: huayang.bai
 * @date: 2020/3/24 11:17
 */
public class NumberUtils {

    private NumberUtils() {
        throw new RuntimeException(this.getClass().getName() + "是工具类，不能被实例化！！");
    }

    private static BigDecimal THOUSAND = new BigDecimal(1000.00D);
    private static BigDecimal WAN = new BigDecimal(10_000.00D);
    private static BigDecimal TEN_WAN = new BigDecimal(100_000.00D);
    private static BigDecimal MILLION = new BigDecimal(1_000_000.00D);
    private static BigDecimal QIAN_WAN = new BigDecimal(10_000_000.00D);
    private static BigDecimal YI = new BigDecimal(100_000_000.00D);
    private static BigDecimal BILLION = new BigDecimal(1_000_000_000.00D);

    /**
     * 自定义格式
     *
     * @param pattern
     * @param value
     * @return
     */
    public static String format(String pattern, Double value) {
        return new DecimalFormat(pattern).format(value);
    }

    /**
     * 格式：#.00
     *
     * @param value
     * @return
     */
    public static String formatToFull(Double value) {
        return format("0.00", value);
    }

    /**
     * 格式：#.##
     *
     * @param value
     * @return
     */
    public static String formatToShort(Double value) {
        return format("0.##", value);
    }

    /**
     * 转换成千，且四舍五入保留两位小数
     *
     * @param number
     * @return
     */
    public static Double toThousand(Number number) {
        return toThousand(number, 2);
    }

    /**
     * 转换成千，且四舍五入保留指定位小数
     *
     * @param number
     * @param scale
     * @return
     */
    public static Double toThousand(Number number, int scale) {
        BigDecimal source = new BigDecimal(number.doubleValue());
        return source.divide(THOUSAND, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 转换成万,且四舍五入保留2位小数
     *
     * @param number
     * @return
     */
    public static Double toWan(Number number) {
        return toWan(number, 2);
    }

    /**
     * 转换成万,且四舍五入保留指定位小数
     *
     * @param number
     * @param scale
     * @return
     */
    public static Double toWan(Number number, int scale) {
        BigDecimal source = new BigDecimal(number.doubleValue());
        return source.divide(WAN, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 转换成十万,且四舍五入保留2位小数
     *
     * @param number
     * @return
     */
    public static Double toTenWan(Number number) {
        return toTenWan(number, 2);
    }

    /**
     * 转换成十万,且四舍五入保留指定位小数
     *
     * @param number
     * @param scale
     * @return
     */
    public static Double toTenWan(Number number, int scale) {
        BigDecimal source = new BigDecimal(number.doubleValue());
        return source.divide(TEN_WAN, 2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 转换成百万,且四舍五入保留2位小数
     *
     * @param number
     * @return
     */
    public static Double toMillion(Number number) {
        return toMillion(number, 2);
    }

    /**
     * 转换成百万,且四舍五入保留指定位小数
     *
     * @param number
     * @param scale
     * @return
     */
    public static Double toMillion(Number number, int scale) {
        BigDecimal source = new BigDecimal(number.doubleValue());
        return source.divide(MILLION, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 转换成千万,且四舍五入保留2位小数
     *
     * @param number
     * @return
     */
    public static Double toQianWan(Number number) {
        return toQianWan(number, 2);
    }

    /**
     * 转换成千万,且四舍五入保留指定位小数
     *
     * @param number
     * @param scale
     * @return
     */
    public static Double toQianWan(Number number, int scale) {
        BigDecimal source = new BigDecimal(number.doubleValue());
        return source.divide(QIAN_WAN, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 转换成亿,且四舍五入2位小数
     *
     * @param number
     * @return
     */
    public static Double toYi(Number number) {
        return toYi(number, 2);
    }

    /**
     * 转换成亿,且四舍五入保留指定位小数
     *
     * @param number
     * @param scale
     * @return
     */
    public static Double toYi(Number number, int scale) {
        BigDecimal source = new BigDecimal(number.doubleValue());
        return source.divide(YI, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 转换成十亿,且四舍五入保留2位小数
     *
     * @param number
     * @return
     */
    public static Double toBillion(Number number) {
        return toBillion(number, 2);
    }

    /**
     * 转换成十亿,且四舍五入保留指定位小数
     *
     * @param number
     * @return
     */
    public static Double toBillion(Number number, int scale) {
        BigDecimal source = new BigDecimal(number.doubleValue());
        return source.divide(BILLION, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static BigDecimal getTHOUSAND() {
        return THOUSAND;
    }

    public static BigDecimal getWAN() {
        return WAN;
    }

    public static BigDecimal getTenWan() {
        return TEN_WAN;
    }

    public static BigDecimal getMILLION() {
        return MILLION;
    }

    public static BigDecimal getQianWan() {
        return QIAN_WAN;
    }

    public static BigDecimal getYI() {
        return YI;
    }

    public static BigDecimal getBILLION() {
        return BILLION;
    }
}
