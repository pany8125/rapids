package com.rapids.web.controller;

import com.rapids.core.domain.Student;
import com.rapids.core.repo.StudentRepo;
import com.rapids.web.Constants;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import javax.servlet.http.HttpSession;


/**
 * @author David on 17/3/8.
 */
@RestController
@RequestMapping("/")
public class StudentController {

    private static Logger LOGGER = LoggerFactory.getLogger(StudentController.class);

    private @Autowired StudentRepo studentRepo;

    @PostMapping("login")
    @ResponseStatus(HttpStatus.OK)
    public void login(@RequestBody LoginReq loginReq, HttpSession session) {
        LOGGER.info("login request: {}", loginReq);
        Student student = studentRepo.findByNameAndPassword(loginReq.getUserName(), loginReq.getPassword());
        if(null == student) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
        }
        session.setAttribute(Constants.STUDENT_SESSION_KEY, student);
    }


    @Data
    private static class LoginReq {
        private String userName;
        private String password;
    }
}
