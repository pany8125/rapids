package com.rapids.web.controller;

import com.rapids.core.domain.Student;
import com.rapids.web.Constants;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.client.HttpClientErrorException;
import javax.servlet.http.HttpSession;

/**
 * @author David on 16/11/9.
 */
public abstract class LoginedController {

    private ThreadLocal<Student> currentStudent = new ThreadLocal<>();

    @ModelAttribute
    public void validateLoginUser(HttpSession session) {
        Student student = (Student)session.getAttribute(Constants.STUDENT_SESSION_KEY);
        if(null == student) {
            throw new HttpClientErrorException(HttpStatus.FORBIDDEN);
        }
        currentStudent.set(student);
    }

    public Student currentStudent() {
        return currentStudent.get();
    }
}
