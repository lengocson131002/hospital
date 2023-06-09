package com.hospital.booking.controllers.admin;

import com.hospital.booking.daos.AccountDao;
import com.hospital.booking.models.Account;
import com.microsoft.sqlserver.jdbc.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/admin/update-account-status")
public class UpdateAccountStatusController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AccountDao accountDao = new AccountDao();
        int id = Integer.parseInt(req.getParameter("id"));
        boolean active = Boolean.parseBoolean(req.getParameter("active"));

        Account account = accountDao.getAccountById(id);
        if (account != null) {
            account.setActive(active);
            account.setUpdatedAt(LocalDateTime.now());
            if (!accountDao.update(account)) {
                Logger.getLogger(UpdateAccountStatusController.class.getName()).log(Level.SEVERE, "Update account status failed");
            }
        }
        resp.sendRedirect(req.getContextPath() + "/admin/accounts");
    }
}
