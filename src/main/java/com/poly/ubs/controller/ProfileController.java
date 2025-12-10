package com.poly.ubs.controller;

import com.poly.ubs.entity.Bill;
import com.poly.ubs.entity.Customer;
import com.poly.ubs.service.BillServiceImpl;
import com.poly.ubs.service.CustomerServiceImpl;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Quản lý hồ sơ cá nhân của khách hàng.
 */
@Controller
public class ProfileController {

    @Autowired
    private CustomerServiceImpl customerService;
    @Autowired
    private BillServiceImpl billService;

    /**
     * Hiển thị trang thông tin cá nhân.
     *
     * @param session Phiên làm việc hiện tại.
     * @param model Đối tượng Model.
     * @return Tên view profile.
     */
    @GetMapping("/profile")
    public String profile(HttpSession session, Model model) {
        Object loggedInUser = session.getAttribute("loggedInUser");

        // Kiểm tra xem người dùng đã đăng nhập và là Customer
        if (loggedInUser == null || !(loggedInUser instanceof Customer)) {
            return "redirect:/login?error=needLogin";
        }

        Customer customer = (Customer) loggedInUser;
        model.addAttribute("customer", customer);
        return "/container/user/profile";
    }

    /**
     * Cập nhật thông tin cá nhân (tên, số điện thoại, email).
     *
     * @param name Tên mới.
     * @param phone Số điện thoại mới.
     * @param email Email mới.
     * @param session Phiên làm việc hiện tại.
     * @param redirectAttributes Đối tượng truyền thông báo.
     * @return Chuyển hướng về trang profile.
     */
    @PostMapping("/update-profile")
    public String updateProfile(
            @RequestParam("name") String name,
            @RequestParam("phone") String phone,
            @RequestParam("email") String email,
            HttpSession session,
            RedirectAttributes redirectAttributes
    ) {
        Object loggedInUser = session.getAttribute("loggedInUser");

        // Kiểm tra xem người dùng đã đăng nhập và là Customer
        if (loggedInUser == null || !(loggedInUser instanceof Customer)) {
            return "redirect:/login?error=needLogin";
        }

        Customer customer = (Customer) loggedInUser;
        customer.setName(name);
        customer.setPhone(phone);
        customer.setEmail(email);

        customerService.save(customer);

        session.setAttribute("loggedInUser", customer);

        redirectAttributes.addFlashAttribute("success", "Cập nhật thông tin thành công!");
        return "redirect:/profile";
    }

    /**
     * Thay đổi mật khẩu đăng nhập.
     *
     * @param currentPass Mật khẩu hiện tại.
     * @param newPass Mật khẩu mới.
     * @param confirmPass Xác nhận mật khẩu mới.
     * @param session Phiên làm việc hiện tại.
     * @param redirectAttributes Đối tượng truyền thông báo.
     * @return Chuyển hướng về trang profile.
     */
    @PostMapping("/change-password")
    public String updatePassword(
            @RequestParam("currentPass") String currentPass,
            @RequestParam("newPass") String newPass,
            @RequestParam("confirmPass") String confirmPass,
            HttpSession session,
            RedirectAttributes redirectAttributes
    ) {
        // Lấy user từ session
        Object loggedInUser = session.getAttribute("loggedInUser");

        // Kiểm tra xem người dùng đã đăng nhập và là Customer
        if (loggedInUser == null || !(loggedInUser instanceof Customer)) {
            return "redirect:/login?error=needLogin";
        }

        Customer customer = (Customer) loggedInUser;

        if (!customer.getPassword().equals(currentPass)) {
            redirectAttributes.addFlashAttribute("error", "Mật khẩu hiện tại không đúng!");
            return "redirect:/profile";
        }


        if (!newPass.equals(confirmPass)) {
            redirectAttributes.addFlashAttribute("error", "Mật khẩu mới và xác nhận không khớp!");
            return "redirect:/profile";
        }

        customer.setPassword(newPass);

        customerService.save(customer);

        session.setAttribute("loggedInUser", customer);

        redirectAttributes.addFlashAttribute("success", "Cập nhật mật khẩu thành công!");
        return "redirect:/profile";
    }

    /**
     * Hiển thị lịch sử đơn hàng của khách hàng.
     *
     * @param session Phiên làm việc hiện tại.
     * @param model Đối tượng Model.
     * @return Tên view lịch sử đơn hàng.
     */
    @GetMapping("/orders")
    public String userOrders(HttpSession session, Model model) {
        Customer loggedInUser = (Customer) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/login";
        }

        // Lấy tất cả đơn hàng của user
        List<Bill> bills = billService.findAll().stream()
                .filter(b -> b.getCustomer() != null && loggedInUser.getId().equals(b.getCustomer().getId()))
                .collect(Collectors.toList());

        model.addAttribute("bills", bills);
        return "container/user/infor-order";
    }

}