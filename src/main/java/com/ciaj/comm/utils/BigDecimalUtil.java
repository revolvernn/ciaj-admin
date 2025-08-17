package com.ciaj.comm.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;

/**
 * @Description TODO
 * @Date 2020/10/19 17:39
 * @Created by ciaj.
 */
public class BigDecimalUtil {
    //保留整数位
    final private static int INTEGER_SCALE_SIZE = 0;
    //两位小数
    final private static int DOUBLE_SCALE_SIZE = 2;
    //三位小数
    final private static int THREE_SCALE_SIZE = 3;
    //四位小数
    final private static int FOUR_SCALE_SIZE = 4;
    //六位小数
    final private static int SIX_SCALE_SIZE = 6;

    public final static BigDecimal oneHundred = new BigDecimal(100L);

    public static BigDecimal ifNullSet0(BigDecimal in){
        if (in != null) {
            return in;
        }
        return BigDecimal.ZERO;
    }


       public static BigDecimal sum (BigDecimal ...in){
        BigDecimal result = BigDecimal.ZERO;
        for (int i=0; i<in.length;i++){
            result = result.add(ifNullSet0(in[i]));
        }
        return  result;
       }

    /**
     * a 减 b
     *
     * @param a
     * @param b
     * @return
     */
    public static BigDecimal subtract(BigDecimal a, BigDecimal... b) {
        BigDecimal reduce = b == null ? BigDecimal.ZERO : Safes.of(Arrays.asList(b)).reduce(BigDecimal.ZERO, BigDecimal::add);
        return a == null ? BigDecimal.ZERO.subtract(reduce) : a.subtract(reduce);
    }
    /**
     * a 加 b
     *
     * @param a
     * @param b
     * @return
     */
    public static BigDecimal add(BigDecimal a, BigDecimal... b) {
        BigDecimal reduce = b == null ? BigDecimal.ZERO : Safes.of(Arrays.asList(b)).reduce(BigDecimal.ZERO, BigDecimal::add);
        return a == null ? BigDecimal.ZERO.add(reduce) : a.add(reduce);
    }

    /**
     * 折扣值计算方式 折扣=(1-合作服务费总金额/业绩总额)x100%
     */
    public static BigDecimal discountCalculate(BigDecimal a, BigDecimal b) {
        if (a == null || b == null) {
            return BigDecimal.ZERO;
        }
        if (b.compareTo(BigDecimal.ZERO) == 0 || a.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }
        BigDecimal divide = a.divide(b, 6, BigDecimal.ROUND_DOWN);
        BigDecimal ratio = BigDecimal.ONE.subtract(divide).multiply(BigDecimal.TEN).setScale(2, BigDecimal.ROUND_DOWN);
        return ratio;
    }

    /**
     * 现金奖计算
     */
    public static BigDecimal rewardAmountCalculate(BigDecimal a, BigDecimal b, BigDecimal c, BigDecimal d) {
        if (a == null || b == null) {
            return BigDecimal.ZERO;
        }
        if (b.compareTo(BigDecimal.ZERO) == 0 || a.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }
        BigDecimal multiply = c.multiply(a.divide(b, 6, BigDecimal.ROUND_DOWN));
        BigDecimal amount = multiply.multiply(d).setScale(2, BigDecimal.ROUND_HALF_UP);
        return amount;
    }

    /**
     * 两个数相除(保留scale位小数，后面的舍掉)
     */
    public static BigDecimal divideRoundDown(BigDecimal a, BigDecimal b, int scale) {
        if (b.compareTo(BigDecimal.ZERO) == 0 || a.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }
        return a.divide(b, scale, BigDecimal.ROUND_DOWN);
    }

    /**
     *  保留2位小数(向下舍入)
     *
     * @author Healer
     * @param b :
     * @return java.math.BigDecimal
     * @date 2020/10/19 18:38
     */
    public static BigDecimal setDoubleScaleDown(BigDecimal b) {
        return b.setScale(DOUBLE_SCALE_SIZE, BigDecimal.ROUND_DOWN);
    }
    /**
     *  保留4位小数(向下舍入)
     *
     * @author Healer
     * @param b :
     * @return java.math.BigDecimal
     * @date 2020/10/19 18:38
     */
    public static BigDecimal setFourScaleDown(BigDecimal b) {
        return b.setScale(FOUR_SCALE_SIZE, BigDecimal.ROUND_DOWN);
    }
    /**
     *  保留6位小数(向下舍入)
     *
     * @author Healer
     * @param b :
     * @return java.math.BigDecimal
     * @date 2020/10/19 18:38
     */
    public static BigDecimal setSixScaleDown(BigDecimal b) {
        return b.setScale(SIX_SCALE_SIZE, BigDecimal.ROUND_DOWN);
    }
    /**
     *  保留2位小数(四舍五入)
     *
     * @author Healer
     * @param b :
     * @return java.math.BigDecimal
     * @date 2020/10/19 18:38
     */
    public static BigDecimal setDoubleScaleRound(BigDecimal b) {
        return b.setScale(DOUBLE_SCALE_SIZE, BigDecimal.ROUND_HALF_UP);
    }
    /**
     *  保留4位小数(四舍五入)
     *
     * @author Healer
     * @param b :
     * @return java.math.BigDecimal
     * @date 2020/10/19 18:38
     */
    public static BigDecimal setFourScaleRound(BigDecimal b) {
        return b.setScale(FOUR_SCALE_SIZE, BigDecimal.ROUND_HALF_UP);
    }
    /**
     *  保留6位小数(四舍五入)
     *
     * @author Healer
     * @param b :
     * @return java.math.BigDecimal
     * @date 2020/10/19 18:38
     */
    public static BigDecimal setSixScaleRound(BigDecimal b) {
        return b.setScale(SIX_SCALE_SIZE, BigDecimal.ROUND_HALF_UP);
    }

