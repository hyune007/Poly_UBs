package com.poly.ubs.service;

import com.poly.ubs.entity.Customer;
import com.poly.ubs.entity.Product;
import com.poly.ubs.entity.ShoppingCart;
import com.poly.ubs.repository.ShoppingCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ShoppingCartServiceImpl extends GenericServiceImpl<ShoppingCart, String, ShoppingCartRepository>{
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

    public List<ShoppingCart> getCartItems(Customer customer) {
        return shoppingCartRepository.findByCustomer(customer);
    }

    public void addToCart(String customerId, String productId, int quantity) {
        Customer customer = customerService.findById(customerId);
        Product product = productService.findById(productId);

        ShoppingCart existing = shoppingCartRepository.findByCustomerAndProduct(customer, product);

        if (existing != null) {
            existing.setQuantity(existing.getQuantity() + quantity);
        } else {
            existing = new ShoppingCart();
            existing.setCustomer(customer);
            existing.setProduct(product);
            existing.setQuantity(quantity);
        }
        shoppingCartRepository.save(existing);
    }

    public void updateQuantity(String customerId, String productId, int quantity) {
        Customer customer = customerService.findById(customerId);
        Product product = productService.findById(productId);

        ShoppingCart item = shoppingCartRepository.findByCustomerAndProduct(customer, product);
        if (item != null) {
            item.setQuantity(quantity);
            shoppingCartRepository.save(item);
        }
    }

    public void removeFromCart(String customerId, String productId) {
        Customer customer = customerService.findById(customerId);
        Product product = productService.findById(productId);

        ShoppingCart item = shoppingCartRepository.findByCustomerAndProduct(customer, product);
        if (item != null) {
            shoppingCartRepository.delete(item);
        }
    }

    public void clearCart(Customer customer) {
        List<ShoppingCart> items = shoppingCartRepository.findByCustomer(customer);
        shoppingCartRepository.deleteAll(items);
    }
}
