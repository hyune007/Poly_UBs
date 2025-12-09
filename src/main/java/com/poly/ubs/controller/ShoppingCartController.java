package com.poly.ubs.controller;

import com.poly.ubs.entity.Customer;
import com.poly.ubs.entity.Product;
import com.poly.ubs.entity.ShoppingCart;
import com.poly.ubs.service.ShoppingCartService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Quản lý các thao tác giỏ hàng (xem, thêm, sửa, xóa).
 */
@Controller
@RequestMapping("/cart")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService cartService;

    /**
     * Hiển thị trang chi tiết giỏ hàng.
     *
     * @param session Phiên làm việc hiện tại.
     * @param model   Đối tượng Model.
     * @return Tên view giỏ hàng.
     */
    @GetMapping
    public String viewCart(HttpSession session, Model model) {
        Object loggedInUser = session.getAttribute("loggedInUser");

        if (loggedInUser == null || !(loggedInUser instanceof Customer)) {
            return "redirect:/login?error=needLogin";
        }

        Customer customer = (Customer) loggedInUser;
        List<ShoppingCart> cartItems = cartService.findByCustomerId(customer.getId());

        // Xử lý đường dẫn hình ảnh cho từng sản phẩm trong giỏ dựa trên mã danh mục
        for (ShoppingCart item : cartItems) {
            Product product = item.getProduct();
            if (product != null && product.getCategory() != null) {
                String folder = getFolderByCategory(product.getCategory().getId());
                product.setImage("products/" + folder + product.getImage());
            }
        }

        int total = cartService.calculateTotal(customer.getId());
        int itemCount = cartService.countItems(customer.getId());

        model.addAttribute("cartItems", cartItems);
        model.addAttribute("total", total);
        model.addAttribute("itemCount", itemCount);
        model.addAttribute("loggedInUser", customer);
        model.addAttribute("currentURI", "/cart");

        return "container/orders/shopping-cart";
    }

    /**
     * API thêm sản phẩm vào giỏ hàng.
     *
     * @param productId ID sản phẩm.
     * @param quantity  Số lượng.
     * @param session   Phiên làm việc hiện tại.
     * @return Phản hồi JSON kết quả thực hiện.
     */
    @PostMapping("/add")
    @ResponseBody
    public ResponseEntity<?> addToCart(
            @RequestParam String productId,
            @RequestParam(defaultValue = "1") int quantity,
            HttpSession session) {

        Map<String, Object> response = new HashMap<>();

        try {
            // Kiểm tra trạng thái đăng nhập
            Object loggedInUser = session.getAttribute("loggedInUser");
            if (loggedInUser == null || !(loggedInUser instanceof Customer)) {
                response.put("success", false);
                response.put("message", "Vui lòng đăng nhập để thêm sản phẩm vào giỏ hàng");
                return ResponseEntity.ok(response);
            }

            Customer customer = (Customer) loggedInUser;

            // Kiểm tra tính hợp lệ của số lượng
            if (quantity <= 0) {
                response.put("success", false);
                response.put("message", "Số lượng phải lớn hơn 0");
                return ResponseEntity.ok(response);
            }

            // Thực hiện thêm vào cơ sở dữ liệu
            ShoppingCart cart = cartService.addToCart(customer.getId(), productId, quantity);

            // Cập nhật số lượng hiển thị trên icon giỏ hàng
            int itemCount = cartService.countItems(customer.getId());

            response.put("success", true);
            response.put("message", "Đã thêm sản phẩm vào giỏ hàng");
            response.put("cartItemCount", itemCount);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.ok(response);
        }
    }

    /**
     * API cập nhật số lượng sản phẩm trong giỏ.
     *
     * @param cartId   ID giỏ hàng chi tiết.
     * @param quantity Số lượng mới.
     * @param session  Phiên làm việc hiện tại.
     * @return Phản hồi JSON kết quả thực hiện.
     */
    @PostMapping("/update")
    @ResponseBody
    public ResponseEntity<?> updateQuantity(
            @RequestParam String cartId,
            @RequestParam int quantity,
            HttpSession session) {

        Map<String, Object> response = new HashMap<>();

        try {
            Object loggedInUser = session.getAttribute("loggedInUser");
            if (loggedInUser == null || !(loggedInUser instanceof Customer)) {
                response.put("success", false);
                response.put("message", "Vui lòng đăng nhập");
                return ResponseEntity.ok(response);
            }

            if (quantity <= 0) {
                response.put("success", false);
                response.put("message", "Số lượng phải lớn hơn 0");
                return ResponseEntity.ok(response);
            }

            // Thực hiện cập nhật
            cartService.updateQuantity(cartId, quantity);

            Customer customer = (Customer) loggedInUser;
            int total = cartService.calculateTotal(customer.getId());
            int itemCount = cartService.countItems(customer.getId());

            response.put("success", true);
            response.put("message", "Đã cập nhật số lượng");
            response.put("total", total);
            response.put("cartItemCount", itemCount);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.ok(response);
        }
    }

    /**
     * API xóa sản phẩm khỏi giỏ hàng.
     *
     * @param cartId  ID giỏ hàng chi tiết.
     * @param session Phiên làm việc hiện tại.
     * @return Phản hồi JSON kết quả thực hiện.
     */
    @PostMapping("/remove")
    @ResponseBody
    public ResponseEntity<?> removeFromCart(
            @RequestParam String cartId,
            HttpSession session) {

        Map<String, Object> response = new HashMap<>();

        try {
            Object loggedInUser = session.getAttribute("loggedInUser");
            if (loggedInUser == null || !(loggedInUser instanceof Customer)) {
                response.put("success", false);
                response.put("message", "Vui lòng đăng nhập");
                return ResponseEntity.ok(response);
            }

            // Thực hiện xóa
            cartService.removeFromCart(cartId);

            Customer customer = (Customer) loggedInUser;
            int total = cartService.calculateTotal(customer.getId());
            int itemCount = cartService.countItems(customer.getId());

            response.put("success", true);
            response.put("message", "Đã xóa sản phẩm khỏi giỏ hàng");
            response.put("total", total);
            response.put("cartItemCount", itemCount);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.ok(response);
        }
    }

    /**
     * API xóa toàn bộ sản phẩm trong giỏ hàng.
     *
     * @param session Phiên làm việc hiện tại.
     * @return Phản hồi JSON kết quả thực hiện.
     */
    @PostMapping("/clear")
    @ResponseBody
    public ResponseEntity<?> clearCart(HttpSession session) {
        Map<String, Object> response = new HashMap<>();

        try {
            Object loggedInUser = session.getAttribute("loggedInUser");
            if (loggedInUser == null || !(loggedInUser instanceof Customer)) {
                response.put("success", false);
                response.put("message", "Vui lòng đăng nhập");
                return ResponseEntity.ok(response);
            }

            Customer customer = (Customer) loggedInUser;
            cartService.clearCart(customer.getId());

            response.put("success", true);
            response.put("message", "Đã xóa toàn bộ giỏ hàng");

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.ok(response);
        }
    }

    /**
     * API lấy tổng số lượng sản phẩm trong giỏ (dùng cho icon).
     *
     * @param session Phiên làm việc hiện tại.
     * @return Phản hồi JSON số lượng sản phẩm.
     */
    @GetMapping("/count")
    @ResponseBody
    public ResponseEntity<?> getCartCount(HttpSession session) {
        Map<String, Object> response = new HashMap<>();

        try {
            Object loggedInUser = session.getAttribute("loggedInUser");
            if (loggedInUser == null || !(loggedInUser instanceof Customer)) {
                response.put("count", 0);
                return ResponseEntity.ok(response);
            }

            Customer customer = (Customer) loggedInUser;
            int itemCount = cartService.countItems(customer.getId());

            response.put("count", itemCount);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            response.put("count", 0);
            return ResponseEntity.ok(response);
        }
    }

    /**
     * Xác định thư mục chứa ảnh dựa trên mã danh mục.
     *
     * @param categoryId Mã danh mục.
     * @return Tên thư mục chứa ảnh.
     */
    private String getFolderByCategory(String categoryId) {
        return switch (categoryId) {
            case "LSP01" -> "phone/";
            case "LSP02" -> "laptop/";
            case "LSP03" -> "pad/";
            case "LSP04" -> "smartwatch/";
            case "LSP05" -> "headphone/";
            case "LSP06" -> "keyboard/";
            case "LSP07" -> "mouse/";
            case "LSP08" -> "screen/";
            case "LSP09" -> "speaker/";
            default -> "other/";
        };
    }
}