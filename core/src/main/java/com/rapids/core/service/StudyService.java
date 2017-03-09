package com.rapids.core.service;

import com.rapids.core.domain.Knowledge;
import com.rapids.core.domain.StuKnowledgeRela;
import com.rapids.core.domain.StuPackRela;
import com.rapids.core.domain.StudyLog;
import com.rapids.core.repo.KnowledgeRepo;
import com.rapids.core.repo.StuKnowledgeRelaRepo;
import com.rapids.core.repo.StuPackRelaRepo;
import com.rapids.core.repo.StudyLogRepo;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author David on 17/2/28.
 */
@Service
@ConfigurationProperties("rapids.core")
public class StudyService {

    private @Autowired StuPackRelaRepo stuPackRelaRepo;
    private @Autowired StuKnowledgeRelaRepo stuKnowledgeRelaRepo;
    private @Autowired KnowledgeRepo knowledgeRepo;
    private @Autowired StudyLogRepo studyLogRepo;
    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    private @Setter List<Integer> studyTimesRule;
    private static final Logger LOGGER = LoggerFactory.getLogger(StudyService.class);

    public boolean checkStudyStatus(long studentId) {
        StuPackRela stuPackRela = stuPackRelaRepo.findLastStudyPack(studentId);
        return null != stuPackRela;
    }

    @Transactional
    public Knowledge nextKnowledge(long studentId) {
        StuKnowledgeRela stuKnowledgeRela = stuKnowledgeRelaRepo.findRequireByTime(studentId, System.currentTimeMillis());
        if(null == stuKnowledgeRela) {
            stuKnowledgeRela = stuKnowledgeRelaRepo.findRequire(studentId);
        }
        return null == stuKnowledgeRela ? null :
                knowledgeRepo.findOne(stuKnowledgeRela.getKnowledgeId());
    }

    /**
     * 将知识点记录到改学习包的末尾
     */
    @Transactional
    public void resetSeq(StuKnowledgeRela stuKnowledgeRela) {
        stuKnowledgeRelaRepo.delete(stuKnowledgeRela.getId());
        stuKnowledgeRela.setCreateTime(new Date());
        stuKnowledgeRelaRepo.save(stuKnowledgeRela);
    }

    /**
     *  将知识点记录成已复习
     */
    @Transactional
    public void reviewKnowledge(StuKnowledgeRela knowledgeRela, int skipTime) {
        Date date = new Date();
        knowledgeRela.setViewCount(0);
        knowledgeRela.setReviewed(true);
        knowledgeRela.setReviewCount(knowledgeRela.getReviewCount() + 1 + skipTime);
        knowledgeRela.setReviewTime(date);
        knowledgeRela.setLeanSeq(getDelayMSFromRule(knowledgeRela.getReviewCount() + 1));
        if(-1 == knowledgeRela.getReviewCount()) {
            deleteKnowledgeRela(knowledgeRela);
        }else {
            stuKnowledgeRelaRepo.save(knowledgeRela);
        }
        createLog(knowledgeRela);
    }

    @Transactional
    private void createLog(StuKnowledgeRela knowledgeRela) {
        StudyLog studyLog = new StudyLog();
        studyLog.setId(DigestUtils.md5DigestAsHex(UUID.randomUUID().toString().getBytes()));
        studyLog.setKnowledgeId(knowledgeRela.getKnowledgeId());
        studyLog.setPackId(knowledgeRela.getPackId());
        studyLog.setStudentId(knowledgeRela.getStudentId());
        studyLog.setLogTime(new Date());
        studyLog.setLearnTimes(knowledgeRela.getReviewCount());
        studyLogRepo.save(studyLog);
    }

    private long getDelayMSFromRule(int index) {
        long ms = -1;
        try {
            ms = studyTimesRule.get(index);
        }catch (IndexOutOfBoundsException e) {
            LOGGER.info("index overflow, {}", index);
        }
        return ms;
    }

    /**
     *  将知识点记录成已复习
     */
    public void reviewKnowledge(StuKnowledgeRela knowledgeRela) {
        reviewKnowledge(knowledgeRela, 0);
    }

    /**
     *  删除知识点
     */
    @Transactional
    public void deleteKnowledgeRela(StuKnowledgeRela knowledgeRela) {
        knowledgeRela.setDeleted(true);
        knowledgeRela.setViewCount(0);
        stuKnowledgeRelaRepo.save(knowledgeRela);
        StuPackRela stuPackRela = stuPackRelaRepo.findByStudentIdAndPackId(knowledgeRela.getStudentId(), knowledgeRela.getPackId());
        stuPackRela.setLearnedNum(stuPackRela.getLearnedNum() + 1);
        stuPackRela.setLastLearnTime(new Date());
        stuPackRelaRepo.save(stuPackRela);
    }
}
