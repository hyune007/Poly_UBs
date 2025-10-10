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
        @Column(name = "sp_id", length = 8, nullable = false)
        private String id;

        @Column(name = "sp_name", length = 100, nullable = false)
        private String name;

        @Column(name = "sp_price", nullable = false)
        private int price;

        @Column(name = "sp_description", length = 100, nullable = false)
        private String description;

        @Column(name = "sp_image", length = 100, nullable = false)
        private String image;

        @Column(name = "sp_stock", nullable = false)
        private int stock;

        @ManyToOne
        @JoinColumn(name = "sp_brand_id", nullable = false)
        private Brand brand;

        @ManyToOne
        @JoinColumn(name = "sp_category_id", nullable = false)
        private Category category;
    }

