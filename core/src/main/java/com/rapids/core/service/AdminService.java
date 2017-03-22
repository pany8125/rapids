package com.rapids.core.service;

import com.rapids.core.domain.Admin;
import com.rapids.core.repo.AdminRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by scott on 3/13/17.
 */
@Service
public class AdminService {
    @Autowired
    private AdminRepo adminRepo;

    public void save(Admin admin){
        adminRepo.save(admin);
    }

    public Admin checkAdmin(String uid, String password){
        return adminRepo.queryByUidAndPassword(uid, password);
    }



}
