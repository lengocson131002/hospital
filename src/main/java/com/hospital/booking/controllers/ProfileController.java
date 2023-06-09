package com.hospital.booking.controllers;

import com.hospital.booking.constants.SessionConstants;
import com.hospital.booking.daos.AccountDao;
import com.hospital.booking.daos.DepartmentDao;
import com.hospital.booking.enums.Gender;
import com.hospital.booking.enums.Role;
import com.hospital.booking.models.Account;
import com.hospital.booking.models.Department;
import com.microsoft.sqlserver.jdbc.StringUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/profile")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
        maxFileSize = 1024 * 1024 * 10,      // 10 MB
        maxRequestSize = 1024 * 1024 * 100   // 100 MB
)
public class ProfileController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DepartmentDao departmentDao = new DepartmentDao();
        req.setAttribute("departments", departmentDao.getAll());
        req.getRequestDispatcher("profile.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Account currentAccount = (Account) session.getAttribute(SessionConstants.ACCOUNT);
        if (currentAccount == null) {
            resp.sendRedirect("home");
            return;
        }

        AccountDao accountDao = new AccountDao();
        currentAccount.setFirstName(req.getParameter("firstName"));
        currentAccount.setLastName(req.getParameter("lastName"));
        currentAccount.setDOB(!StringUtils.isEmpty(req.getParameter("dob")) ? LocalDate.parse(req.getParameter("dob")) : null);
        currentAccount.setGender(Gender.valueOf(req.getParameter("gender")));
        currentAccount.setPhoneNumber(req.getParameter("phoneNumber"));
        currentAccount.setAddress(req.getParameter("address"));
        currentAccount.setUpdatedAt(LocalDateTime.now());
        currentAccount.setDescription(req.getParameter("description"));

        req.setAttribute("account", currentAccount);
        DepartmentDao departmentDao = new DepartmentDao();
        req.setAttribute("departments", departmentDao.getAll());
        if (!StringUtils.isEmpty(req.getParameter("departmentId"))) {
            int departmentId = Integer.parseInt(req.getParameter("departmentId"));
            Department department = departmentDao.getById(departmentId);
            if (department == null) {
                req.setAttribute("error", "Department not found");
                req.getRequestDispatcher("profile.jsp").forward(req, resp);
                return;
            }
            currentAccount.setDepartment(department);
        }

        if (accountDao.update(currentAccount)) {
            req.setAttribute("message", "Update profile successfully");
            //Update session
            req.getSession().setAttribute(SessionConstants.ACCOUNT, currentAccount);
            req.getRequestDispatcher("profile.jsp").forward(req, resp);
            return;
        }

        req.setAttribute("error", "Internal error existed");
        req.getRequestDispatcher("profile.jsp").forward(req, resp);
    }
}
