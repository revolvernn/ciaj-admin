package com.ciaj.boot.component.config;

import com.ciaj.boot.component.filter.ResubmitFilter;
import com.ciaj.boot.component.interceptor.LoginInterceptor;
import com.ciaj.boot.component.interceptor.PageInterceptor;
import com.ciaj.boot.component.interceptor.ResubmitInterceptor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.bind.support.ConfigurableWebBindingInitializer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
        registry.addResourceHandler("/statics/**").addResourceLocations("classpath:/statics/");
    }

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        MappingJackson2HttpMessageConverter jackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        ObjectMapper objectMapper = jackson2HttpMessageConverter.getObjectMapper();
        objectMapper.setDateFormat(new DateFormatExtend());
        jackson2HttpMessageConverter.setObjectMapper(objectMapper);

        converters.add(0, jackson2HttpMessageConverter);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/sys/**");
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
