package com.rapids.web.controller;

import com.rapids.core.domain.Student;
import com.rapids.core.repo.StudentRepo;
import com.rapids.web.Constants;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.client.HttpClientErrorException;
import javax.servlet.http.HttpSession;

/**
 * @author David on 16/11/9.
 */
@SuppressWarnings("WeakerAccess")
public abstract class LoginedController {

    private ThreadLocal<Student> currentStudent = new ThreadLocal<>();
    private @Value("${rapids.study.debug}") Boolean debug;
    private @Autowired StudentRepo studentRepo;
    @ModelAttribute
    public void validateLoginUser(HttpSession session) {
        Student student = (Student)session.getAttribute(Constants.STUDENT_SESSION_KEY);
        if(null == student) {
            if(debug) {
                student = studentRepo.findOne(1L);
            }else {
                throw new HttpClientErrorException(HttpStatus.FORBIDDEN);
            }
        }
        currentStudent.set(student);
    }

    Student currentStudent() {
        return currentStudent.get();
    }
}
