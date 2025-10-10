package com.poly.ubs;

import com.poly.ubs.entity.Product;
import com.poly.ubs.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DemoProductController implements CommandLineRunner {
    
    @Autowired
    private ProductRepository productRepository;
    
    @Override
    public void run(String... args) throws Exception {
        System.out.println("=== Spring Data JPA Demo: Retrieving First 20 Products ===");
        
        List<Product> products = productRepository.findFirst20Products();
        
        System.out.println("First 20 Products from SanPham table:");
        System.out.println("=====================================");
        
        for (Product product : products) {
            System.out.println("ID: " + product.getId());
            System.out.println("Name: " + product.getName());
            System.out.println("Price: " + product.getPrice());
            System.out.println("Description: " + product.getDescription());
            System.out.println("Image: " + product.getImage());
            System.out.println("Stock: " + product.getStock());
            System.out.println("------------------------");
        }
        
        System.out.println("Total products retrieved: " + products.size());
    }
}