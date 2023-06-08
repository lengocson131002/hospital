package com.hospital.booking.controllers.patient;

import com.hospital.booking.constants.AppointmentConstants;
import com.hospital.booking.constants.BaseConstants;
import com.hospital.booking.daos.AppointmentDao;
import com.hospital.booking.enums.AppointmentStatus;
import com.hospital.booking.models.Appointment;
import com.hospital.booking.models.Shift;
import com.hospital.booking.models.Slot;
import com.hospital.booking.utils.SlotUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;

@WebServlet("/patient/cancel-appointment")
public class CancelAppointmentController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));

        AppointmentDao appointmentDao = new AppointmentDao();
        Appointment appointment = appointmentDao.getById(id);

        if (appointment == null || appointment.getShift() == null) {
            req.setAttribute("error", "Không tìm thấy lịch hẹn yêu cầu");
            req.getRequestDispatcher("/patient/appointments.jsp").forward(req, resp);
            return;
        }

        Shift shift = appointment.getShift();
        Slot slot = SlotUtils.getSlot(shift.getSlot());
        LocalDateTime startTime = shift.getDate().atStartOfDay()
                .plusHours(slot.getStartTimeCalendar().get(Calendar.HOUR_OF_DAY))
                .plusMinutes(slot.getStartTimeCalendar().get(Calendar.MINUTE));

        long minutes = ChronoUnit.MINUTES.between(LocalDateTime.now(), startTime);
        System.out.println(minutes);

        if (minutes <= AppointmentConstants.BLOCK_APPOINTMENT_BEFORE) {
            req.setAttribute("error", String.format("Huỷ lịch hẹn thất bại. Bạn chỉ có thể hủy trước %d phút khi cuộc hẹn diễn ra.",AppointmentConstants.BLOCK_APPOINTMENT_BEFORE));
            req.setAttribute("appointment", appointment);
            req.getRequestDispatcher("/patient/appointment.jsp").forward(req, resp);
            return;
        }

        appointment.setStatus(AppointmentStatus.CANCELED);
        if (!appointmentDao.update(appointment)) {
            req.setAttribute("error", "Huỷ lịch hẹn thất bại. Vui lòng thử lại!");
            req.setAttribute("appointment", appointment);
            req.getRequestDispatcher("/patient/appointment.jsp").forward(req, resp);
            return;
        }

        resp.sendRedirect(req.getContextPath() + "/patient/appointments");
    }

}
