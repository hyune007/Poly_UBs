package com.poly.ubs.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="SanPham")
public class Product {
    @Id
    private String sp_id;
    private String sp_name;
    private Integer sp_price;
    private String sp_description;
    private String sp_image;
    private Integer sp_stock;
    @ManyToOne
    @JoinColumn(name="sp_category_id")
    private Category category;
    @ManyToOne
    @JoinColumn(name="sp_brand_id")
    private Brand brand;
    @OneToMany(mappedBy = "product")
    private List<DetailBill> detailBills;
    @OneToMany(mappedBy = "product")
    private List<GoodsImport> goodsImports;
    @OneToMany(mappedBy = "product")
    private List<Promotion> promotions;
    @OneToMany(mappedBy = "product")
    private List<Rating> ratings;
    @OneToMany(mappedBy = "product")
    private List<ShoppingCart> shoppingCarts;
}
