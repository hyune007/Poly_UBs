package com.poly.ubs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Ứng dụng Spring Boot chính cho hệ thống PolyUBs
 */
@SpringBootApplication
public class PolyUBsApplication {

    /**
     * Phương thức main để khởi chạy ứng dụng Spring Boot
     * @param args các tham số dòng lệnh
     */
    public static void main(String[] args) {
        SpringApplication.run(PolyUBsApplication.class, args);
    }

}