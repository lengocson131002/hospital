package com.hospital.booking.controllers;

import com.hospital.booking.daos.AccountDao;
import com.hospital.booking.daos.TokenDao;
import com.hospital.booking.enums.TokenType;
import com.hospital.booking.models.Account;
import com.hospital.booking.models.Token;
import com.hospital.booking.utils.ApplicationSettings;
import com.hospital.booking.utils.EmailUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

@WebServlet("/forgot-password")
public class ForgotPasswordController extends HttpServlet {
    private final int TOKEN_EXPIRE_IN_MINUTES = 2;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("forgot-password.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        AccountDao accountDao = new AccountDao();
        Account account = accountDao.getAccountByEmail(email);
        if (account == null) {
            req.setAttribute("email", email);
            req.setAttribute("error", "Email không tồn tại");
            req.getRequestDispatcher("forgot-password.jsp").forward(req, resp);
            return;
        }

        String tokenValue = UUID.randomUUID().toString();
        // Create token
        Token token = new Token(
                tokenValue,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(TOKEN_EXPIRE_IN_MINUTES),
                TokenType.FORGOT_PASSWORD, account.getId());

        String resetPasswordUrl = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + req.getContextPath() + "/reset-password?token=" + token.getToken();
        boolean sendResult = EmailUtils.sendEmail(
                ApplicationSettings.getGmailFrom(),
                account.getEmail(),
                "Quên mật khẩu",
                String.format("Click vào đường dẫn sau để đặt lại mật khẩu: %s. Đường dẫn sẽ hết hạn sau %d phút", resetPasswordUrl, TOKEN_EXPIRE_IN_MINUTES));

        TokenDao tokenDao = new TokenDao();
        if (sendResult && tokenDao.setUserToken(token)) {
            req.setAttribute("message", "Send request successfully. Check email to reset password");
            req.getRequestDispatcher("forgot-password.jsp").forward(req, resp);
            return;
        }

        req.setAttribute("email", email);
        req.setAttribute("error", "Internal server error");
        req.getRequestDispatcher("forgot-password.jsp").forward(req, resp);
    }
}
