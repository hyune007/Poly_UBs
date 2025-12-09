package com.poly.ubs.controller;

import com.poly.ubs.dto.SePayWebhookDTO;
import com.poly.ubs.entity.Bill;
import com.poly.ubs.service.BillServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Xử lý các API thanh toán và Webhook từ cổng thanh toán (SePay).
 */
@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    @Value("${sepay.api.token}")
    private String sepayApiToken;

    @Autowired
    private BillServiceImpl billService;

    /**
     * Xử lý Webhook từ SePay để cập nhật trạng thái đơn hàng.
     * Xác thực token, tìm hóa đơn tương ứng và cập nhật trạng thái thanh toán.
     *
     * @param webhookData Dữ liệu giao dịch từ SePay.
     * @param authHeader  Header xác thực chứa API Key.
     * @return Phản hồi HTTP về kết quả xử lý.
     */
    @PostMapping("/sepay-webhook")
    public ResponseEntity<String> handleSePayWebhook(
            @RequestBody SePayWebhookDTO webhookData,
            @RequestHeader(value = "Authorization", required = false) String authHeader) {
        try {
            // Kiểm tra bảo mật: Header phải có dạng "Apikey API_KEY"
            if (authHeader == null || !authHeader.startsWith("Apikey ")) {
                return ResponseEntity.status(401).body("Unauthorized: Missing or invalid Authorization header");
            }

            // So khớp token nhận được với cấu hình hệ thống
            String receivedToken = authHeader.substring(7).trim(); // Loại bỏ tiền tố "Apikey "
            if (!sepayApiToken.equals(receivedToken)) {
                return ResponseEntity.status(401).body("Unauthorized: Invalid API Token");
            }

            String transactionContent = webhookData.getTransactionContent();
            if (transactionContent == null) {
                return ResponseEntity.badRequest().body("Missing transaction content");
            }

            // Tìm kiếm hóa đơn trong hệ thống có mã trùng với nội dung chuyển khoản
            List<Bill> allBills = billService.findAllBills();
            Bill foundBill = null;

            for (Bill bill : allBills) {
                if (transactionContent.contains(bill.getId())) {
                    foundBill = bill;
                    break;
                }
            }

            // Nếu tìm thấy hóa đơn, cập nhật trạng thái thành "Đã thanh toán"
            if (foundBill != null) {
                billService.updateStatus(foundBill.getId(), "Đã thanh toán");
                return ResponseEntity.ok("Updated bill " + foundBill.getId());
            }

            return ResponseEntity.ok("No matching bill found");

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Error processing webhook: " + e.getMessage());
        }
    }

    /**
     * Kiểm tra trạng thái hóa đơn.
     *
     * @param billId Mã hóa đơn.
     * @return Trạng thái hiện tại của hóa đơn.
     */
    @GetMapping("/check-status/{billId}")
    public ResponseEntity<?> checkBillStatus(@PathVariable String billId) {
        Bill bill = billService.findById(billId);
        if (bill != null) {
            return ResponseEntity.ok().body(java.util.Collections.singletonMap("status", bill.getStatus()));
        }
        return ResponseEntity.notFound().build();
    }
}