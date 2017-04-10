package com.rapids.web.controller;

import com.rapids.core.domain.*;
import com.rapids.core.repo.KnowledgeRepo;
import com.rapids.core.repo.StuKnowledgeRelaRepo;
import com.rapids.core.repo.StuPackRelaRepo;
import com.rapids.core.service.StudyService;
import lombok.Data;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;


/**
 * @author David on 17/3/8.
 */
@RestController
@RequestMapping("/study")
@ConfigurationProperties("rapids.study")
@SuppressWarnings({"unused", "WeakerAccess"})
public class StudyController extends LoginedController{

    private Logger LOGGER = LoggerFactory.getLogger(StudyController.class);

    private @Autowired StudyService studyService;
    private @Autowired StuKnowledgeRelaRepo stuKnowledgeRelaRepo;
    private @Autowired KnowledgeRepo knowledgeRepo;
    private @Autowired StuPackRelaRepo stuPackRelaRepo;
    private @Setter Integer intervalLimit;

    @GetMapping("checkStudyStatus")
    public ResponseEntity checkStudyStatus() {
        Student student = currentStudent();
        StuKnowledgeRela stuKnowledgeRela = stuKnowledgeRelaRepo.hasDeleteStuKnowledgeRela(student.getId());
        if(null == stuKnowledgeRela) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }else {
            return new ResponseEntity(HttpStatus.FOUND);
        }
    }

    @GetMapping("/packages")
    @ResponseStatus(HttpStatus.OK)
    public List<StuPackRela> packages() {
        return stuPackRelaRepo.findByStudentId(currentStudent().getId());
    }

    @SuppressWarnings("WeakerAccess")
    @GetMapping("/next")
    @ResponseStatus(HttpStatus.OK)
    public Knowledge next() {
        Student student = currentStudent();
        StuKnowledgeRela stuKnowledgeRela = stuKnowledgeRelaRepo.findNextByTime(student.getId(), System.currentTimeMillis());
        if(null == stuKnowledgeRela) {
            stuKnowledgeRela = stuKnowledgeRelaRepo.findNext(student.getId());
        }
        if(null == stuKnowledgeRela) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
        }
        Knowledge knowledge = knowledgeRepo.findOne(stuKnowledgeRela.getKnowledgeId());
        if(null == knowledge) {
            throw new HttpClientErrorException(HttpStatus.NO_CONTENT);
        }
        return knowledge;
    }

    @PostMapping("/reviewAndNext")
    @ResponseStatus(HttpStatus.OK)
    public Knowledge reviewAndNext(@RequestBody ReviewRequest reviewRequest) {
        LOGGER.info("reviewAndNext req : {}", reviewRequest);
        Knowledge knowledge = knowledgeRepo.findOne(reviewRequest.getKnowledgeId());
        if(null == knowledge) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
        }
        StuKnowledgeRela stuKnowledgeRela = stuKnowledgeRelaRepo.findByStudentIdAndKnowledgeId(currentStudent().getId(),
                knowledge.getId());
        if(null == stuKnowledgeRela) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
        }
        stuKnowledgeRela.setViewCount(stuKnowledgeRela.getViewCount() + reviewRequest.getViewCount());
        goStudyWorkFlow(knowledge, stuKnowledgeRela, reviewRequest);
        return next();
    }

    private void goStudyWorkFlow(Knowledge knowledge, StuKnowledgeRela stuKnowledgeRela,
                                 ReviewRequest reviewRequest) {
        LOGGER.info("start to complicate logic... , know id : {}, stuRela: {}, request: {}",
                knowledge.getId(), stuKnowledgeRela.getId(), reviewRequest);
        switch (reviewRequest.firstTime) {
            case REMEMBER: {
                switch (reviewRequest.secondTime) {
                    case REMEMBER: {
                        LOGGER.debug("两次点击REMEMBER的次数间隔{}秒", reviewRequest.getInterval());
                        if (reviewRequest.getInterval() < intervalLimit) {
                            LOGGER.debug("知识点在知识包中第{}次被复习到", stuKnowledgeRela.getReviewCount() + 1);
                            if (stuKnowledgeRela.getReviewCount() == 0) {
                                studyService.finishKnowledgeRela(stuKnowledgeRela);
                            } else {
                                LOGGER.debug("知识点在第{}次复习次数中第{}被显示", stuKnowledgeRela.getReviewCount() + 1,
                                        stuKnowledgeRela.getViewCount());
                                if(stuKnowledgeRela.getViewCount() == 1) {
                                    LOGGER.debug("知识点已熟记标识为:{}", stuKnowledgeRela.isMemorized());
                                    if(stuKnowledgeRela.isMemorized()) {
                                        studyService.finishKnowledgeRela(stuKnowledgeRela);
                                    }else {
                                        LOGGER.debug("当前复习次数为:{} ,复习次数跳过1次, 复习次数被保存为:{}, 并标记为已熟记",
                                                stuKnowledgeRela.getReviewCount(), stuKnowledgeRela.getReviewCount() + 2);
                                        stuKnowledgeRela.setMemorized(true);
                                        studyService.reviewKnowledge(stuKnowledgeRela, 1);
                                    }
                                }else {
                                    studyService.reviewKnowledge(stuKnowledgeRela);
                                }
                            }
                        }else {
                            studyService.reviewKnowledge(stuKnowledgeRela);
                        }
                        break;
                    }
                    case HESITATE: {
                        studyService.resetSeq(stuKnowledgeRela);
                        break;
                    }
                    case FORGET: {
                        // reshow title at page
                        break;
                    }
                }
                break;
            }
            case HESITATE: {
                switch (reviewRequest.secondTime) {
                    case REMEMBER: {
                        studyService.resetSeq(stuKnowledgeRela);
                        break;
                    }
                    case HESITATE: {
                        // reshow title at page
                        break;
                    }
                    case FORGET: {
                        // reshow title at page
                        break;
                    }
                }
                break;
            }
            case FORGET: {
                // reshow title at page
                break;
            }
        }

    }

    @Data
    public static class ReviewRequest {
        private long knowledgeId;
        private Knowledge.Impress firstTime;
        private Knowledge.Impress secondTime;
        private long interval;
        private int viewCount;
    }
}
