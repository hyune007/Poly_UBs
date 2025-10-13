//package com.poly.ubs;
//
//import com.poly.ubs.entity.Product;
//
//import java.sql.*;
//import java.util.ArrayList;
//import java.util.List;
//
//public class DemoProductRetrieval {
//
//    // Database configuration from application.properties
//    private static final String DB_URL = "jdbc:mysql://localhost:3306/poly_ubs?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
//    private static final String DB_USERNAME = "root";
//    private static final String DB_PASSWORD = "huyhuyhuy201107";
//
//    public static void main(String[] args) {
//        List<Product> products = getFirst20Products();
//
//        System.out.println("First 20 Products from SanPham table:");
//        System.out.println("=====================================");
//
//        for (Product product : products) {
//            System.out.println("ID: " + product.getId());
//            System.out.println("Name: " + product.getName());
//            System.out.println("Price: " + product.getPrice());
//            System.out.println("Description: " + product.getDescription());
//            System.out.println("Image: " + product.getImage());
//            System.out.println("Stock: " + product.getStock());
//            System.out.println("------------------------");
//        }
//
//        System.out.println("Total products retrieved: " + products.size());
//    }
//
//    public static List<Product> getFirst20Products() {
//        List<Product> products = new ArrayList<>();
//
//        String query = "SELECT sp_id, sp_name, sp_price, sp_description, sp_image, sp_stock, sp_brand_id, sp_category_id " +
//                      "FROM SanPham LIMIT 20";
//
//        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
//             PreparedStatement statement = connection.prepareStatement(query);
//             ResultSet resultSet = statement.executeQuery()) {
//
//            while (resultSet.next()) {
//                Product product = new Product();
//                product.setId(resultSet.getString("sp_id"));
//                product.setName(resultSet.getString("sp_name"));
//                product.setPrice(resultSet.getInt("sp_price"));
//                product.setDescription(resultSet.getString("sp_description"));
//                product.setImage(resultSet.getString("sp_image"));
//                product.setStock(resultSet.getInt("sp_stock"));
//                // Note: For a complete implementation, you would also fetch the brand and category objects
//                // but for this demo, we're just retrieving the basic product information
//
//                products.add(product);
//            }
//
//        } catch (SQLException e) {
//            System.err.println("Error retrieving products: " + e.getMessage());
//            e.printStackTrace();
//        }
//
//        return products;
//    }
//}