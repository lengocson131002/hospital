package com.hospital.booking.controllers;

import com.hospital.booking.daos.AccountDao;
import com.hospital.booking.daos.ReviewDao;
import com.hospital.booking.enums.Role;
import com.hospital.booking.models.Account;
import com.hospital.booking.models.Review;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(value = {"", "/home"})
public class HomeController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // top review
        ReviewDao reviewDao = new ReviewDao();
        List<Review> reviews = reviewDao.getTopReview(3);
        req.setAttribute("reviews", reviews);

        // top doctor
        AccountDao accountDao = new AccountDao();
        List<Account> doctors = accountDao.getTopDoctors(4, false);
        req.setAttribute("doctors", doctors);

        req.getRequestDispatcher( "index.jsp").forward(req, resp);
    }
}
