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
@Table(name="LoaiSanPham")
public class Category {
    @Id
    private String lsp_id;
    private String lsp_name;
    @OneToMany(mappedBy = "category")
    private List<Product> products;
}
