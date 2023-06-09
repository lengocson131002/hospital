package com.hospital.booking.controllers;

import com.hospital.booking.daos.AccountDao;
import com.hospital.booking.enums.Gender;
import com.hospital.booking.enums.Role;
import com.hospital.booking.models.Account;
import com.hospital.booking.utils.BCryptUtils;
import com.microsoft.sqlserver.jdbc.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Objects;

@WebServlet("/register")
public class RegisterController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        AccountDao accountDao = new AccountDao();
        Account account = new Account();
        account.setFirstName(req.getParameter("firstName"));
        account.setLastName(req.getParameter("lastName"));
        account.setEmail(req.getParameter("email"));
        account.setDOB(!StringUtils.isEmpty(req.getParameter("dob")) ? LocalDate.parse(req.getParameter("dob")) : null);
        account.setGender(Gender.valueOf(req.getParameter("gender")));
        account.setPhoneNumber(req.getParameter("phoneNumber"));
        account.setAddress(req.getParameter("address"));
        account.setRole(Role.PATIENT);
        String password = req.getParameter("password");
        account.setPassword(BCryptUtils.encryptPassword(password));
        String confirmPassword = req.getParameter("confirmPassword");

        String error = null;

        if (!Objects.equals(password, confirmPassword)) {
            error = "Nhập lại mật khẩu không đúng";
        }

        if (accountDao.getAccountByEmail(account.getEmail()) != null) {
            error = "Email đã tồn tại";
        }

        if (StringUtils.isEmpty(error) && !accountDao.insertAccount(account)) {
            error = "Có lỗi xảy ra. Vui lòng thử lại";
        }

        if (!StringUtils.isEmpty(error)) {
            req.setAttribute("error", error);
            req.setAttribute("password", password);
            req.setAttribute("confirmPassword", confirmPassword);
            req.setAttribute("account", account);
            req.getRequestDispatcher("register.jsp").forward(req, resp);
            return;
        }

        req.setAttribute("message", "Đăng ký thành công");
        req.getRequestDispatcher("login.jsp").forward(req, resp);
    }
}
