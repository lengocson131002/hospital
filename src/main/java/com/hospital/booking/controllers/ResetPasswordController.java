package com.hospital.booking.controllers;

import com.hospital.booking.constants.SessionConstants;
import com.hospital.booking.daos.AccountDao;
import com.hospital.booking.daos.DepartmentDao;
import com.hospital.booking.daos.TokenDao;
import com.hospital.booking.enums.TokenType;
import com.hospital.booking.models.Account;
import com.hospital.booking.models.Token;
import com.hospital.booking.utils.BCryptUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Objects;

@WebServlet("/reset-password")
public class ResetPasswordController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String token = req.getParameter("token");
        req.setAttribute("token", token);
        req.getRequestDispatcher("reset-password.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String tokenValue = req.getParameter("token");
        req.setAttribute("token", tokenValue);
        String password = req.getParameter("password");
        String confirmPassword = req.getParameter("confirmPassword");

        TokenDao tokenDao = new TokenDao();
        Token token = tokenDao.getToken(tokenValue, TokenType.FORGOT_PASSWORD);
        if (token == null || token.getExpiredAt().isBefore(LocalDateTime.now())) {
            req.setAttribute("error", "Invalid or Expired token");
            req.getRequestDispatcher("login.jsp").forward(req, resp);
            return;
        }

        if (!Objects.equals(password, confirmPassword)) {
            req.setAttribute("password", password);
            req.setAttribute("confirmPassword", confirmPassword);
            req.setAttribute("error", "Confirm password is not matched");
            req.getRequestDispatcher("reset-password.jsp").forward(req, resp);
            return;
        }

        AccountDao accountDao = new AccountDao();
        if (!accountDao.updatePassword(token.getAccountId(), BCryptUtils.encryptPassword(password))) {
            req.setAttribute("password", password);
            req.setAttribute("confirmPassword", confirmPassword);
            req.setAttribute("error", "Reset password failed");
            req.getRequestDispatcher("reset-password.jsp").forward(req, resp);
            return;
        }

        tokenDao.deleteToken(token.getToken(), TokenType.FORGOT_PASSWORD);
        req.setAttribute("message", "Update password successfully");
        req.getRequestDispatcher("login.jsp").forward(req, resp);
    }
}
