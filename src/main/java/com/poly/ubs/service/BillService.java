package com.poly.ubs.service;

import com.poly.ubs.entity.*;
import com.poly.ubs.repository.BillRepository;
import com.poly.ubs.repository.DetailBillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Service xử lý nghiệp vụ hóa đơn
 */
@Service
public class BillService extends GenericServiceImpl<Bill, String, BillRepository> {

    @Autowired
    private BillRepository billRepository;

    @Autowired
    private DetailBillRepository detailBillRepository;

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private ProductServiceImpl productService;

    @Override
    protected BillRepository getRepository() {
        return billRepository;
    }

    /**
     * Tạo hóa đơn từ giỏ hàng
     *
     * @param customer khách hàng
     * @param employee nhân viên xử lý (có thể null nếu đặt hàng online)
     * @param address  địa chỉ giao hàng
     * @return hóa đơn đã tạo
     */
    @Transactional
    public Bill createBillFromCart(Customer customer, Employee employee, Address address) {
        // Lấy giỏ hàng của khách hàng
        List<ShoppingCart> cartItems = shoppingCartService.findByCustomerId(customer.getId());

        if (cartItems == null || cartItems.isEmpty()) {
            throw new RuntimeException("Giỏ hàng trống, không thể tạo hóa đơn");
        }

        // Tạo hóa đơn mới
        Bill bill = new Bill();
        bill.setId(generateBillId());
        bill.setDate(new Date());
        bill.setStatus("Chờ xác nhận");
        bill.setCustomer(customer);
        bill.setEmployee(employee);
        bill.setAddress(address);

        // Lưu hóa đơn
        Bill savedBill = billRepository.save(bill);

        // Tạo chi tiết hóa đơn từ giỏ hàng
        for (ShoppingCart cartItem : cartItems) {
            DetailBill detailBill = new DetailBill();
            detailBill.setId(generateDetailBillId());
            detailBill.setBill(savedBill);
            detailBill.setProduct(cartItem.getProduct());
            detailBill.setQuantity(cartItem.getQuantity());
            detailBill.setTotal(cartItem.getProduct().getPrice() * cartItem.getQuantity());

            // Lưu chi tiết hóa đơn
            detailBillRepository.save(detailBill);

            // Cập nhật số lượng tồn kho
            Product product = cartItem.getProduct();
            product.setStock(product.getStock() - cartItem.getQuantity());
            productService.save(product);
        }

        // Xóa giỏ hàng sau khi đã tạo hóa đơn
        shoppingCartService.clearCart(customer.getId());

        return savedBill;
    }

    /**
     * Tính tổng tiền của hóa đơn
     *
     * @param billId ID hóa đơn
     * @return tổng tiền
     */
    public int calculateBillTotal(String billId) {
        List<DetailBill> details = detailBillRepository.findByBillId(billId);
        return details.stream()
                .mapToInt(DetailBill::getTotal)
                .sum();
    }

    /**
     * Cập nhật trạng thái hóa đơn
     *
     * @param billId ID hóa đơn
     * @param status trạng thái mới
     */
    public void updateStatus(String billId, String status) {
        Bill bill = billRepository.findById(billId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy hóa đơn"));
        bill.setStatus(status);
        billRepository.save(bill);
    }

    /**
     * Tạo ID ngẫu nhiên cho hóa đơn
     *
     * @return ID hóa đơn
     */
    private String generateBillId() {
        return "HD" + UUID.randomUUID().toString().substring(0, 6).toUpperCase();
    }

    /**
     * Tạo ID ngẫu nhiên cho chi tiết hóa đơn
     *
     * @return ID chi tiết hóa đơn
     */
    private String generateDetailBillId() {
        return "CT" + UUID.randomUUID().toString().substring(0, 6).toUpperCase();
    }
}