    /**
     *  保留整数位(四舍五入)
     *
     * @author Healer
     * @param b :
     * @return java.math.BigDecimal
     * @date 2020/10/19 18:38
     */
    public static BigDecimal setIntegerScaleRound(BigDecimal b) {
        return b.setScale(INTEGER_SCALE_SIZE, BigDecimal.ROUND_HALF_UP);
    }
    /**
     *  保留2位小数(向上舍入)
     *
     * @author Healer
     * @param b :
     * @return java.math.BigDecimal
     * @date 2020/10/19 18:38
     */
    public static BigDecimal setDoubleScaleUp(BigDecimal b) {
        return b.setScale(DOUBLE_SCALE_SIZE, BigDecimal.ROUND_UP);
    }
    /**
     *  保留4位小数(向上舍入)
     *
     * @author Healer
     * @param b :
     * @return java.math.BigDecimal
     * @date 2020/10/19 18:38
     */
    public static BigDecimal setFourScaleUp(BigDecimal b) {
        return b.setScale(FOUR_SCALE_SIZE, BigDecimal.ROUND_UP);
    }
    /**
     *  保留6位小数(向上舍入)
     *
     * @author Healer
     * @param b :
     * @return java.math.BigDecimal
     * @date 2020/10/19 18:38
     */
    public static BigDecimal setSixScaleUp(BigDecimal b) {
        return b.setScale(SIX_SCALE_SIZE, BigDecimal.ROUND_UP);
    }
    /**
     *  断言请求(如果对获得精确结果的操作指定此舍入模式，则抛出ArithmeticException)
     *  断言请求的操作具有精确的结果，因此不需要舍入
     *
     * @author Healer
     * @param b :
     * @return java.math.BigDecimal
     * @date 2020/10/20 10:14
     */
    public static BigDecimal setDoubleScaleUnNecessary(BigDecimal b){
        return b.setScale(DOUBLE_SCALE_SIZE);
    }
    /**
     *  断言请求(如果对获得精确结果的操作指定此舍入模式，则抛出ArithmeticException)
     *  断言请求的操作具有精确的结果，因此不需要舍入
     *
     * @author Healer
     * @param b :
     * @return java.math.BigDecimal
     * @date 2020/10/20 10:14
     */
    public static BigDecimal setFourScaleUnNecessary(BigDecimal b){
        return b.setScale(FOUR_SCALE_SIZE);
    }
    /**
     *  断言请求(如果对获得精确结果的操作指定此舍入模式，则抛出ArithmeticException)
     *  断言请求的操作具有精确的结果，因此不需要舍入
     *
     * @author Healer
     * @param b :
     * @return java.math.BigDecimal
     * @date 2020/10/20 10:14
     */
    public static BigDecimal setSixScaleUnNecessary(BigDecimal b){
        return b.setScale(SIX_SCALE_SIZE);
    }
    /**
     *   两个数相除(四舍五入保留2位小数)
     *
     * @author Healer
     * @param a :
     * @param b :
     * @return java.math.BigDecimal
     * @date 2020/10/20 10:51
     */
    public static BigDecimal doubleDivide(BigDecimal a,BigDecimal b){
        return a.divide(b,DOUBLE_SCALE_SIZE, RoundingMode.HALF_UP);
    }
    /**
     *   两个数相除(四舍五入保留3位小数)
     *
     * @author Healer
     * @param a :
     * @param b :
     * @return java.math.BigDecimal
     * @date 2020/10/20 10:51
     */
    public static BigDecimal threeDivide(BigDecimal a,BigDecimal b){
        if (b.compareTo(BigDecimal.ZERO) == 0){
            return BigDecimal.ZERO;
        }
        return a.divide(b,THREE_SCALE_SIZE, RoundingMode.HALF_UP);
    }
    /**
     *   两个数相除(四舍五入保留4位小数)
     *
     * @author Healer
     * @param a :
     * @param b :
     * @return java.math.BigDecimal
     * @date 2020/10/20 10:51
     */
    public static BigDecimal fourDivide(BigDecimal a,BigDecimal b){
        return a.divide(b,FOUR_SCALE_SIZE, RoundingMode.HALF_UP);
    }
    /**
     *   两个数相除(四舍五入保留6位小数)
     *
     * @author Healer
     * @param a :
     * @param b :
     * @return java.math.BigDecimal
     * @date 2020/10/20 10:51
     */
    public static BigDecimal sixDivide(BigDecimal a,BigDecimal b){
        return a.divide(b,SIX_SCALE_SIZE, RoundingMode.HALF_UP);
    }



}
