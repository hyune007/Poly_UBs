package com.poly.ubs.controller;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.poly.ubs.entity.Customer;
import com.poly.ubs.repository.CustomerRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Controller xử lý Firebase Authentication
 */
@RestController
@RequestMapping("/api/auth")
public class FirebaseAuthController {

    @Autowired
    private CustomerRepository customerRepository;

    /**
     * Endpoint để xác thực Firebase ID Token và tạo session
     * @param request chứa idToken từ Firebase client
     * @param session HTTP session
     * @return thông tin customer
     */
    @PostMapping("/firebase-login")
    public ResponseEntity<?> firebaseLogin(@RequestBody Map<String, String> request, HttpSession session) {
        try {
            String idToken = request.get("idToken");

            // Xác thực token với Firebase Admin SDK
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);

            // Lấy thông tin user từ token
            String email = decodedToken.getEmail();
            String name = decodedToken.getName();
            String uid = decodedToken.getUid();

            // Tìm hoặc tạo customer
            Customer customer = customerRepository.findByEmail(email);

            if (customer == null) {
                // Tạo customer mới từ Google account
                customer = new Customer();
                customer.setId(generateCustomerId());
                customer.setName(name != null ? name : "Google User");
                customer.setEmail(email);
                // Password mẫu cho tài khoản Google (phù hợp với giới hạn VARCHAR(40))
                customer.setPassword("GOOGLE_AUTH"); // Đánh dấu là tài khoản Firebase/Google
                customer.setPhone(""); // Có thể cập nhật sau

                customerRepository.save(customer);
            }

            // Lưu thông tin vào session
            session.setAttribute("loggedInUser", customer);

            // Trả về thông tin customer
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("customer", Map.of(
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

    /**
     * Tạo ID cho khách hàng mới
     * @return ID duy nhất cho khách hàng
     */
    private String generateCustomerId() {
        String prefix = "KH";
        int maxId = 0;

        for (Customer customer : customerRepository.findAll()) {
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

        return prefix + String.format("%03d", maxId + 1);
    }
}
