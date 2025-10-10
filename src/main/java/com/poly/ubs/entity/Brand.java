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
@Table(name = "Hang")
public class Brand {
    @Id
    @Column(name = "hang_id", length = 10, nullable = false)
    private String id;
    
    @Column(name = "hang_name", length = 50, nullable = false, unique = true)
    private String name;
}