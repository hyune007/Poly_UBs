package com.poly.ubs.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

@Getter
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String brand;
    private double price;
    private String description;
    private String imageUrl;

    // 👇 Thêm trường category
    private String category;

    // ==== SETTERS ====
    public void setId(Long id) { this.id = id; }

    public void setName(String name) { this.name = name; }

    public void setBrand(String brand) { this.brand = brand; }

    public void setPrice(double price) { this.price = price; }

    public void setDescription(String description) { this.description = description; }

    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    // 👇 Setter cho category
    public void setCategory(String category) { this.category = category; }
}
