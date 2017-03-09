package com.rapids.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author David on 17/3/3.
 */
@SpringBootApplication
@ComponentScan("com.rapids.web.controller")
public class WebBoot {
    public static void main(String[] args) {
        SpringApplication.run(WebBoot.class, args);
    }
}
