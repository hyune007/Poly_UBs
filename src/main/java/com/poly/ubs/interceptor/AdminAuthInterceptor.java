package com.poly.ubs.interceptor;

import com.poly.ubs.entity.Customer;
import com.poly.ubs.entity.Employee;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * Interceptor để kiểm tra quyền truy cập vào các trang admin
 */
@Component
public class AdminAuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // Lấy session hiện tại
        HttpSession session = request.getSession(false);

        // Kiểm tra xem người dùng đã đăng nhập chưa
        if (session == null || session.getAttribute("loggedInUser") == null) {
            // Chưa đăng nhập, chuyển hướng đến trang login
            response.sendRedirect(request.getContextPath() + "/auth/login?redirect=" + request.getRequestURI());
            return false;
        }

        // Lấy thông tin người dùng từ session
        Object loggedInUser = session.getAttribute("loggedInUser");
        String userRole = null;

        // Kiểm tra xem người dùng là Employee hay Customer
        if (loggedInUser instanceof Employee) {
            Employee employee = (Employee) loggedInUser;
            userRole = employee.getRole();
        } else if (loggedInUser instanceof Customer) {
            Customer customer = (Customer) loggedInUser;
            userRole = customer.getRole();
        }

        // Kiểm tra xem người dùng có role ROLE_ADMIN không
        if (userRole == null || !userRole.equals("ROLE_ADMIN")) {
            // Không có quyền truy cập, chuyển hướng đến trang lỗi hoặc trang chủ
            response.sendRedirect(request.getContextPath() + "/auth/access-denied");
            return false;
        }

        // Có quyền truy cập, cho phép tiếp tục
        return true;
    }
}

