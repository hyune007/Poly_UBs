package com.poly.ubs.entity;

    import jakarta.persistence.*;
    import lombok.AllArgsConstructor;
    import lombok.Getter;
    import lombok.NoArgsConstructor;
    import lombok.Setter;

    import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity

    @Table(name = "SanPham")
    public class Product  {
        @Id
        @Column(name = "sp_id")
        private String id;

        @Column(name = "sp_name")
        private String name;

        @Column(name = "sp_price")
        private int price;

        @Column(name = "sp_description")
        private String description;

        @Column(name = "sp_image")
        private String image;

        @Column(name = "sp_stock")
        private int stock;

        @ManyToOne
        @JoinColumn(name = "sp_brand_id")
        private Brand brand;

        @ManyToOne
        @JoinColumn(name = "sp_category_id")
        private Category category;
    }

