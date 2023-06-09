package com.hospital.booking.controllers.patient;

import com.hospital.booking.constants.SessionConstants;
import com.hospital.booking.daos.ReviewDao;
import com.hospital.booking.models.Account;
import com.hospital.booking.models.Review;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/patient/rating-hospital")
public class RatingHospitalController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer score = Integer.parseInt(req.getParameter("score"));
        String content = req.getParameter("content");

        Account account = (Account) req.getSession().getAttribute(SessionConstants.ACCOUNT);

        Review review = new Review();
        review.setScore(score);
        review.setContent(content);
        review.setReviewer(account);

        ReviewDao dao = new ReviewDao();
        if (dao.insert(review)) {
            req.setAttribute("message", "Cảm ơn bạn đã đánh giá. Chúng tôi sẽ ghi nhận mọi ý kiến của bệnh nhân. Xin cảm ơn!");
            req.getSession().setAttribute(SessionConstants.REVIEW, review);
        } else {
            req.setAttribute("error", "Đánh giá thất bại. Vui lòng thử lại");
        }

        resp.sendRedirect(req.getContextPath() + "/home");
    }
}
