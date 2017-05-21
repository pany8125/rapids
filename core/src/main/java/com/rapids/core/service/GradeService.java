package com.rapids.core.service;

import com.rapids.core.domain.Grade;
import com.rapids.core.domain.Student;
import com.rapids.core.domain.Grade;
import com.rapids.core.domain.Student;
import com.rapids.core.repo.*;
import com.rapids.core.repo.GradeRepo;
import com.rapids.core.repo.StudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by scott on 3/13/17.
 */
@Service
public class GradeService {
    @Autowired
    private GradeRepo gradeRepo;
    @Autowired
    private StudentRepo studentRepo;
    @Autowired
    private StuPackRelaRepo stuPackRelaRepo;
    @Autowired
    private StuKnowledgeRelaRepo stuKnowledgeRelaRepo;

    public Grade save(Grade grade){
        return gradeRepo.save(grade);
    }

    public Student saveStudent(Student student){
        return studentRepo.save(student);
    }

    public boolean checkStudentMobile(String mobile){
        return studentRepo.findByMobile(mobile).size()==0;
    }

    public Grade getById(Long id){
        return gradeRepo.findOne(id);
    }

    public long countGrade(){
        return gradeRepo.count();
    }

    public long countByGradeId(Long gradeId){
        return studentRepo.countByGradeId(gradeId);
    }

    public Student getStudentById(Long id){
        return studentRepo.findOne(id);
    }

    public List<Grade> getGradeList(Integer page, Integer limit){
        PageRequest pageRequest = new PageRequest(page-1, limit);
        return gradeRepo.findAll(pageRequest).getContent();
    }

    @Transactional
    public void delGrade(Long id){
        this.gradeRepo.delete(id);
    }


    @Transactional
    public void delStudent(Long id){
        this.stuPackRelaRepo.deleteByStudentId(id);
        this.stuKnowledgeRelaRepo.deleteByStudentId(id);
        this.studentRepo.delete(id);
    }

    @Transactional
    public List<Student> getStudentListByMobile(String mobile){
        return this.studentRepo.findByMobile(mobile);
    }

    @Transactional
    public List<Student> getStudentListByName(String name){
        return this.studentRepo.findByName(name);
    }

    public List<Student> getStudentList(Long gradeId, Integer page, Integer limit){
        PageRequest pageRequest = new PageRequest(page-1, limit);
        return studentRepo.findByGradeId(gradeId, pageRequest).getContent();
    }


    //    public Admin checkAdmin(String uid, String password){
//        return adminRepo.queryByUidAndPassword(uid, password);
//    }
//
//    public List<Menu> getMenuListByAdminid(int adminId, int parent){
//        return menuRepo.getMenuListByAdminid(adminId, parent);
//    }
//

//
//    public List<AdminRole> getAdminRoleList(int adminId){
//        return adminRoleRepo.getAdminRoleList(adminId);
//    }
//
//
//    public List<AdminRole> getActiveRoleList(){
//        return adminRoleRepo.findByStatus("ACTIVE");
//    }
//
//
//    public AdminRoleMember saveAdminRoleMember(AdminRoleMember adminRoleMember){
//        return adminRoleMemberRepo.save(adminRoleMember);
//    }
//
//    @Transactional
//    public void delAdminRole(int id){
//        this.adminRoleMemberRepo.delete((long) id);
//    }
//
//    @Transactional
//    public void delAdmin(int id){
//        this.adminRoleMemberRepo.deleteByAdminId(id);
//        Admin admin = new Admin();
//        admin.setId(id);
//        this.adminRepo.delete(admin);
//    }
}
