package com.rapids.core.service;

import com.rapids.core.domain.Admin;
import com.rapids.core.domain.AdminRole;
import com.rapids.core.domain.AdminRoleMember;
import com.rapids.core.domain.Menu;
import com.rapids.core.repo.AdminRepo;
import com.rapids.core.repo.AdminRoleMemberRepo;
import com.rapids.core.repo.AdminRoleRepo;
import com.rapids.core.repo.MenuRepo;
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
public class AdminService {
    @Autowired
    private AdminRepo adminRepo;
    @Autowired
    private MenuRepo menuRepo;
    @Autowired
    private AdminRoleRepo adminRoleRepo;
    @Autowired
    private AdminRoleMemberRepo adminRoleMemberRepo;

    public Admin save(Admin admin){
        return adminRepo.save(admin);
    }


    public long countAdmin(){
        return adminRepo.count();
    }

    public Admin getById(Integer id){
        return adminRepo.findById(id);
    }

    public Admin checkAdmin(String uid, String password){
        return adminRepo.queryByUidAndPassword(uid, password);
    }

    public List<Menu> getMenuListByAdminid(int adminId, int parent){
        return menuRepo.getMenuListByAdminid(adminId, parent);
    }

    public List<Admin> getAdminList(Integer page, Integer limit){
        PageRequest pageRequest = new PageRequest(page-1, limit);
        Page<Admin> admins = adminRepo.findAll(pageRequest);
        return admins.getContent();
    }

    public List<AdminRole> getAdminRoleList(int adminId){
        return adminRoleRepo.getAdminRoleList(adminId);
    }


    public List<AdminRole> getActiveRoleList(){
        return adminRoleRepo.findByStatus("ACTIVE");
    }


    public AdminRoleMember saveAdminRoleMember(AdminRoleMember adminRoleMember){
        return adminRoleMemberRepo.save(adminRoleMember);
    }

    @Transactional
    public void delAdminRole(int id){
        this.adminRoleMemberRepo.delete((long) id);
    }

    @Transactional
    public void delAdmin(int id){
        this.adminRoleMemberRepo.deleteByAdminId(id);
        Admin admin = new Admin();
        admin.setId(id);
        this.adminRepo.delete(admin);
    }
}
