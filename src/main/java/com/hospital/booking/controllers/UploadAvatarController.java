package com.hospital.booking.controllers;

import com.hospital.booking.constants.SessionConstants;
import com.hospital.booking.daos.AccountDao;
import com.hospital.booking.models.Account;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.File;
import java.io.IOException;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/upload-avatar")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 5,
        maxRequestSize = 1024 * 1024 * 5 * 5)
public class UploadAvatarController extends HttpServlet {

    private final String UPLOAD_DIRECTORY = "upload";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Part part = req.getPart("avatar");
        try {
            if (part != null) {
                String uploadPath = getServletContext().getRealPath("") + File.separator + UPLOAD_DIRECTORY;
                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) {
                    uploadDir.mkdir();
                }

                String filename = UUID.randomUUID() + "_" + part.getSubmittedFileName();
                part.write(uploadPath + File.separator + filename);

                HttpSession session = req.getSession();
                Account account = (Account) session.getAttribute(SessionConstants.ACCOUNT);
                String filePath = req.getContextPath() + File.separator+ UPLOAD_DIRECTORY + File.separator + filename;
                account.setAvatar(filePath);

                AccountDao accountDao = new AccountDao();
                if (accountDao.update(account)) {
                    session.setAttribute(SessionConstants.ACCOUNT, account);
                    req.setAttribute("message", "Upload ảnh đại diện thành công");
                    req.getRequestDispatcher("profile.jsp").forward(req, resp);
                    return;
                }
            }

        } catch (Exception ex) {
            Logger.getLogger(UploadAvatarController.class.getName()).log(Level.SEVERE, ex.getMessage());
        }

        req.setAttribute("error", "Upload ảnh đại diện thất bại. Vui lòng thử lại");
        req.getRequestDispatcher("profile.jsp").forward(req, resp);

    }


}

