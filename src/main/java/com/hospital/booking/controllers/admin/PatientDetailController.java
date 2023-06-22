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

@WebServlet("/admin/patient")
public class PatientDetailController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String accId = req.getParameter("id");
        if (StringUtils.isInteger(accId)) {
            int id = Integer.parseInt(accId);
            AccountDao accountDao = new AccountDao();
            Account account = accountDao.getAccountById(id);
            if (account != null) {
                req.setAttribute("acc", account);
                req.getRequestDispatcher("/admin/patient.jsp").forward(req, resp);
                return;
            }
        }

        req.setAttribute("error", "Không tìm thấy tài khoản");
        req.getRequestDispatcher("/admin/patient.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String accId = req.getParameter("id");
        Account account;

        AccountDao accountDao = new AccountDao();
        if (!StringUtils.isInteger(accId)
                || (account = accountDao.getAccountById(Integer.parseInt(accId))) == null) {
            req.setAttribute("error", "Không tìm thấy tài khoản");
            req.getRequestDispatcher("/admin/patient.jsp").forward(req, resp);
            return;
        }

        account.setActive(Boolean.parseBoolean(req.getParameter("active")));
        account.setUpdatedAt(LocalDateTime.now());

        if (accountDao.update(account)) {
            req.setAttribute("message", "Cập nhật tài khoản bệnh nhân thành công");
        } else {
            req.setAttribute("error", "Đã có lỗi xảy ra. Vui lòng thử lại");
        }

        req.setAttribute("acc", account);
        req.getRequestDispatcher("/admin/patient.jsp").forward(req, resp);
    }

}
