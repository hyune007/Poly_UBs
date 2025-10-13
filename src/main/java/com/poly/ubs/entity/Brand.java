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
    @Column(name = "hang_id")
    private String id;
    
    @Column(name = "hang_name")
    private String name;
}