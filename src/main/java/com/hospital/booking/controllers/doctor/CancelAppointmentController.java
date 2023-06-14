package com.hospital.booking.controllers.doctor;

import com.hospital.booking.constants.AppointmentConstants;
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
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;

@WebServlet("/doctor/cancel-appointment")
public class CancelAppointmentController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));

        AppointmentDao appointmentDao = new AppointmentDao();
        Appointment appointment = appointmentDao.getById(id);

        if (appointment == null || appointment.getShift() == null) {
            req.setAttribute("error", "Không tìm thấy lịch hẹn yêu cầu");
            req.getRequestDispatcher("/doctor/appointments.jsp").forward(req, resp);
            return;
        }

        Shift shift = appointment.getShift();
        Slot slot = SlotUtils.getSlot(shift.getSlot());
        LocalDateTime startTime = shift.getDate().atStartOfDay()
                .plusHours(slot.getStartTimeCalendar().get(Calendar.HOUR_OF_DAY))
                .plusMinutes(slot.getStartTimeCalendar().get(Calendar.MINUTE));

        long minutes = ChronoUnit.MINUTES.between(LocalDateTime.now(), startTime);
        System.out.println(minutes);

        if (startTime.isAfter(LocalDateTime.now()) && minutes <= AppointmentConstants.BLOCK_APPOINTMENT_BEFORE) {
            req.setAttribute("error", String.format("Huỷ lịch hẹn thất bại. Bạn chỉ có thể hủy trước %d phút khi cuộc hẹn diễn ra.",AppointmentConstants.BLOCK_APPOINTMENT_BEFORE));
            req.setAttribute("appointment", appointment);
            req.getRequestDispatcher("/doctor/appointment.jsp").forward(req, resp);
            return;
        }

        appointment.setDoctorNote(req.getParameter("doctorNote"));
        appointment.setStatus(AppointmentStatus.CANCELED);
        if (!appointmentDao.update(appointment)) {
            req.setAttribute("error", "Huỷ lịch hẹn thất bại. Vui lòng thử lại!");
        } else {
            // Send email
             EmailUtils.sendEmail(
                    ApplicationSettings.getGmailFrom(),
                    appointment.getBooker().getEmail(),
                    "[MEDICAL] Hủy lịch hẹn khám bệnh",
                    String.format("Cuộc hẹn khám bệnh của bạn vào: %s %s-%s với bác sĩ %s đã bị hủy. Xin lỗi vì sự bất tiện này.",
                            DatetimeUtils.toString(appointment.getShift().getDate(), DateTimeConstants.DATE_FORMAT),
                            slot.getStartTime(),
                            slot.getEndTime(),
                            appointment.getDoctor().getLastName() + " " + appointment.getDoctor().getLastName()));

            req.setAttribute("message", "Hủy lịch hẹn thành công. Đã gửi thông báo đến bệnh nhân.");
        }

        req.setAttribute("appointment", appointment);
        req.getRequestDispatcher("/doctor/appointment.jsp").forward(req, resp);
    }

}
