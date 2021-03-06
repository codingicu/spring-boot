package com.demo.sso;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

/**
 * 项目: spring-boot
 * 时间: 2021/11/22 11:38
 *
 * @author HuangYu
 * @version 1.0.0
 * @since JDK1.8
 */
@SpringBootApplication
@ConfigurationPropertiesScan
public class SsoClientApplication {
    public static void main(String[] args) {
        SpringApplication.run(SsoClientApplication.class, args);
    }
}
