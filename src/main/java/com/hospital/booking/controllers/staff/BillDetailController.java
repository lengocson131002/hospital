package com.hospital.booking.controllers.staff;

import com.hospital.booking.daos.BillDao;
import com.hospital.booking.models.Bill;
import com.microsoft.sqlserver.jdbc.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/staff/bill")
public class BillDetailController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String billId = req.getParameter("id");
        if (StringUtils.isEmpty(billId)) {
            resp.sendRedirect(req.getContextPath() + "/staff/bills");
            return;
        }

        BillDao billDao = new BillDao();

        int id = Integer.parseInt(billId);
        Bill bill = billDao.getById(id);
        if (bill == null) {
            HttpSession session = req.getSession();
            session.setAttribute("error", " Không tìm thấy hoá đơn");
            resp.sendRedirect(req.getContextPath() + "/staff/bills");
            return;
        }

        req.setAttribute("bill", bill);
        req.getRequestDispatcher("/staff/bill.jsp").forward(req, resp);
    }
}
