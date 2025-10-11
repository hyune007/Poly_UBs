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
@Table(name="Hang")
public class Brand {
    @Id
    private String hang_id;
    private String hang_name;
    @OneToMany(mappedBy = "brand")
    private List<Product> products;
}
