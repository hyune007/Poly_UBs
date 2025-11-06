package com.poly.ubs.utils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;

public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        HttpSession session = request.getSession(false);
        String uri = request.getRequestURI();

        // Cho phép truy cập các trang public
        if (uri.startsWith("/login") || uri.startsWith("/register") || uri.startsWith("/home") || uri.startsWith("/forgot-password")) {
            return true;
        }

        if (session == null || session.getAttribute("role") == null) {
            response.sendRedirect("/login?redirect=" + uri);
            return false;
        }

        String role = (String) session.getAttribute("role");

        // Nếu là nhân viên thì cho qua mọi request
        if ("employee".equals(role)) {
            return true;
        }

        // Nếu là khách hàng mà vào /admin thì chặn
        if ("customer".equals(role) && uri.startsWith("/admin")) {
            response.sendRedirect("/home?accessDenied=true");
            return false;
        }

        return true;
    }
}
