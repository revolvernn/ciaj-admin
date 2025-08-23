package com.ciaj.comm.utils;

import com.ciaj.comm.exception.BsRException;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import java.util.Collection;

/**
 * @Description
 * @Date 12/18/20 9:51 AM
 * @Created by ciaj.
 */
public class AssertUtil extends Assert {

    /**
     * 非true
     *
     * @param expression
     * @param message
     */
    public static void isTrue(@Nullable Boolean expression, String message) {
        if (!expression) {
            throw new BsRException(message);
        }
    }

    /**
     * 非true
     *
     * @param expression
     * @param reg String.format(reg, param)
     * @param param
     */
    public static void isTrue(@Nullable Boolean expression, String reg, String... param) {
        isTrue(expression, String.format(reg, param));
    }


    /**
     * 为true
     *
     * @param expression
     * @param message
     */
    public static void isFalse(@Nullable Boolean expression, String message) {
        if (expression) {
            throw new BsRException(message);
        }
    }


    /**
     * 为true
     *
     * @param expression
     * @param reg String.format(reg, param)
     * @param param
     */
    public static void isFalse(@Nullable Boolean expression, String reg, String... param) {
        isFalse(expression, String.format(reg, param));
    }

    /**
     * 非null
     *
     * @param object
     * @param message
     */
    public static void isNull(@Nullable Object object, String message) {
        if (object != null) {
            throw new BsRException(message);
        }
    }

    /**
     * 非null
     *
     * @param object
     * @param reg String.format(reg, param)
     * @param param
     */
    public static void isNull(@Nullable Object object, String reg, String... param) {
        isNull(object, String.format(reg, param));
    }

    /**
     * 为null
     *
     * @param object
     * @param message
     */
    public static void notNull(@Nullable Object object, String message) {
        if (object == null) {
            throw new BsRException(message);
        }
    }

    /**
     * 为null
     *
     * @param object
     * @param reg String.format(reg, param)
     * @param param
     */
    public static void notNull(@Nullable Object object, String reg, String... param) {
        notNull(object, String.format(reg, param));
    }

    /**
     * 为null
     *
     * @param str
     * @param message
     */
    public static void notBlank(@Nullable String str, String message) {
        if (StringUtil.isBlank(str)) {
            throw new BsRException(message);
        }
    }

    /**
     * 为null
     *
     * @param str
     * @param reg String.format(reg, param)
     * @param param
     */
    public static void notBlank(@Nullable String str, String reg, String... param) {
        notBlank(str, String.format(reg, param));
    }

    /**
     * 为null
     *
     * @param collection
     * @param message
     */
    public static void notEmpty(@Nullable Collection collection, String message) {
        if (collection == null || collection.size() == 0) {
            throw new BsRException(message);
        }
    }


    /**
     * 为null
     *
     * @param collection
     * @param reg String.format(reg, param)
     * @param param
     */
    public static void notEmpty(@Nullable Collection collection, String reg, String... param) {
        notEmpty(collection, String.format(reg, param));
    }

    /**
     * 非null
     *
     * @param collection
     * @param message
     */
    public static void isEmpty(@Nullable Collection collection, String message) {
        if (collection != null && collection.size() > 0) {
            throw new BsRException(message);
        }
    }

    /**
     * 非null
     *
     * @param collection
     * @param reg String.format(reg, param)
     * @param param
     */
    public static void isEmpty(@Nullable Collection collection, String reg, String... param) {
        isEmpty(collection, String.format(reg, param));
    }
}
