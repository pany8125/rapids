package com.rapids.core.test;

import com.rapids.core.CoreConfig;
import com.rapids.core.domain.*;
import com.rapids.core.repo.*;
import com.rapids.core.service.StudyService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.DigestUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author David on 17/2/23.
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringBootTest(classes = JavaConfigTest.class)
//@EnableAutoConfiguration
//@Import(CoreConfig.class)
public class JavaConfigTest {

    private static Logger LOGGER = LoggerFactory.getLogger(JavaConfigTest.class);
    public static void main(String[] args) {
        SpringApplication.run(JavaConfigTest.class, args);
    }
//    @Test
    public void bootstrapAppFromJavaConfig() {

        ApplicationContext context = new AnnotationConfigApplicationContext(CoreConfig.class);
        PackRepo repo = context.getBean(PackRepo.class);
        Pack pack = new Pack();
        pack.setName("aaa");
        repo.save(pack);
        LOGGER.info("know list : {}", repo.findAll());
    }

    private @Autowired PackRepo packRepo;
    private @Autowired StudentRepo studentRepo;
    private @Autowired StuKnowledgeRelaRepo stuKnowledgeRelaRepo;
    private @Autowired StuPackRelaRepo stuPackRelaRepo;
    private @Autowired KnowledgeRepo knowledgeRepo;
    private @Autowired StudyService studyService;

//    @Test
    public void initTestData() {
        Student student = createStu();
        Pack pack =  createPack();
        List<Knowledge> knows = createKnows(pack);
        relaKnowsAndStudent(student, knows);
        relaStudentAndPack(student, pack, knows);

    }

//    @Test
    public void testQuery() {
        studyService.nextKnowledge(1);
    }

    private void relaStudentAndPack(Student student, Pack pack, List<Knowledge> knowledges) {
        StuPackRela stuPackRela = new StuPackRela();
        stuPackRela.setStudentId(student.getId());
        stuPackRela.setPackId(pack.getId());
        stuPackRela.setStatus(StuPackRela.Status.LEARNING);
        stuPackRela.setKnowledgeNum(knowledges.size());
        stuPackRela.setLearnedNum(0);
        stuPackRela.setCreateTime(new Date());
        stuPackRela.setStatus(StuPackRela.Status.WAIT);
        stuPackRelaRepo.save(stuPackRela);
        LOGGER.debug("pack student rela created! {}", stuPackRela);
    }

    private void relaKnowsAndStudent(Student student, List<Knowledge> knows) {
        knows.forEach(knowledge -> {
            StuKnowledgeRela stuKnowledgeRela = new StuKnowledgeRela();
            stuKnowledgeRela.setId(DigestUtils.md5DigestAsHex(UUID.randomUUID().toString().getBytes()));
            stuKnowledgeRela.setKnowledgeId(knowledge.getId());
            stuKnowledgeRela.setStudentId(student.getId());
//            stuKnowledgeRela.setStatus(StuKnowledgeRela.Status.CREATED);
            stuKnowledgeRelaRepo.save(stuKnowledgeRela);
            LOGGER.debug("knows student rela created! {}", stuKnowledgeRela);
        });


    }

    private List<Knowledge> createKnows(Pack pack) {
        List<Knowledge> list = new ArrayList<>();
        for(int i = 0 ; i < 10 ; i ++) {
            Knowledge knowledge = new Knowledge();
            knowledge.setName("AAAA" + i);
            knowledge.setTitle("aaaa Title + " + i);
            knowledge.setPackId(pack.getId());
            list.add(knowledge);

        }
        knowledgeRepo.save(list);
        LOGGER.debug("knows created! size : {}", list.size());
        return list;
    }

    private Pack createPack() {
        Pack pack = new Pack();
        pack.setName("一年级英语");
        pack.setType(Pack.Type.ENGLISH);
        packRepo.save(pack);
        LOGGER.debug("pack created! {}", pack);
        return pack;
    }



    private Student createStu() {
        Student student = new Student();
        student.setName("David");
        student.setCreateTime(new Date());
        student.setLastUpdateTime(new Date());
        student.setEditor("TESTER");
        student.setPassword("aaa");
        studentRepo.save(student);
        LOGGER.debug("student created! {}", student);
        return student;
    }
}
