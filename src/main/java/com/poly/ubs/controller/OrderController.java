package com.poly.ubs.controller;

import com.poly.ubs.dto.OrderInfoDTO;
import com.poly.ubs.entity.*;
import com.poly.ubs.service.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * Bộ điều khiển đơn hàng
 */
@Controller
public class OrderController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private CustomerServiceImpl customerService;

    @Autowired
    private AddressServiceImpl addressService;

    @Autowired
    private BillServiceImpl billService;

    @Autowired
    private EmployeeServiceImpl employeeService;

    /**
     * Hiển thị trang giỏ hàng
     *
     * @param request yêu cầu HTTP
     * @param model   đối tượng model để truyền dữ liệu đến view
     * @param session session để lấy thông tin khách hàng
     * @return đường dẫn đến template giỏ hàng
     */
    @GetMapping("/order/shopping-cart")
    public String shoppingCart(HttpServletRequest request, Model model, HttpSession session) {
        model.addAttribute("currentURI", request.getRequestURI());

        // Lấy thông tin khách hàng từ session
        Object loggedInUser = session.getAttribute("loggedInUser");
        if (loggedInUser == null || !(loggedInUser instanceof Customer)) {
            // Nếu chưa đăng nhập, chuyển về trang đăng nhập
            return "redirect:/auth/login";
        }

        Customer customer = (Customer) loggedInUser;

        // Lấy danh sách sản phẩm trong giỏ hàng
        List<ShoppingCart> cartItems = shoppingCartService.findByCustomerId(customer.getId());
        int total = shoppingCartService.calculateTotal(customer.getId());

        model.addAttribute("cartItems", cartItems);
        model.addAttribute("total", total);

        return "container/orders/shopping-cart";
    }

    /**
     * Hiển thị trang thông tin đơn hàng
     *
     * @param request yêu cầu HTTP
     * @param model   đối tượng model để truyền dữ liệu đến view
     * @param session session để lấy thông tin khách hàng
     * @return đường dẫn đến template thông tin đơn hàng
     */
    @GetMapping("/order/infor-order")
    public String inforOrder(HttpServletRequest request, Model model, HttpSession session) {
        model.addAttribute("currentURI", request.getRequestURI());

        // Lấy thông tin khách hàng từ session
        Object loggedInUser = session.getAttribute("loggedInUser");
        if (loggedInUser == null || !(loggedInUser instanceof Customer)) {
            return "redirect:/auth/login";
        }

        Customer customer = (Customer) loggedInUser;

        // Kiểm tra giỏ hàng có sản phẩm không
        List<ShoppingCart> cartItems = shoppingCartService.findByCustomerId(customer.getId());
        if (cartItems == null || cartItems.isEmpty()) {
            return "redirect:/order/shopping-cart";
        }

        // Tính tổng tiền
        int total = shoppingCartService.calculateTotal(customer.getId());

        model.addAttribute("customer", customer);
        model.addAttribute("total", total);
        model.addAttribute("orderInfo", new OrderInfoDTO());

        return "container/orders/infor-order";
    }

    /**
     * Xử lý submit form thông tin đơn hàng và chuyển sang trang thanh toán
     *
     * @param orderInfo thông tin đơn hàng từ form
     * @param session   session để lưu thông tin
     * @return redirect sang trang thanh toán
     */
    @PostMapping("/order/submit-info")
    public String submitOrderInfo(@ModelAttribute OrderInfoDTO orderInfo, HttpSession session, RedirectAttributes redirectAttributes) {
        // Lấy thông tin khách hàng từ session
        Object loggedInUser = session.getAttribute("loggedInUser");
        if (loggedInUser == null || !(loggedInUser instanceof Customer)) {
            return "redirect:/auth/login";
        }

        Customer customer = (Customer) loggedInUser;

        // Tính tổng tiền
        int subtotal = shoppingCartService.calculateTotal(customer.getId());
        int shippingFee = "standard".equals(orderInfo.getShippingMethod()) ? 40000 : 0;
        orderInfo.setTotalAmount(subtotal + shippingFee);
        orderInfo.setShippingFee(shippingFee);

        // Lưu thông tin vào session để sử dụng ở trang payment
        session.setAttribute("orderInfo", orderInfo);

        return "redirect:/order/payment";
    }

    /**
     * Hiển thị trang thanh toán
     *
     * @param request yêu cầu HTTP
     * @param model   đối tượng model để truyền dữ liệu đến view
     * @param session session để lấy thông tin đơn hàng
     * @return đường dẫn đến template thanh toán
     */
    @GetMapping("/order/payment")
    public String payment(HttpServletRequest request, Model model, HttpSession session) {
        model.addAttribute("currentURI", request.getRequestURI());

        // Lấy thông tin khách hàng và đơn hàng từ session
        Object loggedInUser = session.getAttribute("loggedInUser");
        OrderInfoDTO orderInfo = (OrderInfoDTO) session.getAttribute("orderInfo");

        if (loggedInUser == null || !(loggedInUser instanceof Customer)) {
            return "redirect:/auth/login";
        }

        Customer customer = (Customer) loggedInUser;

        if (orderInfo == null) {
            return "redirect:/order/infor-order";
        }

        // Lấy danh sách sản phẩm trong giỏ hàng
        List<ShoppingCart> cartItems = shoppingCartService.findByCustomerId(customer.getId());

        model.addAttribute("customer", customer);
        model.addAttribute("orderInfo", orderInfo);
        model.addAttribute("cartItems", cartItems);

        return "container/orders/payment";
    }

    /**
     * Xử lý xác nhận thanh toán và tạo hóa đơn
     *
     * @param session            session để lấy thông tin
     * @param redirectAttributes để truyền thông báo
     * @return redirect sang trang hoàn thành
     */
    @PostMapping("/order/confirm-payment")
    public String confirmPayment(HttpSession session, RedirectAttributes redirectAttributes) {
        try {
            // Lấy thông tin từ session
            Object loggedInUser = session.getAttribute("loggedInUser");
            OrderInfoDTO orderInfo = (OrderInfoDTO) session.getAttribute("orderInfo");

            if (loggedInUser == null || !(loggedInUser instanceof Customer)) {
                return "redirect:/auth/login";
            }

            Customer customer = (Customer) loggedInUser;

            if (orderInfo == null) {
                return "redirect:/order/infor-order";
            }

            // Tạo địa chỉ giao hàng mới và lưu vào database
            Address address = addressService.createAddress(
                    customer,
                    orderInfo.getCity(),
                    orderInfo.getWard(),
                    orderInfo.getDetailAddress()
            );

            // Tạo hóa đơn từ giỏ hàng (employee có thể null nếu đặt online)
            Bill bill = billService.createBillFromCart(customer, null, address);

            // Lưu ID hóa đơn vào session để hiển thị trang complete
            session.setAttribute("completedBillId", bill.getId());

            // Xóa orderInfo khỏi session
            session.removeAttribute("orderInfo");

            redirectAttributes.addFlashAttribute("success", "Đặt hàng thành công!");
            return "redirect:/order/complete";

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Có lỗi xảy ra: " + e.getMessage());
            return "redirect:/order/payment";
        }
    }

    /**
     * Hiển thị trang hoàn thành đơn hàng
     *
     * @param request yêu cầu HTTP
     * @param model   đối tượng model để truyền dữ liệu đến view
     * @param session session để lấy thông tin hóa đơn
     * @return đường dẫn đến template hoàn thành
     */
    @GetMapping("/order/complete")
    public String complete(HttpServletRequest request, Model model, HttpSession session) {
        model.addAttribute("currentURI", request.getRequestURI());

        // Lấy ID hóa đơn từ session
        String billId = (String) session.getAttribute("completedBillId");
        if (billId != null) {
            Bill bill = billService.findById(billId);
            model.addAttribute("bill", bill);

            // Xóa billId khỏi session sau khi đã sử dụng
            session.removeAttribute("completedBillId");
        }

        return "container/orders/complete";
    }
}
