package com.rapids.manage.controller;

import com.rapids.core.domain.Student;
import com.rapids.core.domain.Grade;
import com.rapids.core.service.GradeService;
import com.rapids.core.service.GradeService;
import com.rapids.manage.dto.ExtEntity;
import com.rapids.manage.dto.ExtStatusEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URLDecoder;
import java.util.Date;
import java.util.List;

/**
 * Created by scott on 3/22/17.
 */
@RestController
@RequestMapping("/grade")
public class StudentController {
    private static final Logger LOGGER = LoggerFactory.getLogger(StudentController.class);
    
    @Autowired
    private GradeService gradeService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ExtEntity<Grade> getGradeList(
            @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit) {
        List<Grade> list = this.gradeService.getGradeList(page, limit);
        ExtEntity<Grade> entity = new ExtEntity<>();
        entity.setResult(gradeService.countGrade());
        entity.setRows(list);
        LOGGER.info("getGradeList");
        return entity;
    }

    @RequestMapping(value = "/gradeStudent", method = RequestMethod.GET)
    public ExtEntity<Student> getGradeStudent(
            @RequestParam(value = "gradeId") Long gradeId,
            @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit) {
        List<Student> list = this.gradeService.getStudentList(gradeId, page, limit);
        ExtEntity<Student> entity = new ExtEntity<>();
        entity.setResult(gradeService.countByGradeId(gradeId));
        entity.setRows(list);
        LOGGER.info("getGradeList");
        return entity;
    }

    @RequestMapping(value = "/getStudent")
    public ExtEntity<Student> getStudentListByTitle(@RequestParam("sMobile") String mobile) {
        List<Student> list = this.gradeService.getStudentListByMobile(mobile);
        ExtEntity<Student> entity = new ExtEntity<>();
        entity.setResult(list.size());
        entity.setRows(list);
        LOGGER.info("getStudentListByMobile");
        return entity;
    }


    @RequestMapping(value = "/saveGrade", method = RequestMethod.POST)
    public ExtStatusEntity saveGrade(@RequestParam(value = "id", required = false) Long id,
                                     @RequestParam("name") String name,
                                     @RequestParam("gradeYear") String gradeYear,
                                     @RequestParam("description") String description) {
        ExtStatusEntity result = new ExtStatusEntity();
        try {
            Grade gradeDTO = new Grade();
            gradeDTO.setId(id);
            gradeDTO.setName(name);
            gradeDTO.setGradeYear(gradeYear);
            gradeDTO.setDescription(description);
            Grade grade = this.gradeService.save(gradeDTO);
            if (null == grade) {
                result.setMsg("添加或修改班级失败");
                result.setSuccess(false);
            } else {
                result.setMsg("succeed");
                result.setSuccess(true);
            }
        } catch (Exception ex) {
            LOGGER.error("save grade error", ex);
            result.setMsg("保存失败");
            result.setSuccess(false);
        }
        LOGGER.info("saveGrade");
        return result;
    }

    @RequestMapping(value = "/saveStudent", method = RequestMethod.POST)
    public ExtStatusEntity saveStudent(@RequestParam(value = "id", required = false) Long id,
                                     @RequestParam("gradeId") Long gradeId,
                                     @RequestParam("name") String name,
                                     @RequestParam("password") String password,
                                     @RequestParam("age") Integer age,
                                     @RequestParam("email") String email,
                                     @RequestParam("mobile") String mobile,
                                     @RequestParam("sex") Integer sex,
                                     @RequestParam("adminName") String adminName) {
        ExtStatusEntity result = new ExtStatusEntity();
        if(!gradeService.checkStudentMobile(mobile)){
            result.setMsg("手机号重复");
            result.setSuccess(false);
            return result;
        }
        try {
            Student studentDTO = new Student();
            if (id == null) {
                studentDTO.setEditor(URLDecoder.decode(adminName, "UTF-8"));
            } else {
                studentDTO = this.gradeService.getStudentById(id);
            }
            studentDTO.setGradeId(gradeId);
            studentDTO.setName(name);
            if(password.length()!=32){
                studentDTO.setPassword(DigestUtils.md5DigestAsHex(password.getBytes()));
            }
            studentDTO.setAge(age);
            studentDTO.setEmail(email);
            studentDTO.setSex(sex);
            studentDTO.setMobile(mobile);
            studentDTO.setCreateTime(new Date());
            Student student = this.gradeService.saveStudent(studentDTO);
            if (null == student) {
                result.setMsg("添加或修改学生失败");
                result.setSuccess(false);
            } else {
                result.setMsg("succeed");
                result.setSuccess(true);
            }
        } catch (Exception ex) {
            LOGGER.error("save admin error", ex);
            result.setMsg("保存失败");
            result.setSuccess(false);
        }
        LOGGER.info("saveStudent");
        return result;
    }



    @RequestMapping(value = "/delStudent")
    public ExtStatusEntity delStudent(@RequestParam("id") Long id) {
        ExtStatusEntity entity = new ExtStatusEntity();
        try {
            this.gradeService.delStudent(id);
            entity.setMsg("succeed");
            entity.setSuccess(true);
        } catch (Exception ex) {
            LOGGER.error("delStudent error", ex);
            entity.setMsg("删除失败");
            entity.setSuccess(false);
        }
        LOGGER.info("delStudent");
        return entity;
    }


    @RequestMapping(value = "/delGrade")
    public ExtStatusEntity delGrade(@RequestParam("id") Long id) {
        ExtStatusEntity entity = new ExtStatusEntity();
        try {
            this.gradeService.delGrade(id);
            entity.setMsg("succeed");
            entity.setSuccess(true);
        } catch (Exception ex) {
            LOGGER.error("delGrade error", ex);
            entity.setMsg("删除失败");
            entity.setSuccess(false);
        }
        LOGGER.info("delGrade");
        return entity;
    }

}
