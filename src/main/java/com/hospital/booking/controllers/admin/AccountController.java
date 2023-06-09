package com.hospital.booking.controllers.admin;

import com.hospital.booking.daos.AccountDao;
import com.hospital.booking.enums.Role;
import com.hospital.booking.models.Account;
import com.microsoft.sqlserver.jdbc.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/admin/accounts")
public class AccountController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Account> accounts = new ArrayList<>();
        try {
            String roleParam = req.getParameter("role");
            if (StringUtils.isEmpty(roleParam)) {
                roleParam = "DOCTOR";
            }
            Role role = Role.valueOf(roleParam);
            AccountDao accountDao = new AccountDao();
            accounts = accountDao.getAll(role);
            req.setAttribute("role", role);
        } catch (Exception ex) {
            Logger.getLogger(AccountController.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
            req.setAttribute("error", "Có lỗi xảy ra. Vui lòng thử lại!");
            accounts = new ArrayList<>();
        }

        req.setAttribute("accounts", accounts);
        req.getRequestDispatcher("/admin/accounts.jsp").forward(req, resp);
    }
}
