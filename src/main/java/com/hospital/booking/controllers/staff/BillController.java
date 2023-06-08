package com.hospital.booking.controllers.staff;

import com.hospital.booking.daos.BillDao;
import com.hospital.booking.models.Bill;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/staff/bills")
public class BillController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BillDao billDao = new BillDao();
        List<Bill> bills = billDao.getAll();

        req.setAttribute("bills", bills);
        req.getRequestDispatcher("/staff/bills.jsp").forward(req, resp);
    }
}
