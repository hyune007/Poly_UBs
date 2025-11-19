package com.poly.ubs.controller;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.poly.ubs.entity.Customer;
import com.poly.ubs.entity.Employee;
import com.poly.ubs.service.CustomerServiceImpl;
import com.poly.ubs.service.EmployeeServiceImpl;
import com.poly.ubs.service.PasswordResetService;
import com.poly.ubs.utils.MailSender;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.Map;

/**
 * Bộ điều khiển xác thực người dùng
 * Quản lý tất cả các vấn đề về bảo mật: đăng nhập, đăng ký, quên mật khẩu, Firebase authentication
 */
@Controller
public class AuthController {

    @Autowired
    private CustomerServiceImpl customerService;
    @Autowired
    private EmployeeServiceImpl employeeService;
    @Autowired
    private PasswordResetService passwordResetService;

    /**
     * Hiển thị trang đăng nhập
     *
     * @return tên template đăng nhập
     */
    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }

    /**
     * Hiển thị trang access denied khi người dùng không có quyền truy cập
     *
     * @return tên template access denied
     */
    @GetMapping("/access-denied")
    public String accessDenied() {
        return "error/access-denied";
    }

    /**
     * Xử lý yêu cầu đăng nhập
     *
     * @param req yêu cầu HTTP
     * @return chuyển hướng đến trang chủ
     */
    @PostMapping("/login-post")
    public String loginPost(HttpServletRequest req) {
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        // Kiểm tra thông tin đăng nhập
        Employee employee = employeeService.findByEmailAndPassword(email, password);
        Customer customer = customerService.findByEmailAndPassword(email, password);

        if (customer != null || employee != null) {
            // Đăng nhập thành công, lưu thông tin người dùng vào session
            HttpSession session = req.getSession();
            Object loggedInUser = customer != null ? customer : employee;
            session.setAttribute("loggedInUser", loggedInUser);

            // Kiểm tra role và chuyển hướng tương ứng
            String role = employee != null ? employee.getRole() : customer.getRole();
            if ("ROLE_ADMIN".equals(role)) {
                return "redirect:/admin/dashboard";
            }

            return "redirect:/home";
        } else {
            // Đăng nhập thất bại, quay lại trang đăng nhập với thông báo lỗi
            return "redirect:/login?error=true";
        }
    }

    /**
     * Xử lý yêu cầu đăng xuất
     *
     * @param req yêu cầu HTTP
     * @return chuyển hướng đến trang chủ
     */
    @GetMapping("/logout")
    public String logout(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/home";
    }

    /**
     * Hiển thị trang đăng ký
     *
     * @return tên template đăng ký
     */
    @GetMapping("/register")
    public String register() {
        return "auth/register";
    }

    /**
     * Xử lý yêu cầu đăng ký
     *
     * @param req yêu cầu HTTP
     * @return chuyển hướng đến trang đăng nhập với thông báo thành công
     */
    @PostMapping("/register-post")
    public String registerPost(HttpServletRequest req, RedirectAttributes redirectAttributes) {
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String phone = req.getParameter("phone");
        String password = req.getParameter("password");
        String confirmPassword = req.getParameter("confirmPassword");

        // Kiểm tra xem email đã tồn tại chưa
        if (customerService.findByEmail(email) != null) {
            redirectAttributes.addFlashAttribute("error", "Email đã được sử dụng!");
            return "redirect:/register";
        }

        // Kiểm tra mật khẩu và xác nhận mật khẩu có khớp nhau không
        if (!password.equals(confirmPassword)) {
            redirectAttributes.addFlashAttribute("error", "Mật khẩu xác nhận không khớp!");
            return "redirect:/register";
        }

        // Tạo khách hàng mới
        Customer customer = new Customer();
        customer.setId(generateCustomerId());
        customer.setName(name);
        customer.setEmail(email);
        customer.setPhone(phone);
        customer.setPassword(password);
        customer.setRole("ROLE_CUSTOMER");

        // Lưu khách hàng vào cơ sở dữ liệu
        customerService.save(customer);
        MailSender.send(customer.getEmail(), "Xác nhận đăng ký tài khoản Poly_UBs", "Xin chào " + customer.getName() + ", tài khoản của bạn đã được tạo thành công!");
//        redirectAttributes.addFlashAttribute("success", "Đăng ký thành công! Vui lòng đăng nhập.");
        return "redirect:/login?message=true";
    }

    /**
     * Tạo ID cho khách hàng mới
     *
     * @return ID duy nhất cho khách hàng
     */
    private String generateCustomerId() {
        // Lấy tất cả các ID khách hàng hiện có
        String prefix = "KH";
        int maxId = 0;

        // Duyệt qua tất cả khách hàng để tìm số ID lớn nhất
        for (Customer customer : customerService.findAll()) {
            if (customer.getId().startsWith(prefix)) {
                try {
                    int idNum = Integer.parseInt(customer.getId().substring(prefix.length()));
                    if (idNum > maxId) {
                        maxId = idNum;
                    }
                } catch (NumberFormatException e) {
                    // Bỏ qua nếu không parse được số
                }
            }
        }

        // Tạo ID mới bằng cách tăng số ID lớn nhất lên 1
        return prefix + String.format("%03d", maxId + 1);
    }

    /**
     * Hiển thị trang quên mật khẩu
     *
     * @return tên template quên mật khẩu
     */
    @GetMapping("/forgot-password")
    public String forgotPassword() {
        return "auth/forgot-password";
    }

    /**
     * Xử lý yêu cầu gửi email reset mật khẩu
     *
     * @param email              email của khách hàng
     * @param redirectAttributes để truyền thông báo
     * @return chuyển hướng về trang forgot password với thông báo
     */
    @PostMapping("/forgot-password-post")
    public String forgotPasswordPost(@RequestParam("email") String email,
                                     RedirectAttributes redirectAttributes) {
        try {
            passwordResetService.createPasswordResetToken(email);
            redirectAttributes.addFlashAttribute(
                    "success",
                    "Link đặt lại mật khẩu đã được gửi đến email của bạn. Vui lòng kiểm tra hộp thư!");
            return "redirect:/forgot-password?success=true";
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/forgot-password?error=true";
        }
    }

    /**
     * Hiển thị trang đặt lại mật khẩu
     *
     * @param token token để xác thực
     * @param model để truyền dữ liệu sang view
     * @return tên template đặt lại mật khẩu
     */
    @GetMapping("/reset-password")
    public String resetPassword(@RequestParam(value = "token", required = false) String token,
                                Model model,
                                RedirectAttributes redirectAttributes) {
        if (token == null || token.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Link không hợp lệ");
            return "redirect:/forgot-password";
        }

        // Kiểm tra token có hợp lệ không
        if (!passwordResetService.validateToken(token)) {
            redirectAttributes.addFlashAttribute("error", "Link đã hết hạn hoặc không hợp lệ");
            return "redirect:/forgot-password";
        }

        model.addAttribute("token", token);
        return "auth/reset-password";
    }

    /**
     * Xử lý yêu cầu đặt lại mật khẩu
     *
     * @param token              token để xác thực
     * @param newPassword        mật khẩu mới
     * @param confirmPassword    xác nhận mật khẩu mới
     * @param redirectAttributes để truyền thông báo
     * @return chuyển hướng đến trang đăng nhập
     */
    @PostMapping("/reset-password-post")
    public String resetPasswordPost(@RequestParam("token") String token,
                                    @RequestParam("newPassword") String newPassword,
                                    @RequestParam("confirmPassword") String confirmPassword,
                                    RedirectAttributes redirectAttributes) {
        try {
            // Kiểm tra mật khẩu và xác nhận mật khẩu có khớp không
            if (!newPassword.equals(confirmPassword)) {
                redirectAttributes.addFlashAttribute("error", "Mật khẩu xác nhận không khớp!");
                return "redirect:/reset-password?token=" + token;
            }

            // Kiểm tra mật khẩu có đủ độ dài không
            if (newPassword.length() < 6) {
                redirectAttributes.addFlashAttribute("error", "Mật khẩu phải có ít nhất 6 ký tự!");
                return "redirect:/reset-password?token=" + token;
            }

            // Đặt lại mật khẩu
            passwordResetService.resetPassword(token, newPassword);
            redirectAttributes.addFlashAttribute("success", "Đặt lại mật khẩu thành công! Vui lòng đăng nhập.");
            return "redirect:/login?resetSuccess=true";
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/forgot-password";
        }
    }

    // ==================== FIREBASE AUTHENTICATION ====================

    /**
     * Endpoint API để xác thực Firebase ID Token và tạo session
     *
     * @param request chứa idToken từ Firebase client
     * @param session HTTP session
     * @return thông tin customer dưới dạng JSON
     */
    @PostMapping("/api/auth/firebase-login")
    public ResponseEntity<?> firebaseLogin(@RequestBody Map<String, String> request, HttpSession session) {
        try {
            String idToken = request.get("idToken");
            // Xác thực token với Firebase Admin SDK
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);

            // Lấy thông tin user từ token
            String email = decodedToken.getEmail();
            String name = decodedToken.getName();

            // Tìm hoặc tạo customer
            Customer customer = customerService.findByEmail(email);

            if (customer == null) {
                // Tạo customer mới từ Google account
                customer = new Customer();
                customer.setId(generateCustomerId());
                customer.setName(name != null ? name : "Google User");
                customer.setEmail(email);

                // Tạo password mặc định: [các ký tự trước @]pass
                String username = email.substring(0, email.indexOf('@'));
                customer.setPassword(username + "pass");

                customer.setPhone("");
                customer.setRole("ROLE_CUSTOMER"); // Set role mặc định cho customer

                customerService.save(customer);
            }

            // Lưu thông tin vào session
            session.setAttribute("loggedInUser", customer);

            // Trả về thông tin customer
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put(
                    "customer", Map.of(
                            "id", customer.getId(),
                            "name", customer.getName(),
                            "email", customer.getEmail()
                    ));

            return ResponseEntity.ok(response);

        } catch (FirebaseAuthException e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "Firebase authentication failed: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}