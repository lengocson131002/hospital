package com.hospital.booking.controllers.admin;

import com.hospital.booking.daos.AccountDao;
import com.hospital.booking.daos.ReviewDao;
import com.hospital.booking.models.Account;
import com.hospital.booking.models.Review;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin/reviews")
public class ReviewController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ReviewDao reviewDao = new ReviewDao();
        List<Review> reviews = reviewDao.getHospitalReviews();

        AccountDao accountDao = new AccountDao();
        List<Account> topDoctors = accountDao.getTopDoctors(5);

        req.setAttribute("reviews", reviews);
        req.setAttribute("topDoctors", topDoctors);

        req.getRequestDispatcher("/admin/reviews.jsp").forward(req, resp);
    }
}
