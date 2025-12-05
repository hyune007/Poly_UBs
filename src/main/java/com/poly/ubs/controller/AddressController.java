package com.poly.ubs.controller;

import com.poly.ubs.entity.Address;
import com.poly.ubs.entity.Customer;
import com.poly.ubs.service.AddressServiceImpl;
import com.poly.ubs.service.CustomerServiceImpl;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Bộ điều khiển quản lý địa chỉ
 */
@RestController
public class AddressController {

    @Autowired
    private AddressServiceImpl addressService;

    @Autowired
    private CustomerServiceImpl customerService;

    /**
     * Tạo địa chỉ mới cho khách hàng đang đăng nhập
     * @param payload JSON gồm city, ward, detailAddress
     * @param session phiên HTTP lưu loggedInUser
     * @return thông tin địa chỉ đã tạo
     */
    @PostMapping("/addresses")
    public ResponseEntity<?> createAddress(@RequestBody Map<String, String> payload, HttpSession session) {
        Object loggedInUser = session.getAttribute("loggedInUser");
        if (loggedInUser == null || !(loggedInUser instanceof Customer)) {
            return ResponseEntity.status(401).body("Chưa đăng nhập");
        }
        Customer customer = (Customer) loggedInUser;
        String city = payload.get("city");
        String ward = payload.get("ward");
        String detailAddress = payload.get("detailAddress");
        if (city == null || ward == null || detailAddress == null) {
            return ResponseEntity.badRequest().body("Thiếu dữ liệu địa chỉ");
        }
        Address address = addressService.createAddress(customer, city, ward, detailAddress);
        // Cập nhật lại list địa chỉ trong session (tránh phải reload từ DB nếu không có cascade eager refresh)
        customer.getAddresses().add(address);
        session.setAttribute("loggedInUser", customer);

        Map<String, Object> result = new HashMap<>();
        result.put("id", address.getId());
        result.put("fullAddress", address.getFullAddress());
        return ResponseEntity.ok(result);
    }

    /**
     * Đặt địa chỉ mặc định
     */
    @PutMapping("/addresses/{id}/default")
    public ResponseEntity<?> setDefault(@PathVariable("id") String id, HttpSession session) {
        Object loggedInUser = session.getAttribute("loggedInUser");
        if (loggedInUser == null || !(loggedInUser instanceof Customer)) {
            return ResponseEntity.status(401).body("Chưa đăng nhập");
        }
        Customer customer = (Customer) loggedInUser;
        if (customer.getAddresses() == null || customer.getAddresses().stream().noneMatch(a -> a.getId().equals(id))) {
            return ResponseEntity.badRequest().body("Địa chỉ không thuộc khách hàng");
        }
        addressService.setDefaultAddress(customer, id);
        // reload list từ session object:
        session.setAttribute("loggedInUser", customer);
        return ResponseEntity.ok("Đặt địa chỉ mặc định thành công");
    }

    @DeleteMapping("/addresses/{id}")
    public ResponseEntity<?> removeAddress(@PathVariable("id") String id, HttpSession session) {
        Object loggedInUser = session.getAttribute("loggedInUser");
        if (loggedInUser == null || !(loggedInUser instanceof Customer)) {
            return ResponseEntity.status(401).body("Chưa đăng nhập");
        }
        Customer customer = (Customer) loggedInUser;
        try {
            addressService.deleteByIdAndCustomer(id, customer);
            customer.getAddresses().removeIf(a -> a.getId().equals(id));
            // reload list từ session object:
            session.setAttribute("loggedInUser", customer);
            return ResponseEntity.ok("Xóa địa chỉ thành công");
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity
                    .status(409)    // Conflict
                    .body("Địa chỉ này đã được sử dụng trong hóa đơn. Không thể xóa!");
        }
    }
}
