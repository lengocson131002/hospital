package com.hospital.booking.controllers.patient;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hospital.booking.adapters.LocalDateTimeTypeAdapter;
import com.hospital.booking.adapters.LocalDateTypeAdapter;
import com.hospital.booking.daos.AccountDao;
import com.hospital.booking.database.AccountQuery;
import com.hospital.booking.models.Account;
import com.microsoft.sqlserver.jdbc.StringUtils;

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
import java.util.List;

@WebServlet("/patient/filter-doctors")
public class FilterDoctorsController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        List<Account> doctors = new ArrayList<>();
        try {
            if (!StringUtils.isEmpty(req.getParameter("departmentId"))) {
                AccountQuery query = new AccountQuery();
                query.setActive(true);
                int departmentId = Integer.parseInt(req.getParameter("departmentId"));

                query.setDepartmentId(departmentId);
                query.setActive(true);

                AccountDao accountDao = new AccountDao();
                doctors = accountDao.getAll(query);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter())
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter())
                .create();
        String jsonResult = gson.toJson(doctors);
        PrintWriter out = resp.getWriter();
        out.println(jsonResult);
        out.flush();
    }
}
