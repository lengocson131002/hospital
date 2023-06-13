package com.hospital.booking.controllers.patient;

import com.hospital.booking.constants.SessionConstants;
import com.hospital.booking.daos.AccountDao;
import com.hospital.booking.daos.AppointmentDao;
import com.hospital.booking.daos.ReviewDao;
import com.hospital.booking.models.Account;
import com.hospital.booking.models.Appointment;
import com.hospital.booking.models.Review;
import com.microsoft.sqlserver.jdbc.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/patient/rating-doctor")
public class RatingDoctorController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        int doctorId = Integer.parseInt(req.getParameter("doctorId"));
        AccountDao accountDao = new AccountDao();
        Account doctor = accountDao.getAccountById(doctorId);
        if (doctor == null) {
            session.setAttribute("error", "Không tìm thấy bác sĩ");
            resp.sendRedirect(req.getContextPath() + "/patient/appointments");
            return;
        }

        int appointmentId = Integer.parseInt(req.getParameter("appointmentId"));
        AppointmentDao appointmentDao = new AppointmentDao();
        Appointment appointment = appointmentDao.getById(appointmentId);
        if (appointment == null) {
            session.setAttribute("error", "Không tìm thấy cuộc hẹn");
            resp.sendRedirect(req.getContextPath() + "/patient/appointments");
            return;
        }


        int score = Integer.parseInt(req.getParameter("score"));
        String content = req.getParameter("content");

        Review review = new Review();
        review.setScore(score);
        review.setContent(content);
        review.setDoctor(doctor);
        review.setReviewer((Account) req.getSession().getAttribute(SessionConstants.ACCOUNT));
        review.setAppointment(appointment);

        ReviewDao dao = new ReviewDao();
        if (dao.insert(review)) {
            session.setAttribute("message", "Cảm ơn bạn đã đánh giá. Chúng tôi sẽ ghi nhận mọi ý kiến của bệnh nhân. Xin cảm ơn!");
        } else {
            session.setAttribute("error", "Đánh giá thất bại. Vui lòng thử lại");
        }

        resp.sendRedirect(req.getContextPath() + "/patient/appointments");
    }
}
