package com.rapids.core.service;

import com.rapids.core.domain.StuKnowledgeRela;
import com.rapids.core.domain.StuPackRela;
import com.rapids.core.domain.StudyLog;
import com.rapids.core.repo.StuKnowledgeRelaRepo;
import com.rapids.core.repo.StuPackRelaRepo;
import com.rapids.core.repo.StudyLogRepo;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
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
    private @Autowired StudyLogRepo studyLogRepo;
    private @Setter List<Integer> studyTimesRule;
    private static final Logger LOGGER = LoggerFactory.getLogger(StudyService.class);

    public List<Integer> getStudyTimesRule() {
        return this.studyTimesRule;
    }

    /**
     * 将知识点记录到改学习包的末尾
     */
    @Transactional
    public void resetSeq(StuKnowledgeRela stuKnowledgeRela) {
        LOGGER.debug("讲知识点放到知识序列末尾");
        stuKnowledgeRela.setCreateTime(new Date());
        saveKnowledgeRela(stuKnowledgeRela);
    }

    /**
     *  将知识点记录成已复习
     */
    @Transactional
    public void reviewKnowledge(StuKnowledgeRela knowledgeRela, int skipTime) {
        SimpleDateFormat sft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        knowledgeRela.setViewCount(0);
        knowledgeRela.setReviewed(true);
        knowledgeRela.setReviewCount(knowledgeRela.getReviewCount() + 1 + skipTime);
        knowledgeRela.setReviewTime(date);
        long ruleDelay = getDelayMSFromRule(knowledgeRela.getReviewCount());
        knowledgeRela.setLeanSeq(knowledgeRela.getReviewTime().getTime() + ruleDelay);
        if(-1 == ruleDelay) {
            LOGGER.debug("已超过复习次数16次, 已完成记忆",
                    knowledgeRela.getReviewCount(), knowledgeRela.getReviewCount() + 1,
                    sft.format(knowledgeRela.getReviewTime()),
                    sft.format(new Date(knowledgeRela.getLeanSeq())));
            finishKnowledgeRela(knowledgeRela);
        }else {
            LOGGER.debug("知识点标记为已复习, 显示次数清0, 当前复习次数调整到第{}次, 复习时间为:{}, 下一次复习次数为第{}次, 下一次复习时间为:{}",
                    knowledgeRela.getReviewCount(),
                    sft.format(knowledgeRela.getReviewTime()),
                    knowledgeRela.getReviewCount() + 1,
                    sft.format(new Date(knowledgeRela.getLeanSeq())));
            saveKnowledgeRela(knowledgeRela);
        }
    }

    private long getDelayMSFromRule(int index) {
        if(index == studyTimesRule.size()) {
            return -1;
        }else {
            return studyTimesRule.get(index) * 1000;
        }
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
    public void finishKnowledgeRela(StuKnowledgeRela knowledgeRela) {
        LOGGER.debug("知识点在知识包中标记成[完成记忆],后续无需再次复习");
        knowledgeRela.setDeleted(true);
        knowledgeRela.setViewCount(0);
        knowledgeRela.setLeanSeq(-1L);
        saveKnowledgeRela(knowledgeRela);
    }

    @SuppressWarnings("WeakerAccess")
    @Transactional
    public void saveKnowledgeRela(StuKnowledgeRela stuKnowledgeRela) {
        stuKnowledgeRelaRepo.save(stuKnowledgeRela);
//      -1的packId为学生自己上传的知识点,不计入知识包统计
        if(-1 != stuKnowledgeRela.getPackId()) {
            StuPackRela stuPackRela = stuPackRelaRepo.findByStudentIdAndPackId(stuKnowledgeRela.getStudentId(),
                    stuKnowledgeRela.getPackId());
            stuPackRela.setLearnedNum(stuPackRela.getLearnedNum() + 1);
            stuPackRela.setLastLearnTime(new Date());
            stuPackRelaRepo.save(stuPackRela);
        }
        createLog(stuKnowledgeRela);
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

    @Transactional
    public void activity(long studentId, int enableCount) {
        List<StuPackRela> packages = stuPackRelaRepo.findByStudentId(studentId);
        Date enableDate = new Date();
        packages.forEach(stuPackRela -> {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Calendar calendar = Calendar.getInstance();
            String startDate = simpleDateFormat.format(calendar.getTime());
            calendar.add(Calendar.DATE, 1);
            String endDate = simpleDateFormat.format(calendar.getTime());
            StuKnowledgeRela stuKnowledgeRela = stuKnowledgeRelaRepo.findEnableByDay(startDate, endDate);
            if(null == stuKnowledgeRela) {
                int currentEnableCount = stuKnowledgeRelaRepo.enableKnowledgeByStudentId(enableDate, studentId, enableCount);
                LOGGER.info("student: {}, packId: {}, enableCount : {}, date: {}",
                        studentId, stuPackRela.getId(), currentEnableCount, enableDate);
            }
        });
    }
}
