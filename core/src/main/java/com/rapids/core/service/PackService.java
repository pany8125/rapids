package com.rapids.core.service;

import com.rapids.core.domain.*;
import com.rapids.core.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by scott on 3/13/17.
 */
@Service
public class PackService {
    @Autowired
    private PackRepo packRepo;
    @Autowired
    private KnowledgeRepo knowledgeRepo;

    public Pack save(Pack pack){
        return packRepo.save(pack);
    }

    public Knowledge saveKnowledge(Knowledge knowledge){
        return knowledgeRepo.save(knowledge);
    }


    public Pack getById(Long id){
        return packRepo.findOne(id);
    }

    public Knowledge getKnowledgeById(Long id){
        return knowledgeRepo.findOne(id);
    }

    public List<Pack> getPackList(){
        return (List)packRepo.findAll();
    }

    @Transactional
    public void delPack(Long id){
        this.packRepo.delete(id);
    }


    @Transactional
    public void delKnowledge(Long id){
        this.knowledgeRepo.delete(id);
    }

    @Transactional
    public List<Knowledge> getKnowledgeListByTitle(String title){
        return this.knowledgeRepo.queryKnowledgeByTitle(title);
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
