package com.rapids.web;

import com.rapids.core.CoreConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

/**
 * @author David on 17/3/3.
 */
@EnableAutoConfiguration
@Import(CoreConfig.class)
@ComponentScan("com.rapids.web.controller")
public class WebBoot {
    public static void main(String[] args) {
        SpringApplication.run(WebBoot.class, args);
    }
}
