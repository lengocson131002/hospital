package com.hospital.booking.controllers.patient;

import com.google.gson.Gson;
import com.hospital.booking.constants.DateTimeConstants;
import com.hospital.booking.daos.ShiftDao;
import com.hospital.booking.models.Shift;
import com.hospital.booking.utils.DatetimeUtils;
import com.hospital.booking.utils.SlotUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

@WebServlet("/patient/shifts-by-date")
public class GetShiftsByDateController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        List<Shift> shifts = new ArrayList<>();
        try {
            LocalDate date = DatetimeUtils.toDate(req.getParameter("date"), "yyyy-MM-dd");
            if (date != null) {
                ShiftDao shiftDao = new ShiftDao();
                shifts = shiftDao.getAll(date, true);
                for(Shift shift: shifts) {
                    shift.setSlotInfo(SlotUtils.getSlot(shift.getSlot()));
                }
                shifts.sort(Comparator.comparingInt(Shift::getSlot));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        String jsonResult = new Gson().toJson(shifts);
        PrintWriter out = resp.getWriter();
        out.println(jsonResult);
        out.flush();
    }
}
