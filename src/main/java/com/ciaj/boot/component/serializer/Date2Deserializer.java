package com.ciaj.boot.component.serializer;


import com.ciaj.comm.utils.CalendarUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * @Author: ciaj.
 * @Date: 2020/8/25 20:07
 * @Description: 日期反序列化 处理DATE 类型使用JsonFormat 不生效
 * @version: 1.0
 */
@Slf4j
public class Date2Deserializer extends JsonDeserializer<Date> implements ContextualDeserializer {

    public final DateFormat df;

    public final String pattern;

    public Date2Deserializer() {
        this.df = null;
        this.pattern = null;
    }

    public Date2Deserializer(DateFormat df) {
        this.df = df;
        this.pattern = CalendarUtils.DATE_TIME_PATTERN;
    }

    public Date2Deserializer(DateFormat df, String pattern) {
        this.df = df;
        this.pattern = pattern;
    }

    @Override
    public Date deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        try {
            String dateValue = p.getText();
            if (df == null || StringUtils.isEmpty(dateValue)) {
                return null;
            }
            Date date;
            if (StringUtils.isNumeric(dateValue)) {
                date = new Date(Long.valueOf(dateValue));
            } else {
                String[] formatArray = CalendarUtils.FORMATS.toArray(new String[0]);
                date = DateUtils.parseDate(p.getText(), formatArray);
            }
            return df.parse(df.format(date));
        } catch (ParseException | SecurityException e) {
            log.error("JSON反序列化，时间解析失败", e);
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public JsonDeserializer<?> createContextual(DeserializationContext ctxt, BeanProperty property) {
        if (property != null) {
            JsonFormat.Value format = ctxt.getAnnotationIntrospector().findFormat(property.getMember());

            if (format != null) {
                TimeZone tz = format.getTimeZone();
                // First: fully custom pattern?
                if (format.hasPattern() && !CalendarUtils.FORMATS.contains(format.getPattern())) {
                    CalendarUtils.FORMATS.add(format.getPattern());
                }
                if (format.hasPattern()) {
                    final String dateFormat = format.getPattern();
                    final Locale loc = format.hasLocale() ? format.getLocale() : ctxt.getLocale();
                    SimpleDateFormat df = new SimpleDateFormat(dateFormat, loc);
                    if (tz == null) {
                        tz = ctxt.getTimeZone();
                    }
                    df.setTimeZone(tz);
                    return new Date2Deserializer(df, dateFormat);
                }
                // But if not, can still override timezone
                if (tz != null) {
                    DateFormat df = ctxt.getConfig().getDateFormat();
                    // one shortcut: with our custom format, can simplify handling a bit
                    if (df.getClass() == StdDateFormat.class) {
                        final Locale loc = format.hasLocale() ? format.getLocale() : ctxt.getLocale();
                        StdDateFormat std = (StdDateFormat) df;
                        std = std.withTimeZone(tz);
                        std = std.withLocale(loc);
                        df = std;
                    } else {
                        // otherwise need to clone, re-set timezone:
                        df = (DateFormat) df.clone();
                        df.setTimeZone(tz);
                    }
                    return new Date2Deserializer(df);
                }
            }
        }
        return this;
    }
}