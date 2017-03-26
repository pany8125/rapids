package com.rapids.manage.controller;

import com.rapids.core.domain.Admin;
import com.rapids.core.domain.AdminRole;
import com.rapids.core.service.AdminService;
import com.rapids.manage.dto.ExtEntity;
import com.rapids.manage.security.SecurityConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by scott on 3/22/17.
 */
@RestController
@RequestMapping("/admin")
public class AdminController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    private AdminService adminService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ExtEntity<Admin> getAdminList() {
        List<Admin> list = this.adminService.getAdminList();
        ExtEntity<Admin> entity = new ExtEntity<Admin>();
        entity.setResult(list.size());
        entity.setRows(list);
        LOGGER.info("getAdminList");
        return entity;
    }

    @RequestMapping(value = "/adminrole")
    public ExtEntity<AdminRole> getAdminRoleListByAdminid(@RequestParam("adminid")Integer adminid){
        List<AdminRole> list = this.adminService.getAdminRoleList(adminid);
        ExtEntity<AdminRole> entity = new ExtEntity<>();
        entity.setResult(list.size());
        entity.setRows(list);
        LOGGER.info("getAdminRoleListByAdminid");
        return entity;

    }


    @RequestMapping(value = "/rolelist", method = RequestMethod.GET)
    public List<AdminRole> getRoleList() {
        List<AdminRole> list = this.adminService.getActiveRoleList();
        LOGGER.info("getRoleList");
        return list;
    }


}
