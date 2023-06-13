package com.hospital.booking.filters;

import com.hospital.booking.constants.SessionConstants;
import com.hospital.booking.daos.AccountDao;
import com.hospital.booking.models.Account;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@WebFilter(value = "/*")
public class AuthorizeFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        HttpSession session = request.getSession();

        boolean loggedIn = session != null && session.getAttribute(SessionConstants.ACCOUNT) != null;

        List<String> whitelistUris = Arrays.asList(
                request.getContextPath() + "",
                request.getContextPath() + "/",
                request.getContextPath() + "/home",
                request.getContextPath() + "/login",
                request.getContextPath() + "/register",
                request.getContextPath() + "/login-google",
                request.getContextPath() + "/forgot-password",
                request.getContextPath() + "/reset-password"
        );

        // Check status
        if (loggedIn) {
            AccountDao accountDao = new AccountDao();
            Account account = (Account) session.getAttribute(SessionConstants.ACCOUNT);
            account = accountDao.getAccountById(account.getId());

            if (!account.isActive()) {
                loggedIn = false;
                session.removeAttribute(SessionConstants.ACCOUNT);
            }
        }

        if (loggedIn
                || whitelistUris.contains(request.getRequestURI())
                || request.getRequestURI().startsWith(request.getContextPath() + "/resources")
                || request.getRequestURI().startsWith(request.getContextPath() + "/upload")) {
            filterChain.doFilter(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/login");
        }
    }
}
