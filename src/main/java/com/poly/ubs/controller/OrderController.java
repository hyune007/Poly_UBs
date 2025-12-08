package com.poly.ubs.controller;

import com.poly.ubs.dto.OrderInfoDTO;
import com.poly.ubs.entity.Address;
import com.poly.ubs.entity.Bill;
import com.poly.ubs.entity.Customer;
import com.poly.ubs.entity.ShoppingCart;
import com.poly.ubs.service.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * Quản lý quy trình đặt hàng: giỏ hàng, thông tin đơn hàng, thanh toán và hoàn tất.
 */
@Controller
public class OrderController {

    @Value("${sepay.bank.account}")
    private String sepayAccount;

    @Value("${sepay.bank.code}")
    private String sepayBankCode;

    @Value("${sepay.template}")
    private String sepayTemplate;

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
     * Hiển thị trang giỏ hàng và tính tổng tiền.
     *
     * @param request Yêu cầu HTTP.
     * @param model Đối tượng Model.
     * @param session Phiên làm việc hiện tại.
     * @return Tên view giỏ hàng hoặc chuyển hướng đăng nhập.
     */
    @GetMapping("/order/shopping-cart")
    public String shoppingCart(HttpServletRequest request, Model model, HttpSession session) {
        model.addAttribute("currentURI", request.getRequestURI());

        // Lấy thông tin khách hàng từ session
        Object loggedInUser = session.getAttribute("loggedInUser");
        if (loggedInUser == null || !(loggedInUser instanceof Customer)) {
            // Nếu chưa đăng nhập, chuyển về trang đăng nhập
            return "redirect:/login";
        }

        Customer customer = (Customer) loggedInUser;

        // Lấy danh sách sản phẩm trong giỏ hàng và tính tổng tiền
        List<ShoppingCart> cartItems = shoppingCartService.findByCustomerId(customer.getId());
        int total = shoppingCartService.calculateTotal(customer.getId());

        model.addAttribute("cartItems", cartItems);
        model.addAttribute("total", total);

        return "container/orders/shopping-cart";
    }

    /**
     * Hiển thị trang nhập thông tin giao hàng.
     *
     * @param request Yêu cầu HTTP.
     * @param model Đối tượng Model.
     * @param session Phiên làm việc hiện tại.
     * @return Tên view thông tin đơn hàng.
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

        // Tính tổng tiền cần thanh toán
        int total = shoppingCartService.calculateTotal(customer.getId());

        model.addAttribute("customer", customer);
        model.addAttribute("total", total);
        model.addAttribute("orderInfo", new OrderInfoDTO());

        return "container/orders/infor-order";
    }

    /**
     * Xử lý thông tin giao hàng và lưu tạm vào session.
     *
     * @param orderInfo Thông tin đơn hàng từ form.
     * @param session Phiên làm việc hiện tại.
     * @param redirectAttributes Đối tượng truyền thông báo.
     * @param request Yêu cầu HTTP.
     * @return Chuyển hướng đến trang thanh toán.
     */
    @PostMapping("/order/submit-info")
    public String submitOrderInfo(@ModelAttribute OrderInfoDTO orderInfo,
                                  HttpSession session,
                                  RedirectAttributes redirectAttributes,
                                  HttpServletRequest request) {
        // Lấy thông tin khách hàng từ session
        Object loggedInUser = session.getAttribute("loggedInUser");
        if (loggedInUser == null || !(loggedInUser instanceof Customer)) {
            return "redirect:/auth/login";
        }

        Customer customer = (Customer) loggedInUser;

        // Xử lý địa chỉ được chọn từ danh sách có sẵn (nếu có)
        String addressIdParam = request.getParameter("addressId");
        if (addressIdParam != null && !addressIdParam.isBlank()) {
            Address selected = addressService.findById(addressIdParam);
            if (selected != null) {
                orderInfo.setCity(selected.getCity());
                orderInfo.setWard(selected.getWard());
                orderInfo.setDetailAddress(selected.getDetailAddress());
            }
        }

        // Tự động điền tên và số điện thoại nếu người dùng để trống
        if (orderInfo.getFullName() == null || orderInfo.getFullName().isEmpty()) {
            orderInfo.setFullName(customer.getName());
        }
        if (orderInfo.getPhone() == null || orderInfo.getPhone().isEmpty()) {
            orderInfo.setPhone(customer.getPhone());
        }

        // Cập nhật thông tin cá nhân khách hàng nếu có thay đổi so với hồ sơ hiện tại
        boolean infoChanged = false;
        if (orderInfo.getFullName() != null && !orderInfo.getFullName().equals(customer.getName())) {
            customer.setName(orderInfo.getFullName());
            infoChanged = true;
        }
        if (orderInfo.getPhone() != null && !orderInfo.getPhone().equals(customer.getPhone())) {
            customer.setPhone(orderInfo.getPhone());
            infoChanged = true;
        }

        if (infoChanged) {
            customerService.update(customer);
            session.setAttribute("loggedInUser", customer);
        }

        // Tính toán tổng tiền cuối cùng (bao gồm phí vận chuyển nếu có)
        int subtotal = shoppingCartService.calculateTotal(customer.getId());
        int shippingFee = 0; // Hiện tại đang miễn phí vận chuyển
        orderInfo.setTotalAmount(subtotal + shippingFee);
        orderInfo.setShippingFee(shippingFee);

        // Lưu DTO vào session để chuyển sang bước thanh toán
        session.setAttribute("orderInfo", orderInfo);

        return "redirect:/order/payment";
    }

