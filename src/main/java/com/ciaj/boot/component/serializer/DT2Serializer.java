package com.ciaj.boot.component.serializer;

import com.ciaj.comm.utils.CalendarUtils;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @Author: ciaj.
 * @Date: 2020/8/21 15:40
 * @Description:
 * @version: 1.0
 */
public class DT2Serializer extends JsonSerializer<Object> {

    private final String pattern;

    /**
     * 默认保留两位小数 默认不四舍五入
     */
    public DT2Serializer() {
        super();
        this.pattern = CalendarUtils.DATE_TIME_PATTERN;
    }

    /**
     * 自定义
     *
     * @param pattern
     */
    public DT2Serializer(String pattern) {
        super();
        this.pattern = pattern == null ? CalendarUtils.DATE_TIME_PATTERN : pattern;
    }

    /**
     * 日期格式化
     *
     * @param value
     * @param jsonGenerator
     * @param serializerProvider
     * @throws IOException
     */
    @Override
    public void serialize(Object value, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (value != null) {
            if (value instanceof LocalDateTime) {
                LocalDateTime ldt = (LocalDateTime) value;
                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
                jsonGenerator.writeString(dateTimeFormatter.format(ldt));
            } else if (value instanceof LocalDate) {
                LocalDate ld = (LocalDate) value;
                LocalDateTime ldt = LocalDateTime.of(ld, LocalTime.of(0, 0, 0));
                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
                jsonGenerator.writeString(dateTimeFormatter.format(ldt));
            } else if (value instanceof Date) {
                jsonGenerator.writeString(new SimpleDateFormat(pattern).format((Date) value));
            }
        }
    }
}
