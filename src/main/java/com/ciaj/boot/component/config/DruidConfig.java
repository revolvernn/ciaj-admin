package com.ciaj.boot.component.config;

import org.springframework.context.annotation.Configuration;

import javax.servlet.annotation.WebFilter;

/**
 * @Author: Ciaj.
 * @Date: 2018/6/19 09:42
 * @Description: 配置Druid的监控
 */
@Configuration
@WebFilter(value = {"exclusions", "*.js,*.css,/druid/*"})
public class DruidConfig {
}
