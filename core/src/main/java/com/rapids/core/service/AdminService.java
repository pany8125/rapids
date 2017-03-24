package com.rapids.core.service;

import com.rapids.core.domain.Admin;
import com.rapids.core.domain.Menu;
import com.rapids.core.repo.AdminRepo;
import com.rapids.core.repo.MenuRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public void save(Admin admin){
        adminRepo.save(admin);
    }

    public Admin checkAdmin(String uid, String password){
        return adminRepo.queryByUidAndPassword(uid, password);
    }

    public List<Menu> getMenuListByAdminid(int adminId, int parent){
        return menuRepo.getMenuListByAdminid(adminId, parent);
    }


}
