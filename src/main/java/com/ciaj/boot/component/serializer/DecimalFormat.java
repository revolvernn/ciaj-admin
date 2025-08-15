package com.ciaj.boot.component.serializer;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.lang.annotation.*;
import java.math.BigDecimal;

/**
 * @Description Decimal 格式化 使用在方法上
 * @Date 2020/10/23 17:27
 * @Created by ciaj.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.ANNOTATION_TYPE, ElementType.METHOD})
@JacksonAnnotationsInside
@JsonSerialize(using = Decimal2Serializer.class)
public @interface DecimalFormat {
    /**
     * 默认保留两位
     *
     * @return
     */
    int scale() default 2;

    /**
     * 默认不四舍五入
     *
     * @return
     */
    int roundingMode() default BigDecimal.ROUND_DOWN;
}
