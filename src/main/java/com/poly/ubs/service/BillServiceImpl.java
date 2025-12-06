package com.poly.ubs.service;

import com.poly.ubs.entity.*;
import com.poly.ubs.repository.BillRepository;
import com.poly.ubs.repository.CustomerRepository;
import com.poly.ubs.repository.DetailBillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service xử lý nghiệp vụ hóa đơn
 */
@Service
public class BillServiceImpl extends GenericServiceImpl<Bill, String, BillRepository> {

    @Autowired
    private BillRepository billRepository;

    @Autowired
    private DetailBillRepository detailBillRepository;

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private ProductServiceImpl productService;

    @Autowired
    private CustomerRepository customerRepository;

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
     * @param paymentMethod phương thức thanh toán
     * @return hóa đơn đã tạo
     */
    @Transactional
    public Bill createBillFromCart(Customer customer, Employee employee, Address address, String paymentMethod) {
        // Lấy giỏ hàng của khách hàng
        List<ShoppingCart> cartItems = shoppingCartService.findByCustomerId(customer.getId());

        if (cartItems == null || cartItems.isEmpty()) {
            throw new RuntimeException("Giỏ hàng trống, không thể tạo hóa đơn");
        }

        // Tạo hóa đơn mới
        Bill bill = new Bill();
        bill.setId(generateBillId());
        bill.setDate(new Date());
        
        // Set phương thức thanh toán
        bill.setPaymentMethod(paymentMethod);

        // Set trạng thái dựa trên phương thức thanh toán
        if ("Chuyển khoản ngân hàng".equals(paymentMethod)) {
            bill.setStatus("Chưa thanh toán");
        } else {
            bill.setStatus("Chờ xác nhận");
        }

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
    public Bill findById(String id) {
        return billRepository.findById(id).orElse(null);
    }

    /** Lấy danh sách khách hàng có hóa đơn */
    public List<Customer> findCustomersWithBills() {
        return billRepository.findAll().stream()
                .map(Bill::getCustomer)
                .distinct()
                .collect(Collectors.toList());
    }

    /** Lấy tất cả hóa đơn */
    public List<Bill> findAllBills() {
        return billRepository.findAll();
    }

    public List<Bill> findByCustomerId(String customerId) {
        return billRepository.findByCustomerId(customerId);
    }

//    public List<Customer> findCustomersWithActiveBills() {
//        List<Customer> customers = customerRepository.findAll(); // hoặc cách bạn đang lấy
//        return customers.stream()
//                .filter(c -> c.getBills().stream()
//                        .anyMatch(b -> !"Đã giao thành công".equals(b.getStatus())))
//                .collect(Collectors.toList());
//    }

    public List<Customer> findCustomersWithConfirmedBills() {
        List<Customer> customers = customerRepository.findAll();
        return customers.stream()
                .filter(c -> c.getBills().stream()
                        .anyMatch(b -> "Đã xác nhận".equals(b.getStatus())))
                .collect(Collectors.toList());
    }
}