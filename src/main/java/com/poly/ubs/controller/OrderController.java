package com.poly.ubs.controller;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Date;
import java.util.List;

/**
 * Bộ điều khiển đơn hàng
 */
@Controller
public class OrderController {
    @Autowired
    private CategoryServiceImpl categoryService;
    @Autowired
    private ShoppingCartServiceImpl shoppingCartService;
    @Autowired
    private AddressServiceImpl addressService;
    @Autowired
    private CustomerServiceImpl customerService;
    @Autowired
    private BillServiceImpl billService;
    @Autowired
    private DetailBillServiceImpl detailBillService;
    @Autowired
    private ProductServiceImpl productService;
    @ModelAttribute("categories")
    public List<Category> getCategories() {
        return categoryService.findAll();
    }
    /**
     * Hiển thị trang giỏ hàng
     * @param request yêu cầu HTTP
     * @param model đối tượng model để truyền dữ liệu đến view
     * @return đường dẫn đến template giỏ hàng
     */
    @GetMapping("/order/shopping-cart")
    public String shoppingCart(HttpServletRequest request, HttpSession session, Model model) {
        model.addAttribute("currentURI", request.getRequestURI());
        Customer loggedInUser = (Customer) session.getAttribute("loggedInUser");
        List<ShoppingCart> cartItems = shoppingCartService.getCartItems(loggedInUser);
        for (ShoppingCart cartItem : cartItems) {
            Product item = cartItem.getProduct();
            String folder = "";
            switch (item.getCategory().getId()) {
                case "LSP01": folder = "phone/"; break;
                case "LSP02": folder = "laptop/"; break;
                case "LSP03": folder = "pad/"; break;
                case "LSP04": folder = "smartwatch/"; break;
                case "LSP05": folder = "headphone/"; break;
                case "LSP06": folder = "keyboard/"; break;
                case "LSP07": folder = "mouse/"; break;
                case "LSP08": folder = "screen/"; break;
                case "LSP09": folder = "speaker/"; break;
                default: folder = "other/";
            }
            item.setImage("products/" + folder + item.getImage());
        }
        double subtotal = cartItems.stream()
                .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
                .sum();

        double shippingFee = (subtotal > 0) ? 40000 : 0; // phí cố định
        double total = subtotal + shippingFee;

        model.addAttribute("cartItems", cartItems);
        model.addAttribute("subtotal", subtotal);
        model.addAttribute("shippingFee", shippingFee);
        model.addAttribute("total", total);
        return "container/orders/shopping-cart";
    }

    @PostMapping("/order/add-to-cart")
    public String addToCart(@RequestParam("productId") String productId,
                            @RequestParam(value = "quantity", defaultValue = "1") int quantity,
                            HttpSession session) {
        Customer loggedInUser = (Customer) session.getAttribute("loggedInUser");
        shoppingCartService.addToCart(loggedInUser.getId(), productId, quantity);
        return "redirect:/order/shopping-cart";
    }

    @PostMapping("/order/update-quantity")
    public String updateQuantity(@RequestParam("productId") String productId,
                                 @RequestParam("quantity") int quantity,
                                 HttpSession session) {
        Customer loggedInUser = (Customer) session.getAttribute("loggedInUser");
        shoppingCartService.updateQuantity(loggedInUser.getId(), productId, quantity);
        return "redirect:/order/shopping-cart";
    }

