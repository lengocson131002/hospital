package com.hospital.booking.controllers;

import com.hospital.booking.constants.GoogleConstants;
import com.hospital.booking.constants.SessionConstants;
import com.hospital.booking.daos.AccountDao;
import com.hospital.booking.daos.ReviewDao;
import com.hospital.booking.models.Account;
import com.hospital.booking.models.Review;
import com.hospital.booking.utils.ApplicationSettings;
import com.hospital.booking.utils.BCryptUtils;
import com.microsoft.sqlserver.jdbc.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Objects;

@WebServlet("/login")
public class LoginController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String error = req.getParameter("error");
        req.setAttribute("loginGgUrl", getLoginGgUrl());
        if (!StringUtils.isEmpty(error)) {
            req.setAttribute("error", error);
        }
        req.getRequestDispatcher("login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AccountDao accountDao = new AccountDao();
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String remember = req.getParameter("remember");

        Account account = accountDao.getAccountByEmail(email.trim());
        boolean hasError = false;
        if (account == null || account.getPassword() == null || !BCryptUtils.checkPassword(password, account.getPassword())) {
            hasError = true;
            req.setAttribute("error", "Email hoặc mật khẩu không đúng");
        } else if (!account.isActive()) {
            hasError = true;
            req.setAttribute("error", "Tài khoản bạn hiện đang bị khóa. Vui lòng liên hệ admin để mở.");
        }

        if (hasError) {
            req.setAttribute("email", email);
            req.setAttribute("password", password);
            req.setAttribute("loginGgUrl", getLoginGgUrl());
            req.getRequestDispatcher("login.jsp").forward(req, resp);
            return;
        }

        HttpSession session = req.getSession();
        session.setAttribute(SessionConstants.ACCOUNT, account);

        //Review
        ReviewDao reviewDao = new ReviewDao();
        Review review = reviewDao.getHospitalReview(account.getId());
        if (review != null) {
            session.setAttribute(SessionConstants.REVIEW, review);
        }

        resp.sendRedirect("home");
    }

    private String getLoginGgUrl() {
        String clientId = ApplicationSettings.getGoogleClientId();
        String redirectUri = GoogleConstants.GOOGLE_REDIRECT_URI;
        return "https://accounts.google.com/o/oauth2/auth?scope=email&redirect_uri=" + redirectUri + "&response_type=code&client_id=" + clientId + "&approval_prompt=force";
    }
}
