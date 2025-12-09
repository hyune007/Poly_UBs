package com.poly.ubs.controller;

import com.poly.ubs.entity.Bill;
import com.poly.ubs.entity.Customer;
import com.poly.ubs.service.BillServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * Quản lý hóa đơn trong giao diện quản trị.
 */
@Controller
@RequestMapping("/admin/bills")
public class BillAdminController {

    @Autowired
    private BillServiceImpl billService;

    /**
     * Hiển thị danh sách khách hàng có hóa đơn.
     *
     * @param model Đối tượng Model.
     * @return Tên view quản lý hóa đơn.
     */
    @GetMapping("/manage")
    public String manageUserBill(Model model) {
        List<Customer> customers = billService.findCustomersWithBills();
        model.addAttribute("customers", customers);
        return "admin/TotalUserBill/manage-user-bill";
    }

    /**
     * Hiển thị danh sách hóa đơn của một khách hàng cụ thể.
     *
     * @param customerId ID của khách hàng.
     * @param model      Đối tượng Model.
     * @return Tên view danh sách hóa đơn.
     */
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

        return "admin/TotalUserBill/total-user-bill-list";
    }

    /**
     * Cập nhật trạng thái của hóa đơn.
     *
     * @param billId             ID hóa đơn.
     * @param status             Trạng thái mới.
     * @param customerId         ID khách hàng.
     * @param redirectAttributes Đối tượng truyền thông báo.
     * @return Đường dẫn chuyển hướng về danh sách hóa đơn.
     */
    @PostMapping("/updateStatus")
    public String updateBillStatus(@RequestParam("billId") String billId,
                                   @RequestParam("status") String status,
                                   @RequestParam("customerId") String customerId,
                                   RedirectAttributes redirectAttributes) {
        billService.updateStatus(billId, status);
        redirectAttributes.addFlashAttribute("successMessage", "Cập nhật trạng thái thành công!");

        return "redirect:/admin/bills/customer/" + customerId;
    }
}