package com.hospital.booking.controllers.staff;

import com.hospital.booking.daos.BillDao;
import com.hospital.booking.enums.BillStatus;
import com.hospital.booking.models.Bill;
import com.hospital.booking.utils.DatetimeUtils;
import com.microsoft.sqlserver.jdbc.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@WebServlet("/staff/bills")
public class BillController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BillDao billDao = new BillDao();

        BillStatus billStatus = null;
        String status = req.getParameter("status");
        if (!StringUtils.isEmpty(status)) {
            billStatus = BillStatus.valueOf(status);
        }

        LocalDateTime billFrom = null;
        String from = req.getParameter("from");
        if (!StringUtils.isEmpty(from)) {
            billFrom = DatetimeUtils.toDate(from, "yyyy-MM-dd").atStartOfDay();
        }

        LocalDateTime billTo = null;
        String to = req.getParameter("to");
        if (!StringUtils.isEmpty(to)) {
            billTo = DatetimeUtils.toDate(to, "yyyy-MM-dd").atTime(LocalTime.MAX);
        }

        List<Bill> bills = billDao.getAll(billFrom, billTo, billStatus, null, null);

        req.setAttribute("bills", bills);
        req.setAttribute("status", status);
        req.setAttribute("from", from);
        req.setAttribute("to", to);
        req.getRequestDispatcher("/staff/bills.jsp").forward(req, resp);
    }
}
