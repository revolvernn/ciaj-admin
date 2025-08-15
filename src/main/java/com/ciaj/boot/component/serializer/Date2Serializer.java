package com.ciaj.boot.component.serializer;


import com.ciaj.comm.utils.CalendarUtils;
import com.ciaj.comm.utils.StringUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.fasterxml.jackson.databind.util.StdDateFormat;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * @Author: ciaj.
 * @Date: 2020/8/25 20:34
 * @Description: 日期序列化 处理DATE 类型使用JsonFormat 不生效
 * @version: 1.0
 */
public class Date2Serializer extends JsonSerializer<Date> implements ContextualSerializer {

    public final DateFormat df;

    public final String pattern;

    public Date2Serializer() {
        this.df = null;
        this.pattern = null;
    }

    public Date2Serializer(DateFormat df) {
        this.df = df;
        this.pattern = CalendarUtils.DATE_TIME_PATTERN;
    }

    public Date2Serializer(DateFormat df, String pattern) {
        this.df = df;
        this.pattern = pattern;
    }

    public void serialize(Date date, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (date != null) {
            SimpleDateFormat formatter = new SimpleDateFormat(StringUtil.isBlank(this.pattern) ? CalendarUtils.DATE_TIME_PATTERN : this.pattern);
            String formattedDate = formatter.format(date);
            jsonGenerator.writeString(formattedDate);
        }
    }


    @Override
    public JsonSerializer<?> createContextual(SerializerProvider prov, BeanProperty property) {
        if (property != null) {
            JsonFormat.Value format = prov.getAnnotationIntrospector().findFormat(property.getMember());

            if (format != null) {
                TimeZone tz = format.getTimeZone();
                // First: fully custom pattern?
                if (format.hasPattern() && !CalendarUtils.FORMATS.contains(format.getPattern())) {
                    CalendarUtils.FORMATS.add(format.getPattern());
                }
                if (format.hasPattern()) {
                    final String dateFormat = format.getPattern();
                    final Locale loc = format.hasLocale() ? format.getLocale() : prov.getLocale();
                    SimpleDateFormat df = new SimpleDateFormat(dateFormat, loc);
                    if (tz == null) {
                        tz = prov.getTimeZone();
                    }
                    df.setTimeZone(tz);
                    return new Date2Serializer(df, dateFormat);
                }
                // But if not, can still override timezone
                if (tz != null) {
                    DateFormat df = prov.getConfig().getDateFormat();
                    // one shortcut: with our custom format, can simplify handling a bit
                    if (df.getClass() == StdDateFormat.class) {
                        final Locale loc = format.hasLocale() ? format.getLocale() : prov.getLocale();
                        StdDateFormat std = (StdDateFormat) df;
                        std = std.withTimeZone(tz);
                        std = std.withLocale(loc);
                        df = std;
                    } else {
                        // otherwise need to clone, re-set timezone:
                        df = (DateFormat) df.clone();
                        df.setTimeZone(tz);
                    }
                    return new Date2Serializer(df);
                }
            }
        }
        return this;
    }
}
