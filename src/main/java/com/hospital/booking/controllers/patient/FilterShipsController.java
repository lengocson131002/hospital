package com.hospital.booking.controllers.patient;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hospital.booking.adapters.LocalDateTimeTypeAdapter;
import com.hospital.booking.adapters.LocalDateTypeAdapter;
import com.hospital.booking.daos.ShiftDao;
import com.hospital.booking.database.ShiftQuery;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/patient/filter-shifts")
public class FilterShipsController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        List<Shift> shifts = new ArrayList<>();
        try {
            ShiftQuery query = new ShiftQuery();
            LocalDate date = DatetimeUtils.toDate(req.getParameter("date"), "yyyy-MM-dd");
            int doctorId = Integer.parseInt(req.getParameter("doctorId"));

            query.setFrom(date);
            query.setTo(date);
            query.setDoctorId(doctorId);

            ShiftDao shiftDao = new ShiftDao();
            shifts = shiftDao
                    .getAll(query)
                    .stream()
                    .filter(shift -> shift.getDoctor() != null && shift.getDoctor().isActive())
                    .sorted(Comparator.comparingInt(Shift::getSlot))
                    .collect(Collectors.toList());

            for (Shift shift : shifts) {
                shift.setSlotInfo(SlotUtils.getSlot(shift.getSlot()));
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter())
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter())
                .create();

        String jsonResult = gson.toJson(shifts);
        PrintWriter out = resp.getWriter();
        out.println(jsonResult);
        out.flush();
    }
}
