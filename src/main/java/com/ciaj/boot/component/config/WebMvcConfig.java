package com.ciaj.boot.component.config;

import com.ciaj.boot.component.filter.ResubmitFilter;
import com.ciaj.boot.component.interceptor.PageInterceptor;
import com.ciaj.boot.component.interceptor.ResubmitInterceptor;
import com.ciaj.boot.component.serializer.DT2Serializer;
import com.ciaj.boot.component.serializer.Date2Deserializer;
import com.ciaj.boot.component.serializer.Date2Serializer;
import com.ciaj.boot.component.serializer.Decimal2Serializer;
import com.ciaj.comm.utils.CalendarUtils;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author: Ciaj.
 * @Date: 2018/6/19 19:40
 * @Description:
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/statics/**","/public/**").addResourceLocations("classpath:/statics/","classpath:/public/");
    }

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        MappingJackson2HttpMessageConverter jackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        ObjectMapper objectMapper = jackson2HttpMessageConverter.getObjectMapper();
        objectMapper.setDateFormat(new DateFormatExtend());
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        //
//        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializerExtend(DateTimeFormatter.ofPattern(CalendarUtils.DATE_TIME_PATTERN)));
//        javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializerExtend(DateTimeFormatter.ofPattern(CalendarUtils.DATE_PATTERN)));
//        javaTimeModule.addDeserializer(LocalTime.class, new LocalTimeDeserializer(DateTimeFormatter.ofPattern(CalendarUtils.TIME_SHORT)));
        //
        javaTimeModule.addDeserializer(Date.class, new Date2Deserializer());
        //
        javaTimeModule.addSerializer(LocalDateTime.class, new DT2Serializer());
        javaTimeModule.addSerializer(LocalDate.class, new DT2Serializer());
        javaTimeModule.addSerializer(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ofPattern(CalendarUtils.TIME_SHORT)));
        //日期序列化处理
        javaTimeModule.addSerializer(Date.class, new Date2Serializer());
        // 浮点型使用字符串
        javaTimeModule.addSerializer(BigDecimal.class, new Decimal2Serializer());

        // 注册新的模块到objectMapper
        objectMapper.registerModule(javaTimeModule);

        jackson2HttpMessageConverter.setObjectMapper(objectMapper);
        List<MediaType> list = new ArrayList<>();
        list.add(MediaType.APPLICATION_JSON_UTF8);
        jackson2HttpMessageConverter.setSupportedMediaTypes(list);

        converters.add(0, jackson2HttpMessageConverter);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new PageInterceptor()).addPathPatterns("/**");
        registry.addInterceptor(new ResubmitInterceptor()).addPathPatterns("/**");
    }

    @Bean
    public FilterRegistrationBean repeatedlyReadFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        ResubmitFilter resubmitFilter = new ResubmitFilter();
        registration.setFilter(resubmitFilter);
        registration.addUrlPatterns("/*");
        return registration;
    }
}
