package com.hospital.booking.controllers.doctor;

import com.google.gson.Gson;
import com.hospital.booking.constants.DateTimeConstants;
import com.hospital.booking.constants.SessionConstants;
import com.hospital.booking.daos.ShiftDao;
import com.hospital.booking.models.Account;
import com.hospital.booking.models.Shift;
import com.hospital.booking.models.Slot;
import com.hospital.booking.models.StatusResult;
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
import java.util.*;
import java.util.stream.Collectors;

@WebServlet("/doctor/schedule")
public class ScheduleController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HashMap<LocalDate, List<Slot>> days = new HashMap<>();

        Account account = (Account) req.getSession().getAttribute(SessionConstants.ACCOUNT);

        List<LocalDate> weeklyDays = DatetimeUtils.getWeeklyDays(false);

        ShiftDao shiftDao = new ShiftDao();
        for (LocalDate date : weeklyDays) {
            List<Shift> createdShifts = shiftDao.getAll(account.getId(), date);
            List<Slot> dailySlots = SlotUtils.getDailySlots();
            for (Slot slot : dailySlots) {
                Shift createdShift = createdShifts.stream()
                        .filter(s -> Objects.equals(date, s.getDate()) && slot.getNumber() == s.getSlot())
                        .findFirst()
                        .orElse(null);

                if (createdShift != null && createdShift.isBooked()) {
                    slot.setStatus(Slot.SlotStatus.BOOKED);
                } else if (createdShift != null) {
                    slot.setStatus(Slot.SlotStatus.SHIFTED);
                }
            }
            days.put(date, dailySlots);
        }

        SortedSet<LocalDate> keys = new TreeSet<>(days.keySet());
        HashMap<LocalDate, List<Slot>> sortedDays = new HashMap<>();
        for (LocalDate key: keys) {
            sortedDays.put(key, days.get(key));
        }

        req.setAttribute("from", DatetimeUtils.toString(weeklyDays.get(0), DateTimeConstants.DATE_FORMAT));
        req.setAttribute("to", DatetimeUtils.toString(weeklyDays.get(weeklyDays.size() - 1), DateTimeConstants.DATE_FORMAT));
        req.setAttribute("days", sortedDays);
        req.getRequestDispatcher("/doctor/schedule.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        try {
            LocalDate date = DatetimeUtils.toDate(req.getParameter("date"), "yyyy-MM-dd");
            int slot = Integer.parseInt(req.getParameter("slot"));
            boolean selected = Boolean.parseBoolean(req.getParameter("selected"));
            Account account = (Account) req.getSession().getAttribute(SessionConstants.ACCOUNT);

            ShiftDao shiftDao = new ShiftDao();
            Shift existedShift = shiftDao.get(account.getId(), date, slot);
            if (selected && existedShift == null) {
                Shift shift = new Shift(account, date, slot);
                shiftDao.insert(shift);
            } else if (!selected && existedShift != null && !existedShift.isBooked()) {
                shiftDao.delete(existedShift.getId());
            } else {
                StatusResult result = new StatusResult(false);
                String jsonResult = new Gson().toJson(result);
                out.println(jsonResult);
                return;
            }

            StatusResult result = new StatusResult(true);
            String jsonResult = new Gson().toJson(result);
            out.println(jsonResult);
        } catch (Exception ex) {
            StatusResult result = new StatusResult(false, "Internal server error. Try again");
            String jsonResult = new Gson().toJson(result);
            out.println(jsonResult);
        } finally {
            out.flush();
        }
    }
}
