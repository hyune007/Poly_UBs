package com.poly.ubs.controller;

import com.poly.ubs.entity.Bill;
import com.poly.ubs.entity.Customer;
import com.poly.ubs.service.BillServiceImpl;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;


@Controller
@RequestMapping("/shipper")
public class ShipperController {

    @Autowired
    private BillServiceImpl billService;

    @GetMapping("/manage")
    public String manageUserBill(Model model) {
        List<Customer> confirmedCustomers = billService.findCustomersWithConfirmedBills();
        model.addAttribute("customers", confirmedCustomers);
        return "admin/employee/shipper-customer-bill";
    }

    @GetMapping("/customer/{customerId}")
    public String totalUserBillList(@PathVariable("customerId") String customerId, Model model) {
        List<Bill> bills = billService.findByCustomerId(customerId);
        // Lấy customer từ hóa đơn đầu tiên (nếu có)
        Customer customer = null;
        if (!bills.isEmpty()) {
            customer = bills.get(0).getCustomer();
        }

        model.addAttribute("bills", bills);
        model.addAttribute("customerId", customerId);
        model.addAttribute("customer", customer);

        return "admin/employee/shipper";
    }

    @PostMapping("/updateStatus")
    public String updateBillStatus(@RequestParam("billId") String billId,
                                   @RequestParam("status") String status,
                                   @RequestParam("customerId") String customerId,
                                   RedirectAttributes redirectAttributes) {
        billService.updateStatus(billId, status);
        if ("Đã giao thành công".equals(status)) {
            redirectAttributes.addFlashAttribute("successMessage", "Đơn hàng giao thành công!");
        } else {
            redirectAttributes.addFlashAttribute("successMessage", "Cập nhật trạng thái thành công!");
        }



        return "redirect:/shipper/customer/" + customerId;

    }
}
