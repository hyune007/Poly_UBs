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
 * Controller xử lý giỏ hàng
 */
@Controller
@RequestMapping("/cart")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService cartService;

    /**
     * Hiển thị trang giỏ hàng
     *
     * @param session đối tượng session
     * @param model   đối tượng model
     * @return tên view
     */
    @GetMapping
    public String viewCart(HttpSession session, Model model) {
        Object loggedInUser = session.getAttribute("loggedInUser");

        if (loggedInUser == null || !(loggedInUser instanceof Customer)) {
            return "redirect:/login?error=needLogin";
        }

        Customer customer = (Customer) loggedInUser;
        List<ShoppingCart> cartItems = cartService.findByCustomerId(customer.getId());

        // Xử lý đường dẫn hình ảnh cho từng sản phẩm trong giỏ
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
        model.addAttribute("currentURI", "/cart");  // Thêm biến currentURI để order-process.html sử dụng

        return "container/orders/shopping-cart";
    }

    /**
     * Thêm sản phẩm vào giỏ hàng
     *
     * @param productId ID sản phẩm
     * @param quantity  số lượng
     * @param session   đối tượng session
     * @return response JSON
     */
    @PostMapping("/add")
    @ResponseBody
    public ResponseEntity<?> addToCart(
            @RequestParam String productId,
            @RequestParam(defaultValue = "1") int quantity,
            HttpSession session) {

        Map<String, Object> response = new HashMap<>();

        try {
            // Kiểm tra đăng nhập
            Object loggedInUser = session.getAttribute("loggedInUser");
            if (loggedInUser == null || !(loggedInUser instanceof Customer)) {
                response.put("success", false);
                response.put("message", "Vui lòng đăng nhập để thêm sản phẩm vào giỏ hàng");
                return ResponseEntity.ok(response);
            }

            Customer customer = (Customer) loggedInUser;

            // Kiểm tra số lượng hợp lệ
            if (quantity <= 0) {
                response.put("success", false);
                response.put("message", "Số lượng phải lớn hơn 0");
                return ResponseEntity.ok(response);
            }

            // Thêm vào giỏ hàng
            ShoppingCart cart = cartService.addToCart(customer.getId(), productId, quantity);

            // Đếm số lượng sản phẩm trong giỏ
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
     * Cập nhật số lượng sản phẩm trong giỏ hàng
     *
     * @param cartId   ID giỏ hàng
     * @param quantity số lượng mới
     * @param session  đối tượng session
     * @return response JSON
     */
    @PostMapping("/update")
    @ResponseBody
    public ResponseEntity<?> updateQuantity(
            @RequestParam String cartId,
            @RequestParam int quantity,
            HttpSession session) {

        Map<String, Object> response = new HashMap<>();

        try {
            // Kiểm tra đăng nhập
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

            // Cập nhật số lượng
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
     * Xóa sản phẩm khỏi giỏ hàng
     *
     * @param cartId  ID giỏ hàng
     * @param session đối tượng session
     * @return response JSON
     */
    @PostMapping("/remove")
    @ResponseBody
    public ResponseEntity<?> removeFromCart(
            @RequestParam String cartId,
            HttpSession session) {

        Map<String, Object> response = new HashMap<>();

        try {
            // Kiểm tra đăng nhập
            Object loggedInUser = session.getAttribute("loggedInUser");
            if (loggedInUser == null || !(loggedInUser instanceof Customer)) {
                response.put("success", false);
                response.put("message", "Vui lòng đăng nhập");
                return ResponseEntity.ok(response);
            }

            // Xóa sản phẩm
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
     * Xóa toàn bộ giỏ hàng
     *
     * @param session đối tượng session
     * @return response JSON
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
     * Lấy số lượng sản phẩm trong giỏ hàng
     *
     * @param session đối tượng session
     * @return response JSON
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
     * Lấy thư mục hình ảnh theo danh mục sản phẩm
     *
     * @param categoryId ID danh mục
     * @return tên thư mục
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

