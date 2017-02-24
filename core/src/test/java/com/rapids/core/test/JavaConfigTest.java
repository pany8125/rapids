package com.rapids.core.test;

import com.rapids.core.CoreConfig;
import com.rapids.core.domain.KnowledgePack;
import com.rapids.core.repo.KnowledgePackRepo;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author David on 17/2/23.
 */
public class JavaConfigTest {

    private static Logger LOGGER = LoggerFactory.getLogger(JavaConfigTest.class);
    @Test
    public void bootstrapAppFromJavaConfig() {

        ApplicationContext context = new AnnotationConfigApplicationContext(CoreConfig.class);
        KnowledgePackRepo repo = context.getBean(KnowledgePackRepo.class);
        KnowledgePack pack = new KnowledgePack();
        pack.setName("aaa");
        repo.save(pack);
        LOGGER.info("know list : {}", repo.findAll());
    }
}
