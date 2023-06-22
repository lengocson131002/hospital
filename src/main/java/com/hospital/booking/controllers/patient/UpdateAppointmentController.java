package com.hospital.booking.controllers.patient;

import com.hospital.booking.constants.AppointmentConstants;
import com.hospital.booking.daos.AppointmentDao;
import com.hospital.booking.daos.DepartmentDao;
import com.hospital.booking.daos.ShiftDao;
import com.hospital.booking.enums.AppointmentStatus;
import com.hospital.booking.models.Appointment;
import com.hospital.booking.models.Shift;
import com.hospital.booking.models.Slot;
import com.hospital.booking.utils.SlotUtils;
import com.microsoft.sqlserver.jdbc.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Objects;

@WebServlet("/patient/update-appointment")
public class UpdateAppointmentController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AppointmentDao appointmentDao = new AppointmentDao();
        int appointmentId = Integer.parseInt(req.getParameter("id"));
        Appointment appointment = appointmentDao.getById(appointmentId);
        if (appointment == null) {
            HttpSession session = req.getSession();
            session.setAttribute("error", "Không tìm thấy lịch hẹn");
            resp.sendRedirect(req.getContextPath() + "/patient/appointments");
            return;
        }

        DepartmentDao departmentDao = new DepartmentDao();
        req.setAttribute("departments", departmentDao.getAll());
        req.setAttribute("appointment", appointment);
        req.getRequestDispatcher("/patient/update-appointment.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        int id = Integer.parseInt(req.getParameter("id"));
        AppointmentDao appointmentDao = new AppointmentDao();
        Appointment appointment = appointmentDao.getById(id);

        if (appointment == null || appointment.getShift() == null) {
            req.setAttribute("error", "Không tìm thấy lịch hẹn yêu cầu");
            req.getRequestDispatcher("/patient/appointments.jsp").forward(req, resp);
            return;
        }

        if (!Objects.equals(appointment.getStatus(), AppointmentStatus.CREATED)) {
            session.setAttribute("error", "Không thể cập nhật lịch hẹn");
            resp.sendRedirect(req.getContextPath() + "/patient/appointment?id=" + appointment.getId());
            return;
        }

        appointment.setPatientName(req.getParameter("name"));
        appointment.setPatientPhoneNumber(req.getParameter("phoneNumber"));
        appointment.setPatientEmail(req.getParameter("email"));
        appointment.setPatientNote(req.getParameter("note"));

        if (StringUtils.isInteger(req.getParameter("shiftId"))) {
            int shiftId = Integer.parseInt(req.getParameter("shiftId"));
            ShiftDao shiftDao = new ShiftDao();
            Shift shift = shiftDao.getById(shiftId);
            if (shift != null) {
                appointment.setShift(shift);
                appointment.setDoctor(shift.getDoctor());
            }
        }

        Shift shift = appointment.getShift();
        Slot slot = SlotUtils.getSlot(shift.getSlot());
        LocalDateTime startTime = shift.getDate().atStartOfDay()
                .plusHours(slot.getStartTimeCalendar().get(Calendar.HOUR_OF_DAY))
                .plusMinutes(slot.getStartTimeCalendar().get(Calendar.MINUTE));

        long minutes = ChronoUnit.MINUTES.between(LocalDateTime.now(), startTime);
        System.out.println(minutes);

        if (minutes <= AppointmentConstants.BLOCK_APPOINTMENT_BEFORE) {
            session.setAttribute("error", String.format("Cập nhật lịch hẹn thất bại. Bạn chỉ có thể cập nhật trước %d phút khi cuộc hẹn diễn ra.",AppointmentConstants.BLOCK_APPOINTMENT_BEFORE));
            resp.sendRedirect(req.getContextPath() + "/patient/appointment?id=" + appointment.getId());
            return;
        }

        if (!appointmentDao.update(appointment)) {
            session.setAttribute("error", "Cập nhật lịch hẹn khám thất bại. Vui lòng thử lại!");
            resp.sendRedirect(req.getContextPath() + "/patient/appointment?id=" + appointment.getId());
            return;
        }

        session.setAttribute("message", "Cập nhập lịch hẹn khám thành công!");
        resp.sendRedirect(req.getContextPath() + "/patient/appointment?id=" + appointment.getId());
    }

}
