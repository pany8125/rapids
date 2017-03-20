package com.rapids.web.controller;

import com.rapids.core.domain.Knowledge;
import com.rapids.core.domain.StuKnowledgeRela;
import com.rapids.core.domain.StuPackRela;
import com.rapids.core.domain.Student;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * @author David on 17/3/8.
 */
@RestController
@RequestMapping("/study")
@ConfigurationProperties("study")
@SuppressWarnings({"unused", "WeakerAccess"})
public class StudyController extends LoginedController{

    private Logger LOGGER = LoggerFactory.getLogger(StudyController.class);

    private @Autowired StudyService studyService;
    private @Autowired StuKnowledgeRelaRepo stuKnowledgeRelaRepo;
    private @Autowired KnowledgeRepo knowledgeRepo;
    private @Autowired StuPackRelaRepo stuPackRelaRepo;
    private @Setter Integer seqCount;
    private @Setter Integer intervalLimit;

    @GetMapping("/packages")
    @ResponseStatus(HttpStatus.OK)
    public List<StuPackRela> packages() {
        return stuPackRelaRepo.findByStudentId(currentStudent().getId());
    }

    @GetMapping("/activity")
    @ResponseStatus(HttpStatus.CONTINUE)
    public void activity() {
        Student student = currentStudent();
        if(studyService.checkStudyStatus(student.getId())) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Calendar calendar = Calendar.getInstance();
            String startDate = simpleDateFormat.format(calendar.getTime());
            calendar.add(Calendar.DATE, 1);
            String endDate = simpleDateFormat.format(calendar.getTime());
            StuKnowledgeRela stuKnowledgeRela = stuKnowledgeRelaRepo.findEnableByDay(startDate, endDate);
            if(null == stuKnowledgeRela) {
                stuKnowledgeRelaRepo.enableKnowledgeByStudentId(new Date(), student.getId(), seqCount);
            }
        }
    }

    @SuppressWarnings("WeakerAccess")
    @GetMapping("/next")
    @ResponseStatus(HttpStatus.OK)
    public Knowledge next() {
        Student student = currentStudent();
        Knowledge knowledge = studyService.nextKnowledge(student.getId());
        if(null == knowledge) {
            throw new HttpClientErrorException(HttpStatus.NO_CONTENT);
        }
        StuKnowledgeRela stuKnowledgeRela =
                stuKnowledgeRelaRepo.findByStudentIdAndKnowledgeId(student.getId(), knowledge.getId());
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
        StuKnowledgeRela stuKnowledgeRela = stuKnowledgeRelaRepo.findByStudentIdAndKnowledgeId(currentStudent().getId(), knowledge.getId());
        if(null == stuKnowledgeRela) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
        }
        stuKnowledgeRela.setViewCount(stuKnowledgeRela.getViewCount() + reviewRequest.getViewCount());
        goStudyWorkFlow(knowledge, stuKnowledgeRela, reviewRequest);
        return next();
    }

    private void goStudyWorkFlow(Knowledge knowledge, StuKnowledgeRela stuKnowledgeRela,
                                 ReviewRequest reviewRequest) {
        switch (reviewRequest.firstTime) {
            case REMEMBER: {
                switch (reviewRequest.secondTime) {
                    case REMEMBER: {
                        if (reviewRequest.getInterval() < intervalLimit) {
                            if (stuKnowledgeRela.getReviewCount() == 0) {
                                studyService.deleteKnowledgeRela(stuKnowledgeRela);
                            } else {
                                if(stuKnowledgeRela.getViewCount() == 1) {
                                    if(stuKnowledgeRela.isMemorized()) {
                                        studyService.deleteKnowledgeRela(stuKnowledgeRela);
                                    }else {
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
    private static class ReviewRequest {
        private long knowledgeId;
        private Knowledge.Impress firstTime;
        private Knowledge.Impress secondTime;
        private long interval;
        private int viewCount;
    }
}
