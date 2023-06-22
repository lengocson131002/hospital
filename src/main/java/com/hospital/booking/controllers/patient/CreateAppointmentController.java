package com.hospital.booking.controllers.patient;

import com.hospital.booking.constants.SessionConstants;
import com.hospital.booking.daos.AccountDao;
import com.hospital.booking.daos.AppointmentDao;
import com.hospital.booking.daos.DepartmentDao;
import com.hospital.booking.daos.ShiftDao;
import com.hospital.booking.enums.AppointmentStatus;
import com.hospital.booking.models.Account;
import com.hospital.booking.models.Appointment;
import com.hospital.booking.models.Shift;
import com.hospital.booking.utils.DatetimeUtils;
import com.microsoft.sqlserver.jdbc.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

@WebServlet("/patient/create-appointment")
public class CreateAppointmentController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Account account = (Account) session.getAttribute(SessionConstants.ACCOUNT);
        String name = !StringUtils.isEmpty(req.getParameter("name")) ? req.getParameter("name") : String.format("%s %s", account.getLastName(), account.getFirstName());
        String email = !StringUtils.isEmpty(req.getParameter("email")) ? req.getParameter("email") : account.getEmail();
        String phoneNumber = !StringUtils.isEmpty(req.getParameter("phoneNumber")) ? req.getParameter("phoneNumber") : account.getPhoneNumber();
        String dateParam = req.getParameter("date");
        LocalDate date = !StringUtils.isEmpty(dateParam) ? DatetimeUtils.toDate(dateParam, "yyyy-MM-dd") : null;


        req.setAttribute("name", name);
        req.setAttribute("email", email);
        req.setAttribute("phoneNumber", phoneNumber);
        req.setAttribute("date", date);

        DepartmentDao departmentDao = new DepartmentDao();
        req.setAttribute("departments", departmentDao.getAll());

        if (date != null) {
            ShiftDao shiftDao = new ShiftDao();
            List<Shift> shifts = shiftDao.getAll(date, true);
            shifts.sort((s1, s2) -> s1.getSlot() - s2.getSlot());
            req.setAttribute("shifts", shifts);
        }

        req.getRequestDispatcher("/patient/create-appointment.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        HttpSession session = req.getSession();
        Account account = (Account) session.getAttribute(SessionConstants.ACCOUNT);

        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String phoneNumber = req.getParameter("phoneNumber");
        String note = req.getParameter("note");
        int shiftId = Integer.parseInt(req.getParameter("shiftId"));

        ShiftDao shiftDao = new ShiftDao();
        AccountDao accountDao = new AccountDao();
        AppointmentDao appointmentDao = new AppointmentDao();

        Shift shift = shiftDao.getById(shiftId);
        if (shift == null || shift.getDoctor() == null || shift.isBooked()) {
            session.setAttribute("error", "Lịch làm việc không hợp lệ");
            resp.sendRedirect(req.getContextPath() + "/patient/create-appointment");
            return;
        }


        Account doctor = accountDao.getAccountById(shift.getDoctor().getId());
        if (doctor == null) {
            session.setAttribute("error", "Bác sĩ được chọn không hợp lệ hoặc đã có người đặt. Vui lòng chọn lại");
            resp.sendRedirect(req.getContextPath() + "/patient/create-appointment");
            return;
        }

        Appointment appointment = new Appointment();
        appointment.setDoctor(doctor);
        appointment.setBooker(account);
        appointment.setShift(shift);
        appointment.setPatientName(name);
        appointment.setPatientEmail(email);
        appointment.setPatientPhoneNumber(phoneNumber);
        appointment.setPatientNote(note);
        appointment.setStatus(AppointmentStatus.CREATED);

        if (!appointmentDao.insertAppointment(appointment)) {
            session.setAttribute("error", "Có lỗi xảy ra. Vui lòng thử lại");
            resp.sendRedirect(req.getContextPath() + "/patient/create-appointment");
            return;
        }

        session.setAttribute("message", "Tạo lịch hẹn thành công");
        resp.sendRedirect(req.getContextPath() + "/patient/appointments");

    }
}
