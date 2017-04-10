package com.rapids.manage.security;

import com.rapids.core.domain.Admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author zhipeng.tian
 *         <p>
 *         2014年10月21日
 */

public class SessionUtil {
    public static Admin getLoginAdmin(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Admin admin = (Admin) session.getAttribute(SecurityConstant.ADMIN_SESSION_ATTRIBUTE);
        return admin;
    }

    public static String getLoginAdminName(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Admin admin = (Admin) session.getAttribute(SecurityConstant.ADMIN_SESSION_ATTRIBUTE);
        return admin.getName();
    }

    public static String getLoginAdminUid(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Admin admin = (Admin) session.getAttribute(SecurityConstant.ADMIN_SESSION_ATTRIBUTE);
        return admin.getUid();
    }

    public static void setLoginAdmin(HttpServletRequest request, Admin admin) {
        HttpSession session = request.getSession();
        session.setAttribute(SecurityConstant.ADMIN_SESSION_ATTRIBUTE, admin);
    }
}
