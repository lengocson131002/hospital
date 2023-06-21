package com.hospital.booking.controllers.patient;

import com.hospital.booking.constants.AppointmentConstants;
import com.hospital.booking.constants.BaseConstants;
import com.hospital.booking.constants.DateTimeConstants;
import com.hospital.booking.daos.AppointmentDao;
import com.hospital.booking.enums.AppointmentStatus;
import com.hospital.booking.models.Appointment;
import com.hospital.booking.models.Shift;
import com.hospital.booking.models.Slot;
import com.hospital.booking.utils.ApplicationSettings;
import com.hospital.booking.utils.DatetimeUtils;
import com.hospital.booking.utils.EmailUtils;
import com.hospital.booking.utils.SlotUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;

@WebServlet("/patient/cancel-appointment")
public class CancelAppointmentController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        int id = Integer.parseInt(req.getParameter("id"));
        String reason = req.getParameter("cancelReason");

        AppointmentDao appointmentDao = new AppointmentDao();
        Appointment appointment = appointmentDao.getById(id);

        if (appointment == null || appointment.getShift() == null || !AppointmentStatus.CREATED.equals(appointment.getStatus())) {
            session.setAttribute("error", "Không tìm thấy lịch hẹn yêu cầu");
            resp.sendRedirect(req.getContextPath() + "/patient/appointments");
            return;
        }

        Shift shift = appointment.getShift();
        Slot slot = SlotUtils.getSlot(shift.getSlot());
        LocalDateTime startTime = shift.getDate().atStartOfDay()
                .plusHours(slot.getStartTimeCalendar().get(Calendar.HOUR_OF_DAY))
                .plusMinutes(slot.getStartTimeCalendar().get(Calendar.MINUTE));

        long minutes = ChronoUnit.MINUTES.between(LocalDateTime.now(), startTime);
        System.out.println(minutes);

        if (startTime.isAfter(LocalDateTime.now())
                && minutes <= AppointmentConstants.BLOCK_APPOINTMENT_BEFORE) {
            session.setAttribute("error", String.format("Huỷ lịch hẹn thất bại. Bạn chỉ có thể hủy trước %d phút khi cuộc hẹn diễn ra.",AppointmentConstants.BLOCK_APPOINTMENT_BEFORE));
            req.setAttribute("appointment", appointment);
            req.getRequestDispatcher("/patient/appointment.jsp").forward(req, resp);
            return;
        }

        appointment.setStatus(AppointmentStatus.CANCELED);
        if (!appointmentDao.update(appointment)) {
            req.setAttribute("error", "Huỷ lịch hẹn thất bại. Vui lòng thử lại!");
        } else {
            req.setAttribute("message", "Hủy lịch khám thành công!");
        }

        // send email
        EmailUtils.sendEmail(
                ApplicationSettings.getGmailFrom(),
                appointment.getDoctor().getEmail(),
                "[MEDICAL] Bệnh nhân hủy lịch hẹn",
                String.format("Cuộc hẹn khám bệnh của bạn vào: %s %s-%s với bệnh nhân %s đã bị hủy bởi bệnh nhân \n.Lý do hủy: %s.",
                        DatetimeUtils.toString(appointment.getShift().getDate(), DateTimeConstants.DATE_FORMAT),
                        slot.getStartTime(),
                        slot.getEndTime(),
                        appointment.getPatientName(),
                        reason));

        req.setAttribute("appointment", appointment);
        req.getRequestDispatcher("/patient/appointment.jsp").forward(req, resp);
    }

}
