package com.rapids.web.controller;

import com.rapids.core.domain.StuKnowledgeRela;
import com.rapids.core.domain.Student;
import com.rapids.core.repo.StuKnowledgeRelaRepo;
import com.rapids.core.repo.StudentRepo;
import com.rapids.core.service.StudyService;
import com.rapids.web.Constants;
import lombok.Data;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import javax.servlet.http.HttpSession;


/**
 * @author David on 17/3/8.
 */
@SuppressWarnings({"WeakerAccess", "unused"})
@ConfigurationProperties("study")
@RestController
@RequestMapping("/")
public class StudentController {

    private static Logger LOGGER = LoggerFactory.getLogger(StudentController.class);

    private @Setter Integer enableCount;
    private @Autowired StudentRepo studentRepo;
    private @Autowired StudyService studyService;
    private @Autowired StuKnowledgeRelaRepo stuKnowledgeRelaRepo;

    @PostMapping("login")
    @ResponseStatus(HttpStatus.OK)
    public void login(@RequestBody LoginReq loginReq, HttpSession session) {
        LOGGER.info("login request: {}", loginReq);
        Student student = studentRepo.findByNameAndPassword(loginReq.getUserName(), loginReq.getPassword());
        if(null == student) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
        }
        session.setAttribute(Constants.STUDENT_SESSION_KEY, student);
        //每次登陆检查是否需要激活知识点
        studyService.activity(student.getId(), enableCount);
    }

    @GetMapping("checkSession")
    @ResponseStatus(HttpStatus.OK)
    public void checkSession(HttpSession session) {
        Student student = (Student)session.getAttribute(Constants.STUDENT_SESSION_KEY);
        if(null == student) {
            throw new HttpClientErrorException(HttpStatus.FORBIDDEN);
        }
    }

    @Data
    private static class LoginReq {
        private String userName;
        private String password;
    }

}
