package com.hospital.booking.controllers;

import com.hospital.booking.daos.AccountDao;
import com.hospital.booking.enums.Gender;
import com.hospital.booking.enums.Role;
import com.hospital.booking.models.Account;
import com.microsoft.sqlserver.jdbc.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Objects;

@WebServlet("/add-staff")
public class AddStaffController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("add-staff.jsp").forward(req, resp);
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
        account.setGender(!StringUtils.isEmpty(req.getParameter("gender")) ? Gender.valueOf(req.getParameter("gender")) : null);
        account.setPhoneNumber(req.getParameter("phoneNumber"));
        account.setAddress(req.getParameter("address"));
        account.setPassword(req.getParameter("password"));
        account.setRole(Role.valueOf(req.getParameter("role")));
        String confirmPassword = req.getParameter("confirmPassword");

        String error = null;

        if (!Objects.equals(account.getPassword(), confirmPassword)) {
            error = "Confirm password is not matched";
        }

        if (accountDao.getAccountByEmail(account.getEmail()) != null) {
            error = "Email existed";
        }

        if (StringUtils.isEmpty(error) && !accountDao.insertAccount(account)) {
            error = "Some error occurred. Try again";
        }

        if (!StringUtils.isEmpty(error)) {
            req.setAttribute("error", error);
            req.setAttribute("confirmPassword", confirmPassword);
            req.setAttribute("account", account);
            req.getRequestDispatcher("add-staff.jsp").forward(req, resp);
            return;
        }

        req.setAttribute("message", "Register successfully");
        req.getRequestDispatcher("add-staff.jsp").forward(req, resp);
    }
}
