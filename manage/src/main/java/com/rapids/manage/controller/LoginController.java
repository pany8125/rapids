package com.rapids.manage.controller;

import com.alibaba.fastjson.JSONObject;
import com.rapids.core.domain.Admin;
import com.rapids.core.service.AdminService;
import com.rapids.manage.dto.ExtStatusEntity;
import com.rapids.manage.security.SecurityConstant;
import com.rapids.manage.security.SessionUtil;
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

/**
 * Created by scott on 3/22/17.
 */
@RestController
@RequestMapping("/login")
public class LoginController {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private AdminService adminService;


    /**
     * user info checked
     *
     * @param request
     * @param model
     * @param uid
     * @param password
     * @return
     */
    @RequestMapping(value = "/loginValid", method = RequestMethod.POST)
    public ExtStatusEntity userLogin(HttpServletRequest request, Model model, @RequestParam("name") String uid, @RequestParam("password") String password) {
        ExtStatusEntity resp = new ExtStatusEntity();

//        Admin admin = adminService.checkAdmin(uid, Util.toSHA1(password.getBytes()));
        Admin admin = adminService.checkAdmin(uid, password);//TODO:测试完了改回来
        if (null == admin) {
            resp.setMsg("账号密码错误或者账号不存在");
            resp.setSuccess(false);
        } else {
            SessionUtil.setLoginAdmin(request, admin);
            resp.setMsg(JSONObject.toJSONString(admin));
            resp.setSuccess(true);
            LOGGER.info(admin.getName() + " login");
        }
        return resp;
    }

    /**
     * logout
     *
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/logout")
    public String userLogout(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        session.removeAttribute(SecurityConstant.ADMIN_SESSION_ATTRIBUTE);
        return "index";
    }


}
