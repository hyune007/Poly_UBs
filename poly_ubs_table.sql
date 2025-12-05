CREATE DATABASE Poly_UBs;
USE Poly_UBs;
DROP DATABASE Poly_UBs;
create table KhachHang
(
    kh_id       VARCHAR(8)   NOT NULL,
    kh_name     VARCHAR(100) NOT NULL,
    kh_password VARCHAR(40)  NOT NULL,
    kh_phone    VARCHAR(15)  NOT NULL,
    kh_mail     VARCHAR(50)  NOT NULL,
    PRIMARY KEY (kh_id)
);
create table Role
(
    role_id   VARCHAR(20)  NOT NULL,
    role_name VARCHAR(100) NOT NULL,
    PRIMARY KEY (role_id)
);
CREATE TABLE GioHang
(
    gh_id       VARCHAR(8) NOT NULL,
    sp_quantity INT        NOT NULL,
    kh_id       VARCHAR(8) NOT NULL,
    sp_id       VARCHAR(8) NOT NULL,
    PRIMARY KEY (gh_id),
    FOREIGN KEY (kh_id) REFERENCES KhachHang (kh_id),
    FOREIGN KEY (sp_id) REFERENCES SanPham (sp_id),
    UNIQUE KEY unique_customer_product (kh_id, sp_id)
);
create table NhanVien
(
    nv_id       VARCHAR(8)   NOT NULL,
    nv_name     VARCHAR(100) NOT NULL,
    nv_password VARCHAR(40)  NOT NULL,
    nv_phone    VARCHAR(15)  NOT NULL,
    nv_mail     VARCHAR(50)  NOT NULL,
    nv_address  VARCHAR(100) NOT NULL,
    nv_role     BIT          NOT NULL,
    nv_birth    DATE         NOT NULL,
    PRIMARY KEY (nv_id)
);
create table LoaiSanPham
(
    lsp_id   VARCHAR(20)  NOT NULL,
    lsp_name VARCHAR(100) NOT NULL,
    PRIMARY KEY (lsp_id)
);
CREATE TABLE Hang
(
    hang_id   VARCHAR(10) NOT NULL,
    hang_name VARCHAR(50) NOT NULL UNIQUE,
    PRIMARY KEY (hang_id)
);
create table SanPham
(
    sp_id          VARCHAR(8)   NOT NULL,
    sp_name        VARCHAR(100) NOT NULL,
    sp_price       INT          NOT NULL,
    sp_description VARCHAR(100) NOT NULL,
    sp_image       VARCHAR(100) NOT NULL,
    sp_category_id VARCHAR(20)  NOT NULL,
    sp_stock       INT          NOT NULL,
    sp_brand_id    VARCHAR(50)  NOT NULL,
    PRIMARY KEY (sp_id),
    FOREIGN KEY (sp_brand_id) REFERENCES Hang (hang_id),
    FOREIGN KEY (sp_category_id) REFERENCES LoaiSanPham (lsp_id)
);
create table DiaChi
(
    dc_id   VARCHAR(8)  NOT NULL,
    kh_id   VARCHAR(8)  NOT NULL,
    dc_city VARCHAR(50) NOT NULL,
    dc_ward VARCHAR(50) NOT NULL,
    dc_detailaddress VARCHAR(255) NOT NULL,
    PRIMARY KEY (dc_id),
    FOREIGN KEY (kh_id) REFERENCES KhachHang (kh_id)
);
create table HoaDon
(
    hd_id     VARCHAR(8)  NOT NULL,
    hd_date   DATE        NOT NULL,
    hd_status VARCHAR(20) NOT NULL,
    kh_id     VARCHAR(8)  NOT NULL,
    nv_id     VARCHAR(8)  NOT NULL,
    dc_id     VARCHAR(8)  NOT NULL,
    PRIMARY KEY (hd_id),
    FOREIGN KEY (kh_id) REFERENCES KhachHang (kh_id),
    FOREIGN KEY (nv_id) REFERENCES NhanVien (nv_id),
    FOREIGN KEY (dc_id) REFERENCES DiaChi (dc_id)
);
create table ChiTietHoaDon
(
    hdct_id  VARCHAR(8) NOT NULL,
    hd_id    VARCHAR(8) NOT NULL,
    sp_id    VARCHAR(8) NOT NULL,
    quantity INT        NOT NULL,
    hdct_total INT        NOT NULL,
    PRIMARY KEY (hdct_id),
    FOREIGN KEY (hd_id) REFERENCES HoaDon (hd_id),
    FOREIGN KEY (sp_id) REFERENCES SanPham (sp_id)
);
CREATE TABLE GioHang
(
    gh_id       VARCHAR(8) NOT NULL,
    sp_quantity INT        NOT NULL,
    kh_id       VARCHAR(8) NOT NULL,
    sp_id       VARCHAR(8) NOT NULL,
    PRIMARY KEY (gh_id),
    FOREIGN KEY (kh_id) REFERENCES KhachHang (kh_id),
    FOREIGN KEY (sp_id) REFERENCES SanPham (sp_id),
    UNIQUE KEY unique_customer_product (kh_id, sp_id)
);
create table KhuyenMai
(
    km_id          VARCHAR(8)   NOT NULL,
    km_name        VARCHAR(100) NOT NULL,
    km_description VARCHAR(100) NOT NULL,
    km_percent     INT          NOT NULL,
    km_start_date  DATE         NOT NULL,
    km_end_date    DATE         NOT NULL,
    PRIMARY KEY (km_id)
);
create table NhapKho
(
    nk_id       VARCHAR(8) NOT NULL,
    nk_quantity INT        NOT NULL,
    sp_id       VARCHAR(8) NOT NULL,
    nk_date     DATE       NOT NULL,
    PRIMARY KEY (nk_id),
    FOREIGN KEY (sp_id) REFERENCES SanPham (sp_id)
);
create table DanhGia
(
    dg_id      VARCHAR(8)   NOT NULL,
    dg_content VARCHAR(100) NOT NULL,
    dg_rating  INT          NOT NULL,
    sp_id      VARCHAR(8)   NOT NULL,
    kh_id      VARCHAR(8)   NOT NULL,
    dg_date    DATE         NOT NULL,
    PRIMARY KEY (dg_id),
    FOREIGN KEY (sp_id) REFERENCES SanPham (sp_id),
    FOREIGN KEY (kh_id) REFERENCES KhachHang (kh_id)
);
CREATE TABLE IF NOT EXISTS password_reset_tokens
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    token       VARCHAR(255) NOT NULL UNIQUE,
    kh_id       VARCHAR(8)   NOT NULL,
    expiry_date DATETIME     NOT NULL,
    FOREIGN KEY (kh_id) REFERENCES KhachHang (kh_id) ON DELETE CASCADE
);

-- Index để tăng tốc độ tìm kiếm theo token
CREATE INDEX idx_token ON password_reset_tokens (token);
CREATE INDEX idx_expiry_date ON password_reset_tokens (expiry_date);

