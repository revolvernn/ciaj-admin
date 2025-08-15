package com.ciaj.boot.component.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.math.BigDecimal;

/**
 * @Author: ciaj.
 * @Date: 2020/8/21 15:40
 * @Description:
 * @version: 1.0
 */
public class Decimal2Serializer extends JsonSerializer<Object> {

    private final int scale;
    private final int roundingMode;

    /**
     * 默认保留两位小数 默认不四舍五入
     */
    public Decimal2Serializer() {
        super();
        this.scale = 2;
        this.roundingMode = BigDecimal.ROUND_DOWN;
    }

    /**
     * 自定义
     * @param scale
     * @param roundingMode
     */
    public Decimal2Serializer(int scale, int roundingMode) {
        super();
        this.scale = scale;
        this.roundingMode = roundingMode;
    }

    /**
     * 将返回的BigDecimal保留两位小数，再返回给前端
     *
     * @param value
     * @param jsonGenerator
     * @param serializerProvider
     * @throws IOException
     * @throws JsonProcessingException
     */
    @Override
    public void serialize(Object value, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        if (value != null) {
            BigDecimal bigDecimal = new BigDecimal(value.toString()).setScale(scale, roundingMode);
            jsonGenerator.writeString(bigDecimal.toString());
        }
    }
}
