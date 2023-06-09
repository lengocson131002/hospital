package com.hospital.booking.controllers.admin;

import com.hospital.booking.daos.AccountDao;
import com.hospital.booking.daos.DepartmentDao;
import com.hospital.booking.enums.Gender;
import com.hospital.booking.enums.Role;
import com.hospital.booking.models.Account;
import com.hospital.booking.models.Department;
import com.hospital.booking.utils.BCryptUtils;
import com.microsoft.sqlserver.jdbc.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Objects;

@WebServlet("/admin/add-doctor")
public class AddDoctorController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DepartmentDao departmentDao = new DepartmentDao();
        req.setAttribute("departments", departmentDao.getAll());
        req.getRequestDispatcher("/admin/add-doctor.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AccountDao accountDao = new AccountDao();
        Account account = new Account();
        account.setFirstName(req.getParameter("firstName"));
        account.setLastName(req.getParameter("lastName"));
        account.setEmail(req.getParameter("email"));
        account.setDOB(!StringUtils.isEmpty(req.getParameter("dob")) ? LocalDate.parse(req.getParameter("dob")) : null);
        account.setGender(Gender.valueOf(req.getParameter("gender")));
        account.setPhoneNumber(req.getParameter("phoneNumber"));
        account.setAddress(req.getParameter("address"));
        account.setDescription(req.getParameter("description"));
        account.setRole(Role.DOCTOR);

        String password = req.getParameter("password");
        account.setPassword(BCryptUtils.encryptPassword(password));

        String error = null;

        String confirmPassword = req.getParameter("confirmPassword");
        if (!Objects.equals(password, confirmPassword)) {
            error = "Nhập lại mật khẩu không đúng";
        }

        if (accountDao.getAccountByEmail(account.getEmail()) != null) {
            error = "Email đã tồn tại";
        }

        DepartmentDao departmentDao = new DepartmentDao();
        String departmentIdParam = req.getParameter("departmentId");
        if (StringUtils.isInteger(departmentIdParam)) {
            int departmentId = Integer.parseInt(departmentIdParam);
            Department department = departmentDao.getById(departmentId);
            if (department == null) {
                error = "Không tìm thấy phòng ban";
            } else {
                account.setDepartment(department);
            }
        }

        if (StringUtils.isEmpty(error) && !accountDao.insertAccount(account)) {
            error = "Có lỗi xảy ra. Vui lòng thử lại";
        }

        if (!StringUtils.isEmpty(error)) {
            req.setAttribute("departments", departmentDao.getAll());

            req.setAttribute("error", error);
            req.setAttribute("password", password);
            req.setAttribute("confirmPassword", confirmPassword);
            req.setAttribute("acc", account);
            req.getRequestDispatcher("/admin/add-doctor.jsp").forward(req, resp);
            return;
        }

        req.setAttribute("message", "Tạo mới bác sĩ thành công");
        resp.sendRedirect(req.getContextPath() + "/admin/accounts?role=DOCTOR");
    }
}