    @PostMapping("/order/remove-from-cart")
    public String removeFromCart(@RequestParam("productId") String productId,
                                 HttpSession session) {
        Customer loggedInUser = (Customer) session.getAttribute("loggedInUser");
        shoppingCartService.removeFromCart(loggedInUser.getId(), productId);
        return "redirect:/order/shopping-cart";
    }
    /**
     * Hiển thị trang thanh toán
     * @param request yêu cầu HTTP
     * @param model đối tượng model để truyền dữ liệu đến view
     * @return đường dẫn đến template thanh toán
     */
    @GetMapping("/order/payment")
    public String payment(HttpServletRequest request, HttpSession session, Model model) {
        model.addAttribute("currentURI", request.getRequestURI());
        Customer loggedInUser = (Customer) session.getAttribute("loggedInUser");
        Customer customer = customerService.findById(loggedInUser.getId());
        List<Address> addresses = addressService.findByCustomerId(loggedInUser.getId());
        List<ShoppingCart> cartItems = shoppingCartService.getCartItems(loggedInUser);
        for (ShoppingCart cartItem : cartItems) {
            Product item = cartItem.getProduct();
            String folder = "";
            switch (item.getCategory().getId()) {
                case "LSP01": folder = "phone/"; break;
                case "LSP02": folder = "laptop/"; break;
                case "LSP03": folder = "pad/"; break;
                case "LSP04": folder = "smartwatch/"; break;
                case "LSP05": folder = "headphone/"; break;
                case "LSP06": folder = "keyboard/"; break;
                case "LSP07": folder = "mouse/"; break;
                case "LSP08": folder = "screen/"; break;
                case "LSP09": folder = "speaker/"; break;
                default: folder = "other/";
            }
            item.setImage("products/" + folder + item.getImage());
        }
        double subtotal = cartItems.stream()
                .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
                .sum();

        double shippingFee = (subtotal > 0) ? 40000 : 0; // phí cố định
        double total = subtotal + shippingFee;

        model.addAttribute("cartItems", cartItems);
        model.addAttribute("shippingFee", shippingFee);
        model.addAttribute("total", total);
        model.addAttribute("customer", customer);
        model.addAttribute("addresses", addresses);
        return "container/orders/payment";
    }
    
    /**
     * Hiển thị trang hoàn thành đơn hàng
     * @param request yêu cầu HTTP
     * @param model đối tượng model để truyền dữ liệu đến view
     * @return đường dẫn đến template hoàn thành
     */
    @PostMapping("/order/complete")
    public String completeOrder(@RequestParam("addressId") String addressId,
                                @RequestParam("paymentMethod") String paymentMethod,
                                HttpSession session, HttpServletRequest request, Model model,
                                RedirectAttributes redirectAttributes) {
        model.addAttribute("currentURI", request.getRequestURI());
        Customer loggedInUser = (Customer) session.getAttribute("loggedInUser");

        List<ShoppingCart> cartItems = shoppingCartService.getCartItems(loggedInUser);

        if (cartItems == null || cartItems.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Giỏ hàng của bạn đang trống!");
            return "redirect:/order/shopping-cart";
        }

        // ✅ Kiểm tra tồn kho trước khi đặt hàng
        for (ShoppingCart item : cartItems) {
            Product product = item.getProduct();
            if (item.getQuantity() > product.getStock()) {
                redirectAttributes.addFlashAttribute("error",
                        "Sản phẩm '" + product.getName() + "' chỉ còn " + product.getStock() + " sản phẩm trong kho!");
                return "redirect:/order/shopping-cart";
            }
            if(item.getQuantity()<=0){
                redirectAttributes.addFlashAttribute("error",
                        "Sản phẩm '" + product.getName() + "' trong giỏ hàng của bạn có số lượng không phù hợp!");
                return "redirect:/order/shopping-cart";
            }
        }

        Bill bill = new Bill();
        bill.setDate(new Date());
        bill.setStatus("Đang chuẩn bị");
        bill.setPayment(paymentMethod);

        Address address = addressService.findById(addressId);
        bill.setAddress(address);
        bill.setCustomer(loggedInUser);

        billService.save(bill);

        for (ShoppingCart item : cartItems) {
            DetailBill detail = new DetailBill();
            detail.setBill(bill);
            detail.setProduct(item.getProduct());
            detail.setQuantity(item.getQuantity());
            detailBillService.save(detail);
            Product product = item.getProduct();
            int newStock = product.getStock() - item.getQuantity();
            product.setStock(newStock);
            productService.save(product);
        }

        shoppingCartService.clearCart(loggedInUser);
        return "container/orders/complete";
    }
}
