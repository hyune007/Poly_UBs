package com.poly.ubs.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
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
@Table(name="KhachHang")
public class Customer {
    @Id
    private String kh_id;
    private String kh_name;
    private String kh_password;
    private String kh_phone;
    private String kh_mail;
    @OneToMany(mappedBy = "customer")
    private List<Address> addresses;
    @OneToMany(mappedBy = "customer")
    private List<Bill> bills;
    @OneToMany(mappedBy = "customer")
    private List<Rating> ratings;
    @OneToMany(mappedBy = "customer")
    private List<ShoppingCart> shoppingCarts;
}
