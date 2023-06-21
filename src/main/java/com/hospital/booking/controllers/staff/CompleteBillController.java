package com.hospital.booking.controllers.staff;

import com.hospital.booking.daos.AppointmentDao;
import com.hospital.booking.daos.BillDao;
import com.hospital.booking.enums.AppointmentStatus;
import com.hospital.booking.enums.BillStatus;
import com.hospital.booking.models.Appointment;
import com.hospital.booking.models.Bill;
import com.microsoft.sqlserver.jdbc.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/staff/complete-bill")
public class CompleteBillController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String billId = req.getParameter("id");
        if (StringUtils.isEmpty(billId)) {
            resp.sendRedirect(req.getContextPath() + "/staff/bills");
            Logger.getLogger(CompleteBillController.class.getName()).log(Level.SEVERE, "BillId is missing");
            return;
        }

        BillDao billDao = new BillDao();
        int id = Integer.parseInt(billId);
        Bill bill = billDao.getById(id);
        if (bill == null) {
            resp.sendRedirect(req.getContextPath() + "/staff/bills");
            Logger.getLogger(CompleteBillController.class.getName()).log(Level.SEVERE, "Bill is not found");
            return;
        }

        // Update appointment status
        AppointmentDao appointmentDao = new AppointmentDao();
        Appointment appointment = appointmentDao.getByBillId(bill.getId());
        if (appointment != null) {
            appointment.setStatus(AppointmentStatus.COMPLETED);
            appointment.setBill(bill);
            appointmentDao.update(appointment);
        }

        // Update bill
        bill.setNote(req.getParameter("note"));
        bill.setCheckoutAt(LocalDateTime.now());
        bill.setUpdatedAt(LocalDateTime.now());
        bill.setStatus(BillStatus.COMPLETED);

        if (!billDao.update(bill)) {
            req.setAttribute("bill", bill);
            req.setAttribute("error", "Có lỗi xảy ra, vui lòng thử lại");
            req.getRequestDispatcher("/staff/bill.jsp").forward(req, resp);
            return;
        }

        resp.sendRedirect(req.getContextPath() + "/staff/bills");
    }
}
