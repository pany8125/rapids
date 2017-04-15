package com.rapids.manage;

import com.rapids.core.CoreConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

/**
 * @author David on 17/2/23.
 */
@EnableAutoConfiguration
@Import(CoreConfig.class)
@ComponentScan({"com.rapids.manage.controller", "com.rapids.manage.service"})
public class ManageBoot {
    public static void main(String[] args) {
        SpringApplication.run(ManageBoot.class, args);
    }

}
