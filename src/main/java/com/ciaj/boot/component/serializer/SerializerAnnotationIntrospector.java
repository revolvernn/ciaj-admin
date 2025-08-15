package com.ciaj.boot.component.serializer;

import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description 自定义注解JSON 序列化拦截器
 * @Date 2020/10/23 18:10
 * @Created by ciaj.
 */
@Slf4j
public class SerializerAnnotationIntrospector extends JacksonAnnotationIntrospector {
    @Override
    public Object findSerializer(Annotated annotated) {
        //只对get方法有用
        if (annotated instanceof AnnotatedMethod) {
            DecimalFormat decimalFormat = annotated.getAnnotated().getAnnotation(DecimalFormat.class);
            if (decimalFormat != null) {
                log.debug("==>findSerializer-DecimalFormat scale: {} roundingMode: {}", decimalFormat.scale(), decimalFormat.roundingMode());
                return new Decimal2Serializer(decimalFormat.scale(), decimalFormat.roundingMode());
            }
            DateTimeFormat dateTimeFormat = annotated.getAnnotated().getAnnotation(DateTimeFormat.class);
            if (dateTimeFormat != null) {
                log.debug("==>findSerializer-DateTimeFormat-pattern: {}",dateTimeFormat.pattern());
                return new DT2Serializer(dateTimeFormat.pattern());
            }
        }
        return super.findSerializer(annotated);
    }
}
