package com.poly.ubs.service;

import com.poly.ubs.entity.Bill;
import com.poly.ubs.entity.DetailBill;
import com.poly.ubs.entity.Product;
import com.poly.ubs.repository.BillRepository;
import com.poly.ubs.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

/**
 * Dịch vụ tự động xử lý các tác vụ liên quan đến đơn hàng theo lịch trình.
 */
@Service
public class OrderAutoCancelService {

    @Autowired
    private BillRepository billRepository;

    @Autowired
    private ProductRepository productRepository;

    /**
     * Quét và hủy các đơn hàng quá hạn thanh toán theo chu kỳ mỗi phút.
     * <p>
     * Điều kiện hủy: Đơn hàng ở trạng thái "Chưa thanh toán" và được tạo quá 10 phút.
     * Hành động:
     * - Chuyển trạng thái đơn hàng sang "Đã Hủy".
     * - Hoàn trả số lượng sản phẩm trong đơn hàng về kho.
     */
    @Scheduled(cron = "0 * * * * *")
    @Transactional
    public void scanAndCancelExpiredOrders() {
        // Xác định mốc thời gian giới hạn (10 phút trước)
        LocalDateTime tenMinutesAgo = LocalDateTime.now().minusMinutes(10);
        Date thresholdDate = Date.from(tenMinutesAgo.atZone(ZoneId.systemDefault()).toInstant());

        // Truy vấn các hóa đơn hết hạn
        List<Bill> expiredBills = billRepository.findByStatusAndDateBefore("Chưa thanh toán", thresholdDate);

        if (!expiredBills.isEmpty()) {
            System.out.println(">>> [OrderAutoCancel] Found " + expiredBills.size() + " expired bills. Cancelling...");
        }

        // Xử lý từng hóa đơn
        for (Bill bill : expiredBills) {
            try {
                // Cập nhật trạng thái hóa đơn
                bill.setStatus("Đã Hủy");
                billRepository.save(bill);

                // Hoàn trả tồn kho
                List<DetailBill> details = bill.getBillDetails();
                if (details != null) {
                    for (DetailBill detail : details) {
                        Product product = detail.getProduct();
                        if (product != null) {
                            int quantityToReturn = detail.getQuantity();
                            product.setStock(product.getStock() + quantityToReturn);
                            productRepository.save(product);
                            System.out.println(">>> [OrderAutoCancel] Restocked Product " + product.getId() + ": +" + quantityToReturn);
                        }
                    }
                }

                System.out.println(">>> [OrderAutoCancel] Cancelled Bill #" + bill.getId());
            } catch (Exception e) {
                System.err.println(">>> [OrderAutoCancel] Error cancelling Bill " + bill.getId() + ": " + e.getMessage());
            }
        }
    }
}