package com.rapids.manage.controller;

import com.rapids.core.domain.*;
import com.rapids.core.service.GradeService;
import com.rapids.core.service.PackService;
import com.rapids.core.service.StudentService;
import com.rapids.manage.dto.ExtEntity;
import com.rapids.manage.dto.ExtStatusEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

/**
 * Created by scott on 3/22/17.
 */
@RestController
@RequestMapping("/rela")
public class RelaController {
    private static final Logger LOGGER = LoggerFactory.getLogger(RelaController.class);

    @Autowired
    private GradeService gradeService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private PackService packService;


    @RequestMapping(value = "/getStudent")
    public ExtEntity<Student> getStudentListByTitle(@RequestParam("sMobile") String mobile) {
        List<Student> list;
        if( mobile.startsWith("1") && mobile.length() == 11){ //手机号
            list = this.gradeService.getStudentListByMobile(mobile);
        }else { //姓名
            list = this.gradeService.getStudentListByName(mobile);
        }
        ExtEntity<Student> entity = new ExtEntity<>();
        entity.setResult(list.size());
        entity.setRows(list);
        LOGGER.info("getStudentListByMobile");
        return entity;
    }


    @RequestMapping(value = "/stuPackRela")
    public ExtEntity<StuPackRela> getStuPackRela(@RequestParam("studentid") long studentId) {
        List<StuPackRela> list = this.studentService.getByStu(studentId);
        ExtEntity<StuPackRela> entity = new ExtEntity<>();
        entity.setResult(list.size());
        entity.setRows(list);
        LOGGER.info("getStuPackRela");
        return entity;
    }


    @RequestMapping(value = "/stuKnowRela")
    public ExtEntity<StuKnowledgeRela> getStuKnowRela(@RequestParam("studentid") long studentId) {
        List<StuKnowledgeRela> list = this.studentService.getKnowByStu(studentId);
        ExtEntity<StuKnowledgeRela> entity = new ExtEntity<>();
        entity.setResult(list.size());
        entity.setRows(list);
        LOGGER.info("getStuPackRela");
        return entity;
    }

    @RequestMapping(value = "/delRela")
    public ExtStatusEntity delRela(@RequestParam("studentId") long studentId, @RequestParam("packId") long packId) {
        ExtStatusEntity entity = new ExtStatusEntity();
        try {
            this.studentService.delRela(studentId, packId);
            entity.setMsg("succeed");
            entity.setSuccess(true);
        } catch (Exception ex) {
            LOGGER.error("delRela error", ex);
            entity.setMsg("删除失败");
            entity.setSuccess(false);
        }
        LOGGER.info("delRela");
        return entity;
    }

    @RequestMapping(value = "/saveStudentPack", method = RequestMethod.POST)
    public ExtStatusEntity saveRela(@RequestParam(value = "studentid") long studentId,
                                    @RequestParam("packid") long packId) {
        ExtStatusEntity result = new ExtStatusEntity();
        try {
            Pack pack = packService.getById(packId);
            if (null == pack) {
                result.setMsg("知识包不存在");
                result.setSuccess(false);
                return result;
            }
            List<Knowledge> knowledges = packService.getKnowledgeListByPack(packId);
            if (knowledges.isEmpty()) {
                result.setMsg("知识包下无知识点");
                result.setSuccess(false);
                return result;
            }
            if (!this.studentService.savaRela(studentId, pack, knowledges)) {
                result.setMsg("添加知识包关联失败");
                result.setSuccess(false);
                return result;
            }
        } catch (Exception ex) {
            LOGGER.error("save rela error", ex);
            result.setMsg("保存失败");
            result.setSuccess(false);
        }
        result.setMsg("succeed");
        result.setSuccess(true);
        LOGGER.info("saveRela");
        return result;
    }

}
