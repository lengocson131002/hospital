package com.hospital.booking.controllers.admin;

import com.hospital.booking.daos.AccountDao;
import com.hospital.booking.daos.DepartmentDao;
import com.hospital.booking.enums.Gender;
import com.hospital.booking.models.Account;
import com.hospital.booking.models.Department;
import com.microsoft.sqlserver.jdbc.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;

@WebServlet("/admin/doctor")
public class DoctorDetailController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String accId = req.getParameter("id");
        if (StringUtils.isInteger(accId)) {
            int id = Integer.parseInt(accId);
            AccountDao accountDao = new AccountDao();
            Account account = accountDao.getAccountById(id);
            if (account != null) {
                DepartmentDao departmentDao = new DepartmentDao();
                req.setAttribute("departments", departmentDao.getAll());
                req.setAttribute("acc", account);
                req.getRequestDispatcher("/admin/doctor.jsp").forward(req, resp);
                return;
            }
        }

        req.setAttribute("error", "Không tìm thấy tài khoản");
        req.getRequestDispatcher("/admin/doctor.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String accId = req.getParameter("id");
        Account account;

        AccountDao accountDao = new AccountDao();
        if (!StringUtils.isInteger(accId)
                || (account = accountDao.getAccountById(Integer.parseInt(accId))) == null) {
            req.setAttribute("error", "Không tìm thấy tài khoản");
            req.getRequestDispatcher("/admin/doctor.jsp").forward(req, resp);
            return;
        }


        DepartmentDao departmentDao = new DepartmentDao();
        boolean hasError = false;

        account.setFirstName(req.getParameter("firstName"));
        account.setLastName(req.getParameter("lastName"));
        account.setEmail(req.getParameter("email"));
        account.setDOB(!StringUtils.isEmpty(req.getParameter("dob")) ? LocalDate.parse(req.getParameter("dob")) : null);
        account.setGender(Gender.valueOf(req.getParameter("gender")));
        account.setPhoneNumber(req.getParameter("phoneNumber"));
        account.setAddress(req.getParameter("address"));
        account.setDescription(req.getParameter("description"));
        account.setUpdatedAt(LocalDateTime.now());
        account.setActive(Boolean.parseBoolean(req.getParameter("active")));

        if (!StringUtils.isEmpty(req.getParameter("departmentId"))) {
            int departmentId = Integer.parseInt(req.getParameter("departmentId"));
            Department department = departmentDao.getById(departmentId);
            if (department == null) {
                hasError = true;
                req.setAttribute("error", "Không tìm thấy phòng ban");
            } else {
                account.setDepartment(department);
            }
        }

        // Check email
        Account existedAcc = accountDao.getAccountByEmail(account.getEmail());
        if (existedAcc != null && existedAcc.getId() != account.getId()) {
            hasError = true;
            req.setAttribute("error", "Email đã tồn tại");
        }

        if (!hasError) {
            if (accountDao.update(account)) {
                req.setAttribute("message", "Cập nhật tài khoản bác sĩ thành công");
            } else {
                req.setAttribute("error", "Đã có lỗi xảy ra. Vui lòng thử lại");
            }
        }

        req.setAttribute("departments", departmentDao.getAll());
        req.setAttribute("acc", account);
        req.getRequestDispatcher("/admin/doctor.jsp").forward(req, resp);
    }
}
