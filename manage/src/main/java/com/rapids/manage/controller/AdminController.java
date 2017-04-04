package com.rapids.manage.controller;

import com.rapids.core.domain.Admin;
import com.rapids.core.domain.AdminRole;
import com.rapids.core.domain.AdminRoleMember;
import com.rapids.core.service.AdminService;
import com.rapids.manage.dto.ExtEntity;
import com.rapids.manage.dto.ExtStatusEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.URLDecoder;
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
    public ExtEntity<Admin> getAdminList(
            @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
            @RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit) {
        List<Admin> list = this.adminService.getAdminList(page, limit);
        ExtEntity<Admin> entity = new ExtEntity<Admin>();
        entity.setResult(adminService.countAdmin());
        entity.setRows(list);
        LOGGER.info("getAdminList");
        return entity;
    }

    @RequestMapping(value = "/adminrole")
    public ExtEntity<AdminRole> getAdminRoleListByAdminid(@RequestParam("adminid") Integer adminid) {
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

    @RequestMapping(value = "/saveAdmin", method = RequestMethod.POST)
    public ExtStatusEntity saveAdmin(@RequestParam(value = "id", required = false) Integer id,
                                     @RequestParam("uid") String uid,
                                     @RequestParam("password") String password,
                                     @RequestParam("name") String name,
                                     @RequestParam("mobile") String mobile,
                                     @RequestParam("adminName") String adminName) {
        ExtStatusEntity result = new ExtStatusEntity();
        try {
            Admin adminDTO = new Admin();
            if (id == null) {
                adminDTO.setUid(uid);
                adminDTO.setCreateBy(URLDecoder.decode(adminName, "UTF-8"));
            } else {
                adminDTO = this.adminService.getById(id);
            }
            adminDTO.setPassword(DigestUtils.md5DigestAsHex(password.getBytes()));
            adminDTO.setName(name);
            adminDTO.setMobile(mobile);
            Admin admin = this.adminService.save(adminDTO);
            if (null == admin) {
                result.setMsg("添加或修改账号失败");
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
        LOGGER.info("saveAdmin");
        return result;
    }

    @RequestMapping(value = "/saveAdminRoleMember", method = RequestMethod.POST)
    public
    @ResponseBody
    ExtStatusEntity saveAdminRoleMember(@RequestParam("adminid") int adminId,
                                        @RequestParam("roleid") int roleId, HttpServletRequest request) {
        ExtStatusEntity result = new ExtStatusEntity();
        try {
            AdminRoleMember adminRoleMember = new AdminRoleMember();
            adminRoleMember.setAdminId(adminId);
            adminRoleMember.setRoleId(roleId);
            AdminRoleMember a = this.adminService.saveAdminRoleMember(adminRoleMember);
            if (null == a) {
                result.setMsg("添加权限失败");
                result.setSuccess(false);
            } else {
                result.setMsg("succeed");
                result.setSuccess(true);
            }
        } catch (Exception ex) {
            LOGGER.error("saveAdminRoleMember error", ex);
            result.setMsg("保存失败");
            result.setSuccess(false);
        }
        LOGGER.info("saveAdminRoleMember");
        return result;
    }


    @RequestMapping(value = "/delAdminRole")
    public ExtStatusEntity delAdminRole(@RequestParam("id") int id, HttpServletRequest request) {
        ExtStatusEntity entity = new ExtStatusEntity();
        try {
            this.adminService.delAdminRole(id);
            entity.setMsg("succeed");
            entity.setSuccess(true);
        } catch (Exception ex) {
            LOGGER.error("delAdminRole error", ex);
            entity.setMsg("删除失败");
            entity.setSuccess(false);
        }
        LOGGER.info("delAdminRole");
        return entity;
    }


    @RequestMapping(value = "/delAdmin")
    public ExtStatusEntity delAdminRole(@RequestParam("id") int id) {
        ExtStatusEntity entity = new ExtStatusEntity();
        try {
            this.adminService.delAdmin(id);
            entity.setMsg("succeed");
            entity.setSuccess(true);
        } catch (Exception ex) {
            LOGGER.error("delAdmin error", ex);
            entity.setMsg("删除失败");
            entity.setSuccess(false);
        }
        LOGGER.info("delAdmin");
        return entity;
    }

}
