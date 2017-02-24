package com.rapids.manage;

import com.rapids.core.CoreConfig;
import com.rapids.core.repo.KnowledgePackRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author David on 17/2/23.
 */
@EnableAutoConfiguration
@Import(CoreConfig.class)
public class ManageBoot {
    private static Logger log = LoggerFactory.getLogger(ManageBoot.class);
    private @Autowired KnowledgePackRepo knowledgePackRepo;
    public static void main(String[] args) {
        SpringApplication.run(ManageBoot.class, args);
    }

    @Bean
    public CommandLineRunner demo() {
        return (args) -> log.info("know list : {}", knowledgePackRepo.findAll());
    }
}
