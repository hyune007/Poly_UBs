package com.poly.ubs.controller;

import com.poly.ubs.entity.Customer;
import com.poly.ubs.repository.CustomerRepository;
import com.poly.ubs.service.CustomerServiceImpl;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ProfileController {

    @Autowired
    private CustomerServiceImpl customerService;

    @GetMapping("/profile")
    public String profile(HttpSession session, Model model) {
        Customer loggedInUser = (Customer) session.getAttribute("loggedInUser");

        if (loggedInUser == null) {
            return "redirect:/login";
        }

        model.addAttribute("customer", loggedInUser);
        return "/container/user/profile";
    }

    @PostMapping("/update-profile")
    public String updateProfile(
            @RequestParam("name") String name,
            @RequestParam("phone") String phone,
            @RequestParam("email") String email,
            HttpSession session,
            RedirectAttributes redirectAttributes
    ) {
        Customer loggedInUser = (Customer) session.getAttribute("loggedInUser");

        if (loggedInUser == null) {
            return "redirect:/login";
        }

        loggedInUser.setName(name);
        loggedInUser.setPhone(phone);
        loggedInUser.setEmail(email);

        customerService.save(loggedInUser);

        session.setAttribute("loggedInUser", loggedInUser);

        redirectAttributes.addFlashAttribute("success", "Cập nhật thông tin thành công!");
        return "redirect:/profile";
    }

    @PostMapping("/change-password")
    public String updatePassword(
            @RequestParam("currentPass") String currentPass,
            @RequestParam("newPass") String newPass,
            @RequestParam("confirmPass") String confirmPass,
            HttpSession session,
            RedirectAttributes redirectAttributes
    ) {
        // Lấy user từ session
        Customer loggedInUser = (Customer) session.getAttribute("loggedInUser");

        if (loggedInUser == null) {
            return "redirect:/login";
        }


        // Sai mật khẩu hiện tại
        if (!loggedInUser.getPassword().equals(currentPass)) {
            redirectAttributes.addFlashAttribute("errorChangePass", "Mật khẩu hiện tại không đúng!");
            redirectAttributes.addFlashAttribute("openChangePassModal", true);
            return "redirect:/profile";
        }

        // Mật khẩu xác nhận không khớp
        if (!newPass.equals(confirmPass)) {
            redirectAttributes.addFlashAttribute("errorChangePass", "Mật khẩu mới và xác nhận không khớp!");
            redirectAttributes.addFlashAttribute("openChangePassModal", true);
            return "redirect:/profile";
        }

        loggedInUser.setPassword(newPass);

        customerService.save(loggedInUser);

        session.setAttribute("loggedInUser", loggedInUser);

        redirectAttributes.addFlashAttribute("success", "Cập nhật mật khẩu thành công!");
        return "redirect:/profile";
    }

}