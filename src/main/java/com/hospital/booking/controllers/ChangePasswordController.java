package com.hospital.booking.controllers;

import com.hospital.booking.constants.SessionConstants;
import com.hospital.booking.daos.AccountDao;
import com.hospital.booking.daos.DepartmentDao;
import com.hospital.booking.models.Account;
import com.hospital.booking.utils.BCryptUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@WebServlet("/change-password")
public class ChangePasswordController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Account account = (Account) req.getSession().getAttribute(SessionConstants.ACCOUNT);
        if (account == null) {
            resp.sendRedirect("login");
            return;
        }

        String password = req.getParameter("password");
        String confirmPassword = req.getParameter("confirmPassword");
        DepartmentDao departmentDao = new DepartmentDao();
        req.setAttribute("departments", departmentDao.getAll());

        if (!Objects.equals(password, confirmPassword)) {
            req.setAttribute("password", password);
            req.setAttribute("confirmPassword", confirmPassword);
            req.setAttribute("error", "Confirm password is not matched");
            req.getRequestDispatcher("profile.jsp").forward(req, resp);
            return;
        }

        AccountDao accountDao = new AccountDao();
        if (!accountDao.updatePassword(account.getId(), BCryptUtils.encryptPassword(password))) {
            req.setAttribute("password", password);
            req.setAttribute("confirmPassword", confirmPassword);
            req.setAttribute("error", "Change password failed");
            req.getRequestDispatcher("profile.jsp").forward(req, resp);
            return;
        }

        req.setAttribute("message", "Update password successfully");
        req.getRequestDispatcher("profile.jsp").forward(req, resp);
    }
}
