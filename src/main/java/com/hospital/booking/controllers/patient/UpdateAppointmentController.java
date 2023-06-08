package com.hospital.booking.controllers.patient;

import com.hospital.booking.constants.AppointmentConstants;
import com.hospital.booking.daos.AppointmentDao;
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
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Objects;

@WebServlet("/patient/update-appointment")
public class UpdateAppointmentController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));

        AppointmentDao appointmentDao = new AppointmentDao();
        Appointment appointment = appointmentDao.getById(id);

        if (appointment == null || appointment.getShift() == null) {
            req.setAttribute("error", "Không tìm thấy lịch hẹn yêu cầu");
            req.getRequestDispatcher("/patient/appointments.jsp").forward(req, resp);
            return;
        }

        if (!Objects.equals(appointment.getStatus(), AppointmentStatus.CREATED)) {
            req.setAttribute("error", "Không thể cập nhật lịch hẹn");
            req.setAttribute("appointment", appointment);
            req.getRequestDispatcher("/patient/appointment.jsp").forward(req, resp);
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
            req.setAttribute("error", String.format("Cập nhật lịch hẹn thất bại. Bạn chỉ có thể cập nhật trước %d phút khi cuộc hẹn diễn ra.",AppointmentConstants.BLOCK_APPOINTMENT_BEFORE));
            req.setAttribute("appointment", appointment);
            req.getRequestDispatcher("/patient/appointment.jsp").forward(req, resp);
            return;
        }

        if (!appointmentDao.update(appointment)) {
            req.setAttribute("error", "Cập nhật lịch hẹn khám thất bại. Vui lòng thử lại!");
            req.setAttribute("appointment", appointment);
            req.getRequestDispatcher("/patient/appointment.jsp").forward(req, resp);
            return;
        }

        req.setAttribute("appointment", appointment);
        req.setAttribute("message", "Cập nhập lịch hẹn khám thành công!");
        req.getRequestDispatcher("/patient/appointment.jsp").forward(req, resp);
    }


}
