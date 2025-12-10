package com.poly.ubs.service;

import com.poly.ubs.entity.Customer;
import com.poly.ubs.entity.Product;
import com.poly.ubs.entity.ShoppingCart;
import com.poly.ubs.repository.ShoppingCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * Triển khai dịch vụ xử lý nghiệp vụ liên quan đến giỏ hàng.
 */
@Service
public class ShoppingCartService extends GenericServiceImpl<ShoppingCart, String, ShoppingCartRepository> {

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private ProductServiceImpl productService;

    @Autowired
    private CustomerServiceImpl customerService;

    @Override
    protected ShoppingCartRepository getRepository() {
        return shoppingCartRepository;
    }

    /**
     * Lấy danh sách sản phẩm trong giỏ hàng của khách hàng.
     *
     * @param customerId Mã khách hàng.
     * @return Danh sách đối tượng ShoppingCart.
     */
    public List<ShoppingCart> findByCustomerId(String customerId) {
        return shoppingCartRepository.findByCustomerId(customerId);
    }

    /**
     * Thêm sản phẩm vào giỏ hàng.
     * Nếu sản phẩm đã tồn tại, cập nhật số lượng. Nếu chưa, tạo mới.
     * Kiểm tra tồn kho trước khi thực hiện.
     *
     * @param customerId Mã khách hàng.
     * @param productId  Mã sản phẩm.
     * @param quantity   Số lượng cần thêm.
     * @return Đối tượng ShoppingCart đã được cập nhật hoặc tạo mới.
     * @throws RuntimeException Nếu khách hàng/sản phẩm không tồn tại hoặc không đủ hàng.
     */
    public ShoppingCart addToCart(String customerId, String productId, int quantity) {
        // Kiểm tra khách hàng có tồn tại không
        Customer customer = customerService.findById(customerId);
        if (customer == null) {
            throw new RuntimeException("Khách hàng không tồn tại");
        }

        // Kiểm tra sản phẩm có tồn tại không
        Product product = productService.findById(productId);
        if (product == null) {
            throw new RuntimeException("Sản phẩm không tồn tại");
        }

        // Kiểm tra số lượng tồn kho
        if (product.getStock() < quantity) {
            throw new RuntimeException("Số lượng sản phẩm trong kho không đủ");
        }

        // Kiểm tra xem sản phẩm đã có trong giỏ hàng chưa
        ShoppingCart existingCart = shoppingCartRepository.findByCustomerIdAndProductId(customerId, productId);

        if (existingCart != null) {
            // Nếu đã có thì cập nhật số lượng
            int newQuantity = existingCart.getQuantity() + quantity;

            // Kiểm tra lại số lượng tồn kho
            if (product.getStock() < newQuantity) {
                throw new RuntimeException("Số lượng sản phẩm trong kho không đủ");
            }

            existingCart.setQuantity(newQuantity);
            return shoppingCartRepository.save(existingCart);
        } else {
            // Nếu chưa có thì thêm mới
            ShoppingCart newCart = new ShoppingCart();
            newCart.setId(generateCartId());
            newCart.setCustomer(customer);
            newCart.setProduct(product);
            newCart.setQuantity(quantity);
            return shoppingCartRepository.save(newCart);
        }
    }

    /**
     * Cập nhật số lượng sản phẩm trong giỏ hàng.
     *
     * @param cartId   Mã giỏ hàng chi tiết.
     * @param quantity Số lượng mới.
     * @return Đối tượng ShoppingCart đã cập nhật.
     * @throws RuntimeException Nếu không tìm thấy giỏ hàng hoặc không đủ hàng.
     */
    public ShoppingCart updateQuantity(String cartId, int quantity) {
        ShoppingCart cart = shoppingCartRepository.findById(cartId).orElse(null);
        if (cart == null) {
            throw new RuntimeException("Không tìm thấy sản phẩm trong giỏ hàng");
        }

        // Kiểm tra số lượng tồn kho
        if (cart.getProduct().getStock() < quantity) {
            throw new RuntimeException("Số lượng sản phẩm trong kho không đủ");
        }

        cart.setQuantity(quantity);
        return shoppingCartRepository.save(cart);
    }

    /**
     * Xóa một mục sản phẩm khỏi giỏ hàng.
     *
     * @param cartId Mã giỏ hàng chi tiết.
     */
    public void removeFromCart(String cartId) {
        shoppingCartRepository.deleteById(cartId);
    }

    /**
     * Xóa toàn bộ sản phẩm trong giỏ hàng của khách hàng.
     *
     * @param customerId Mã khách hàng.
     */
    public void clearCart(String customerId) {
        List<ShoppingCart> carts = findByCustomerId(customerId);
        shoppingCartRepository.deleteAll(carts);
    }

    /**
     * Tính tổng giá trị tiền của giỏ hàng.
     *
     * @param customerId Mã khách hàng.
     * @return Tổng tiền.
     */
    public int calculateTotal(String customerId) {
        List<ShoppingCart> carts = findByCustomerId(customerId);
        return carts.stream()
                .mapToInt(cart -> cart.getProduct().getPrice() * cart.getQuantity())
                .sum();
    }

    /**
     * Đếm tổng số lượng sản phẩm (items) trong giỏ hàng.
     *
     * @param customerId Mã khách hàng.
     * @return Tổng số lượng sản phẩm.
     */
    public int countItems(String customerId) {
        List<ShoppingCart> carts = findByCustomerId(customerId);
        return carts.stream()
                .mapToInt(ShoppingCart::getQuantity)
                .sum();
    }

    /**
     * Sinh mã định danh ngẫu nhiên cho giỏ hàng.
     *
     * @return Chuỗi ID giỏ hàng.
     */
    private String generateCartId() {
        return "GH" + UUID.randomUUID().toString().substring(0, 6).toUpperCase();
    }
}