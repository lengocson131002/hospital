package com.hospital.booking.controllers;

import com.hospital.booking.constants.SessionConstants;
import com.hospital.booking.daos.AccountDao;
import com.hospital.booking.daos.ReviewDao;
import com.hospital.booking.enums.Gender;
import com.hospital.booking.enums.Role;
import com.hospital.booking.models.Account;
import com.hospital.booking.models.GooglePojo;
import com.hospital.booking.models.Review;
import com.hospital.booking.utils.GoogleUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/login-google")
public class LoginGoogleController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String code = req.getParameter("code");
        if (code == null || code.isEmpty()) {
            RequestDispatcher dis = req.getRequestDispatcher("login.jsp");
            dis.forward(req, resp);
        } else {
            String accessToken = GoogleUtils.getToken(code);
            GooglePojo googlePojo = GoogleUtils.getUserInfo(accessToken);
            AccountDao accountDao = new AccountDao();
            Account account = accountDao.getAccountByEmail(googlePojo.getEmail());

            if (account == null) {
                account = new Account();
                account.setRole(Role.PATIENT);
                account.setGender(Gender.OTHER);
                account.setEmail(googlePojo.getEmail());
                account.setLastName(googlePojo.getFamily_name() );
                account.setFirstName(googlePojo.getFamily_name());
                account.setAvatar(googlePojo.getPicture());

                if (!accountDao.insertAccount(account)) {
                    RequestDispatcher dis = req.getRequestDispatcher("login.jsp");
                    req.setAttribute("error", "Some error occurred. Try again");
                    dis.forward(req, resp);
                }
            }

            account = accountDao.getAccountByEmail(account.getEmail());
            if (!account.isActive()) {
                String error = "Tài khoản bạn hiện đang bị khóa. Vui lòng liên hệ admin để mở.";
                req.getSession().setAttribute("error", error);
                resp.sendRedirect (req.getContextPath() + "/login");
                return;
            }

            HttpSession session = req.getSession();
            session.setAttribute(SessionConstants.ACCOUNT, account);
            ReviewDao reviewDao = new ReviewDao();
            Review review = reviewDao.getHospitalReview(account.getId());
            if (review != null) {
                session.setAttribute(SessionConstants.REVIEW, review);
            }

            resp.sendRedirect(req.getContextPath() + "/home");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
