package com.ciaj.boot;

import lombok.extern.log4j.Log4j2;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @Author: Ciaj.
 * @Date: 2018/5/31 09:55
 * @Description:
 */
@Log4j2
@SpringBootApplication
//@EnableScheduling
@MapperScan(basePackages = {"com.ciaj.boot.modules.sys.mapper"})
public class AdminApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class, args);
        log.info("========================================================================");
        log.info("========================================================================");
        log.info("============================ 系统启动完成 ==============================");
        log.info("========================================================================");
        log.info("========================================================================");
    }
}
