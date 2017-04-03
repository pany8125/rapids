package com.rapids.core.service;

import com.rapids.core.domain.Grade;
import com.rapids.core.domain.Student;
import com.rapids.core.domain.Grade;
import com.rapids.core.domain.Student;
import com.rapids.core.repo.GradeRepo;
import com.rapids.core.repo.StudentRepo;
import com.rapids.core.repo.GradeRepo;
import com.rapids.core.repo.StudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
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

    public Grade save(Grade grade){
        return gradeRepo.save(grade);
    }

    public Student saveStudent(Student student){
        return studentRepo.save(student);
    }

    public Grade getById(Long id){
        return gradeRepo.findOne(id);
    }

    public Student getStudentById(Long id){
        return studentRepo.findOne(id);
    }

    public List<Grade> getGradeList(){
        return (List)gradeRepo.findAll();
    }

    @Transactional
    public void delGrade(Long id){
        this.gradeRepo.delete(id);
    }


    @Transactional
    public void delStudent(Long id){
        this.studentRepo.delete(id);
    }

    @Transactional
    public List<Student> getStudentListByMobile(String mobile){
        return this.studentRepo.findByMobile(mobile);
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
