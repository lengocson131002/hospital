package com.hospital.booking.controllers.doctor;

import com.hospital.booking.constants.AppointmentConstants;
import com.hospital.booking.constants.PriceConstants;
import com.hospital.booking.daos.AppointmentDao;
import com.hospital.booking.daos.BillDao;
import com.hospital.booking.enums.AppointmentStatus;
import com.hospital.booking.models.Appointment;
import com.hospital.booking.models.Bill;
import com.hospital.booking.models.Shift;
import com.hospital.booking.models.Slot;
import com.hospital.booking.utils.SlotUtils;

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

@WebServlet("/doctor/complete-appointment")
public class CompleteAppointmentController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        int id = Integer.parseInt(req.getParameter("id"));

        AppointmentDao appointmentDao = new AppointmentDao();
        Appointment appointment = appointmentDao.getById(id);

        if (appointment == null || appointment.getShift() == null) {
            req.setAttribute("error", "Không tìm thấy lịch hẹn yêu cầu");
            req.getRequestDispatcher("/doctor/appointments.jsp").forward(req, resp);
            return;
        }

        // check time
//        Shift shift = appointment.getShift();
//        Slot slot = SlotUtils.getSlot(shift.getSlot());
//        LocalDateTime startTime = shift.getDate().atStartOfDay()
//                .plusHours(slot.getStartTimeCalendar().get(Calendar.HOUR_OF_DAY))
//                .plusMinutes(slot.getStartTimeCalendar().get(Calendar.MINUTE));
//
//        if (startTime.isAfter(LocalDateTime.now())) {
//            session.setAttribute("error", String.format("Hoàn thành lịch hẹn thất bại. Bạn chỉ có thể hoàn thành khi cuộc hẹn đã diễn ra."));
//            resp.sendRedirect( req.getContextPath() + "/doctor/appointment?id=" + id);
//            return;
//        }

        // Create bill
        Bill bill = new Bill();
        bill.setPrice(PriceConstants.DEFAULT_PRICE);
        bill.setPatientName(appointment.getPatientName());
        bill.setPatientPhoneNumber(appointment.getPatientPhoneNumber());
        bill.setPatientEmail(appointment.getPatientEmail());

        BillDao billDao = new BillDao();
        int billId = billDao.insert(bill);

        bill.setId(billId);

        // check re-Examination
        boolean reEx = Boolean.parseBoolean(req.getParameter("reExamination"));
        appointment.setDoctorNote(req.getParameter("doctorNote"));
        appointment.setReExamination(reEx);
        appointment.setStatus(AppointmentStatus.FINISHED);
        appointment.setBill(bill);

        if (billId > 0 && !appointmentDao.update(appointment)) {
            req.setAttribute("error", "Hoàn thành buổi hẹn thất bại. Vui lòng thử lại!");
            req.setAttribute("appointment", appointment);
            req.getRequestDispatcher("/doctor/appointment.jsp").forward(req, resp);
            return;
        }

        resp.sendRedirect(req.getContextPath() + "/doctor/appointments");
    }

}
