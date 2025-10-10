package com.poly.ubs.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "LoaiSanPham")
public class Category {
    @Id
    @Column(name = "lsp_id")
    private String id;
    
    @Column(name = "lsp_name")
    private String name;
}