    /**
     * Hiển thị trang lựa chọn phương thức thanh toán.
     *
     * @param request Yêu cầu HTTP.
     * @param model Đối tượng Model.
     * @param session Phiên làm việc hiện tại.
     * @return Tên view thanh toán.
     */
    @GetMapping("/order/payment")
    public String payment(HttpServletRequest request, Model model, HttpSession session) {
        model.addAttribute("currentURI", request.getRequestURI());

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

        // Lấy lại danh sách sản phẩm để hiển thị chi tiết trong phần thanh toán
        List<ShoppingCart> cartItems = shoppingCartService.findByCustomerId(customer.getId());

        model.addAttribute("customer", customer);
        model.addAttribute("orderInfo", orderInfo);
        model.addAttribute("cartItems", cartItems);

        return "container/orders/payment";
    }

    /**
     * Xử lý xác nhận thanh toán và tạo hóa đơn.
     *
     * @param paymentMethod Phương thức thanh toán được chọn.
     * @param session Phiên làm việc hiện tại.
     * @param redirectAttributes Đối tượng truyền thông báo.
     * @return Chuyển hướng đến trang hoàn tất đơn hàng.
     */
    @PostMapping("/order/confirm-payment")
    public String confirmPayment(@RequestParam("paymentMethod") String paymentMethod, HttpSession session, RedirectAttributes redirectAttributes) {
        try {
            // Kiểm tra tính hợp lệ của session
            Object loggedInUser = session.getAttribute("loggedInUser");
            OrderInfoDTO orderInfo = (OrderInfoDTO) session.getAttribute("orderInfo");

            if (loggedInUser == null || !(loggedInUser instanceof Customer)) {
                return "redirect:/auth/login";
            }

            Customer customer = (Customer) loggedInUser;

            if (orderInfo == null) {
                return "redirect:/order/infor-order";
            }

            // Lưu địa chỉ giao hàng mới vào cơ sở dữ liệu
            Address address = addressService.createAddress(
                    customer,
                    orderInfo.getCity(),
                    orderInfo.getWard(),
                    orderInfo.getDetailAddress()
            );

            // Chuẩn hóa tên phương thức thanh toán để lưu trữ
            String paymentMethodString;
            if ("bank".equals(paymentMethod)) {
                paymentMethodString = "Chuyển khoản ngân hàng";
            } else if ("cod".equals(paymentMethod)) {
                paymentMethodString = "Thanh toán khi nhận hàng";
            } else {
                paymentMethodString = "Khác";
            }

            // Thực hiện tạo hóa đơn, lưu chi tiết hóa đơn và xóa giỏ hàng
            Bill bill = billService.createBillFromCart(customer, null, address, paymentMethodString);

            // Lưu thông tin cần thiết vào session cho trang hoàn tất
            session.setAttribute("completedBillId", bill.getId());
            session.setAttribute("completedTotalAmount", orderInfo.getTotalAmount());
            session.setAttribute("completedPaymentMethod", paymentMethod);

            // Xóa thông tin đơn hàng tạm thời
            session.removeAttribute("orderInfo");

            redirectAttributes.addFlashAttribute("success", "Đặt hàng thành công!");
            return "redirect:/order/complete";

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Có lỗi xảy ra: " + e.getMessage());
            return "redirect:/order/payment";
        }
    }

    /**
     * Hiển thị trang hoàn tất đơn hàng và mã QR (nếu có).
     *
     * @param request Yêu cầu HTTP.
     * @param model Đối tượng Model.
     * @param session Phiên làm việc hiện tại.
     * @return Tên view hoàn tất đơn hàng.
     */
    @GetMapping("/order/complete")
    public String complete(HttpServletRequest request, Model model, HttpSession session) {
        model.addAttribute("currentURI", request.getRequestURI());

        // Lấy ID hóa đơn và thông tin thanh toán từ session
        String billId = (String) session.getAttribute("completedBillId");
        Integer totalAmount = (Integer) session.getAttribute("completedTotalAmount");
        String paymentMethod = (String) session.getAttribute("completedPaymentMethod");

        if (billId != null) {
            Bill bill = billService.findById(billId);
            model.addAttribute("bill", bill);

            // Tạo URL mã QR thanh toán nếu phương thức là chuyển khoản ngân hàng
            if ("bank".equals(paymentMethod) && totalAmount != null) {
                // Định dạng URL SePay: https://qr.sepay.vn/img?acc={acc}&bank={bank}&amount={amount}&des={des}&template={template}
                String qrUrl = String.format("https://qr.sepay.vn/img?acc=%s&bank=%s&amount=%d&des=%s&template=%s",
                        sepayAccount,
                        sepayBankCode,
                        totalAmount,
                        bill.getId(),
                        sepayTemplate != null ? sepayTemplate : "compact"
                );
                model.addAttribute("qrUrl", qrUrl);
                
                // Thiết lập thời gian hết hạn hiển thị (ví dụ: 10 phút)
                long expiryTimeMillis = bill.getDate().getTime() + (10 * 60 * 1000);
                model.addAttribute("expiryTimeMillis", expiryTimeMillis);
            }

            // Dọn dẹp session sau khi hiển thị xong
            session.removeAttribute("completedBillId");
            session.removeAttribute("completedTotalAmount");
            session.removeAttribute("completedPaymentMethod");
        }

        return "container/orders/complete";
    }
}