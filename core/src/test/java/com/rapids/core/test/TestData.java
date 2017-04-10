package com.rapids.core.test;

import com.rapids.core.CoreConfig;
import com.rapids.core.domain.*;
import com.rapids.core.repo.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
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
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TestData.class)
@EnableAutoConfiguration
@Import(CoreConfig.class)
public class TestData {

    private static Logger LOGGER = LoggerFactory.getLogger(TestData.class);

    private @Autowired PackRepo packRepo;
    private @Autowired StudentRepo studentRepo;
    private @Autowired StuKnowledgeRelaRepo stuKnowledgeRelaRepo;
    private @Autowired StuPackRelaRepo stuPackRelaRepo;
    private @Autowired KnowledgeRepo knowledgeRepo;

    @Test
    public void restoreDate() {
        clearTestData();
        initTestData();
    }

    @Test
    public void clearTestData() {
        packRepo.deleteAll();
        stuKnowledgeRelaRepo.deleteAll();
        stuPackRelaRepo.deleteAll();
        knowledgeRepo.deleteAll();
    }

    @Test
    public void initTestData() {
        Student student = createStu();
        Pack pack =  createPack();
        List<Knowledge> knows = createKnows(pack);
        relaKnowsAndStudent(student, pack, knows);
        relaStudentAndPack(student, pack, knows);

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
        stuPackRela.setPackName(pack.getName());
        stuPackRelaRepo.save(stuPackRela);
        LOGGER.debug("pack student rela created! {}", stuPackRela);
    }

    private void relaKnowsAndStudent(Student student, Pack pack, List<Knowledge> knows) {
        knows.forEach(knowledge -> {
            StuKnowledgeRela stuKnowledgeRela = new StuKnowledgeRela();
            stuKnowledgeRela.setId(DigestUtils.md5DigestAsHex(UUID.randomUUID().toString().getBytes()));
            stuKnowledgeRela.setKnowledgeId(knowledge.getId());
            stuKnowledgeRela.setStudentId(student.getId());
            stuKnowledgeRela.setCreateTime(new Date());
            stuKnowledgeRela.setPackId(pack.getId());
            stuKnowledgeRela.setEnabled(true);
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
            knowledge.setDescription("desc" + i);
            knowledge.setDescPic("img/culinary/people/1.jpg");
            knowledge.setMemo("没什么妙记，记住就好，死记硬背，我也没有什么好说得了");
            knowledge.setMemoPic("img/culinary/people/1.jpg");
            knowledge.setCreateTime(new Date());
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
        Student student = studentRepo.findOne(1L);
        if(null == student) {
            student = new Student();
            student.setName("David");
            student.setCreateTime(new Date());
            student.setLastUpdateTime(new Date());
            student.setEditor("TESTER");
            student.setPassword("aaa");
            studentRepo.save(student);
            LOGGER.debug("student created! {}", student);
        }
        return student;
    }
}
