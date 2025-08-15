package com.ciaj.boot.component.serializer;


import com.ciaj.comm.utils.CalendarUtils;
import com.ciaj.comm.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;

import java.text.*;
import java.util.Date;

/**
 * @Description 日期格式处理
 * @Author Ciaj.
 * @Date 2019/5/8 16:42
 * @Version 1.0
 */
@Slf4j
public class DateFormatExtend extends DateFormat {
    private DateFormat dateFormat;

    public DateFormatExtend() {
        this.dateFormat = new SimpleDateFormat(CalendarUtils.DATE_TIME_PATTERN);
    }


    public DateFormatExtend(DateFormat dateFormat) {
        this.dateFormat = dateFormat;
    }

    /**
     * 序列化时会执行这个方法
     *
     * @param date
     * @param toAppendTo
     * @param fieldPosition
     * @return
     */
    @Override
    public StringBuffer format(Date date, StringBuffer toAppendTo, FieldPosition fieldPosition) {
        return dateFormat.format(date, toAppendTo, fieldPosition);
    }

    /**
     * 反序列化时执行此方法，我们先让他执行我们自己的format，如果异常则执执行他的
     *
     * @param source
     * @param pos
     * @return
     */
    @Override
    public Date parse(String source, ParsePosition pos) {
        return getParseDate(source);
    }

    /**
     * 反序列化时执行此方法，我们先让他执行我们自己的format，如果异常则执执行他的
     *
     * @param source
     * @return
     */
    @Override
    public Date parse(String source) {
        return getParseDate(source);
    }

    private Date getParseDate(String source) {
        if (StringUtil.isBlank(source)){
            return null;
        }
        try {
            return CalendarUtils.parseDate(source, CalendarUtils.FORMATS.toArray(new String[0]));
        } catch (ParseException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 此方法在objectmapper 默认的dateformat里边用到，这里也要重写
     *
     * @return
     */
    @Override
    public Object clone() {
        return new DateFormatExtend();
    }
}
