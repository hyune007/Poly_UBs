CREATE DATABASE Poly_UBs;
USE Poly_UBs;

create table KhachHang (
  kh_id VARCHAR(8) NOT NULL,
  kh_name VARCHAR(100) NOT NULL,
  kh_password VARCHAR(40) NOT NULL,
  kh_phone VARCHAR(15) NOT NULL,
  kh_mail VARCHAR(50) NOT NULL,
  PRIMARY KEY (kh_id)
);

create table NhanVien (
  nv_id VARCHAR(8) NOT NULL,
  nv_name VARCHAR(100) NOT NULL,
  nv_password VARCHAR(40) NOT NULL,
  nv_phone VARCHAR(15) NOT NULL,
  nv_mail VARCHAR(50) NOT NULL,
  nv_address VARCHAR(100) NOT NULL,
    nv_role VARCHAR(20) NOT NULL,
    nv_birth DATE NOT NULL,
  PRIMARY KEY (nv_id)
);
create table LoaiSanPham(
    lsp_id VARCHAR(20) NOT NULL,
    lsp_name VARCHAR(100) NOT NULL,
    PRIMARY KEY (lsp_id)
);
create table SanPham(
    sp_id VARCHAR(8) NOT NULL,
    sp_name VARCHAR(100) NOT NULL,
    sp_price INT NOT NULL,
    sp_description VARCHAR(100) NOT NULL,
    sp_image VARCHAR(100) NOT NULL,
    sp_category_id VARCHAR(20) NOT NULL,
    sp_stock INT NOT NULL,
    sp_brand VARCHAR(50) NOT NULL,
    PRIMARY KEY (sp_id),
    FOREIGN KEY (sp_category_id) REFERENCES LoaiSanPham(lsp_id)
)
;
create table DiaChi(
    dc_id VARCHAR(8) NOT NULL,
    kh_id VARCHAR(8) NOT NULL,
    dc_city VARCHAR(50) NOT NULL,
    dc_ward VARCHAR(50) NOT NULL,
    PRIMARY KEY (dc_id),
    FOREIGN KEY (kh_id) REFERENCES KhachHang(kh_id)
);
create table HoaDon(
    hd_id VARCHAR(8) NOT NULL,
    hd_date DATE NOT NULL,
    hd_status VARCHAR(20) NOT NULL,
    kh_id VARCHAR(8) NOT NULL,
    nv_id VARCHAR(8) NOT NULL,
    dc_id VARCHAR(8) NOT NULL,
    PRIMARY KEY (hd_id),
    FOREIGN KEY (kh_id) REFERENCES KhachHang(kh_id),
    FOREIGN KEY (nv_id) REFERENCES NhanVien(nv_id),
    FOREIGN KEY (dc_id) REFERENCES DiaChi(dc_id)
);
create table ChiTietHoaDon(
    hdct_id VARCHAR(8) NOT NULL,
    hd_id VARCHAR(8) NOT NULL,
    sp_id VARCHAR(8) NOT NULL,
    quantity INT NOT NULL,
    PRIMARY KEY (hdct_id),
    FOREIGN KEY (hd_id) REFERENCES HoaDon(hd_id),
    FOREIGN KEY (sp_id) REFERENCES SanPham(sp_id)
);
create table GioHang(
    gh_id VARCHAR(8) NOT NULL,
    sp_quantity INT NOT NULL,
    kh_id VARCHAR(8) NOT NULL unique ,
    sp_id VARCHAR(8) NOT NULL,
    PRIMARY KEY (gh_id),
    FOREIGN KEY (kh_id) REFERENCES KhachHang(kh_id),
    FOREIGN KEY (sp_id) REFERENCES SanPham(sp_id)
);
create table KhuyenMai(
    km_id VARCHAR(8) NOT NULL,
    km_name VARCHAR(100) NOT NULL,
    km_description VARCHAR(100) NOT NULL,
    km_percent INT NOT NULL,
    km_start_date DATE NOT NULL,
    km_end_date DATE NOT NULL,
    PRIMARY KEY (km_id)
);
create table NhapKho(
    nk_id VARCHAR(8) NOT NULL,
    nk_quantity INT NOT NULL,
    sp_id VARCHAR(8) NOT NULL,
    nk_date DATE NOT NULL,
    PRIMARY KEY (nk_id),
    FOREIGN KEY (sp_id) REFERENCES SanPham(sp_id)
);)
create table DanhGia(
    dg_id VARCHAR(8) NOT NULL,
    dg_content VARCHAR(100) NOT NULL,
    dg_rating INT NOT NULL,
    sp_id VARCHAR(8) NOT NULL,
    kh_id VARCHAR(8) NOT NULL,
    dg_date DATE NOT NULL,
    PRIMARY KEY (dg_id),
    FOREIGN KEY (sp_id) REFERENCES SanPham(sp_id),
    FOREIGN KEY (kh_id) REFERENCES KhachHang(kh_id)
);


drop table DanhGia;
drop table NhapKho;
drop table KhuyenMai;
drop table GioHang;
drop table ChiTietHoaDon;
drop table HoaDon;
drop table DiaChi;
drop table SanPham;
drop table NhanVien;
drop table KhachHang;

-- Sample Data

-- LoaiSanPham (10 categories)
INSERT INTO LoaiSanPham (lsp_id, lsp_name) VALUES
('LSP01', 'Điện thoại'),
('LSP02', 'Laptop'),
('LSP03', 'Máy tính bảng'),
('LSP04', 'Đồng hồ thông minh'),
('LSP05', 'Tai nghe'),
('LSP06', 'Bàn phím'),
('LSP07', 'Chuột'),
('LSP08', 'Màn hình'),
('LSP09', 'Loa'),
('LSP10', 'Phụ kiện khác');

-- SanPham (200 products)
-- Điện thoại (20)
INSERT INTO SanPham (sp_id, sp_name, sp_price, sp_description, sp_image, sp_category_id, sp_stock, sp_brand) VALUES
('SP001', 'iPhone 14 Pro Max', 25000000, 'Điện thoại cao cấp từ Apple', 'iphone14.jpg', 'LSP01', 50, 'Apple'),
('SP002', 'Samsung Galaxy S23 Ultra', 23000000, 'Flagship của Samsung', 's23ultra.jpg', 'LSP01', 40, 'Samsung'),
('SP003', 'Google Pixel 7 Pro', 20000000, 'Chụp ảnh đỉnh cao', 'pixel7pro.jpg', 'LSP01', 30, 'Google'),
('SP004', 'Xiaomi 13 Pro', 18000000, 'Hiệu năng mạnh mẽ', 'xiaomi13pro.jpg', 'LSP01', 60, 'Xiaomi'),
('SP005', 'Oppo Find X6 Pro', 19000000, 'Thiết kế độc đáo', 'oppofindx6.jpg', 'LSP01', 25, 'Oppo'),
('SP006', 'iPhone 13', 17000000, 'Giá tốt, hiệu năng ổn', 'iphone13.jpg', 'LSP01', 100, 'Apple'),
('SP007', 'Samsung Galaxy Z Fold 5', 40000000, 'Điện thoại gập', 'zfold5.jpg', 'LSP01', 20, 'Samsung'),
('SP008', 'OnePlus 11', 16000000, 'Sạc siêu nhanh', 'oneplus11.jpg', 'LSP01', 35, 'OnePlus'),
('SP009', 'Asus ROG Phone 7', 22000000, 'Chuyên game', 'rogphone7.jpg', 'LSP01', 15, 'Asus'),
('SP010', 'Sony Xperia 1 V', 28000000, 'Màn hình 4K', 'xperia1v.jpg', 'LSP01', 10, 'Sony'),
('SP011', 'Realme GT 3', 15000000, 'Giá rẻ, cấu hình cao', 'realmegt3.jpg', 'LSP01', 50, 'Realme'),
('SP012', 'Vivo X90 Pro', 21000000, 'Camera Zeiss', 'vivox90pro.jpg', 'LSP01', 20, 'Vivo'),
('SP013', 'Nokia G22', 4000000, 'Dễ dàng sửa chữa', 'nokiag22.jpg', 'LSP01', 80, 'Nokia'),
('SP014', 'Samsung Galaxy A54', 9000000, 'Tầm trung đáng mua', 'galaxya54.jpg', 'LSP01', 120, 'Samsung'),
('SP015', 'Xiaomi Redmi Note 12 Pro', 7000000, 'Pin trâu, sạc nhanh', 'redminote12.jpg', 'LSP01', 150, 'Xiaomi'),
('SP016', 'iPhone SE 2022', 10000000, 'Nhỏ gọn, mạnh mẽ', 'iphonese2022.jpg', 'LSP01', 70, 'Apple'),
('SP017', 'Oppo Reno8 T', 8000000, 'Thiết kế trẻ trung', 'renot8.jpg', 'LSP01', 90, 'Oppo'),
('SP018', 'Google Pixel 6a', 12000000, 'Trải nghiệm Android thuần', 'pixel6a.jpg', 'LSP01', 40, 'Google'),
('SP019', 'Samsung Galaxy S22', 15000000, 'Flagship năm ngoái', 's22.jpg', 'LSP01', 60, 'Samsung'),
('SP020', 'Xiaomi 12T', 11000000, 'Hiệu năng tốt trong tầm giá', 'xiaomi12t.jpg', 'LSP01', 75, 'Xiaomi');

-- Laptop (20)
INSERT INTO SanPham (sp_id, sp_name, sp_price, sp_description, sp_image, sp_category_id, sp_stock, sp_brand) VALUES
('SP021', 'MacBook Pro 16 M2', 60000000, 'Laptop cho dân chuyên nghiệp', 'macpro16.jpg', 'LSP02', 30, 'Apple'),
('SP022', 'Dell XPS 15', 45000000, 'Thiết kế đẹp, màn hình vô cực', 'xps15.jpg', 'LSP02', 40, 'Dell'),
('SP023', 'HP Spectre x360', 38000000, 'Laptop 2-in-1 cao cấp', 'spectrex360.jpg', 'LSP02', 25, 'HP'),
('SP024', 'Lenovo ThinkPad X1 Carbon', 42000000, 'Bền bỉ, bàn phím tốt', 'thinkpadx1.jpg', 'LSP02', 50, 'Lenovo'),
('SP025', 'Asus ROG Zephyrus G14', 35000000, 'Laptop gaming nhỏ gọn', 'zephyrusg14.jpg', 'LSP02', 35, 'Asus'),
('SP026', 'MacBook Air M2', 28000000, 'Mỏng nhẹ, pin trâu', 'macairm2.jpg', 'LSP02', 80, 'Apple'),
('SP027', 'Razer Blade 15', 55000000, 'Build quality cao cấp', 'razerblade15.jpg', 'LSP02', 20, 'Razer'),
('SP028', 'LG Gram 17', 33000000, 'Siêu nhẹ', 'lggram17.jpg', 'LSP02', 45, 'LG'),
('SP029', 'Acer Swift 5', 25000000, 'Thiết kế thanh lịch', 'acerswift5.jpg', 'LSP02', 60, 'Acer'),
('SP030', 'Microsoft Surface Laptop 5', 30000000, 'Trải nghiệm Windows tốt nhất', 'surface5.jpg', 'LSP02', 30, 'Microsoft'),
('SP031', 'Dell Inspiron 15', 18000000, 'Laptop phổ thông', 'inspiron15.jpg', 'LSP02', 100, 'Dell'),
('SP032', 'HP Pavilion Aero 13', 22000000, 'Mỏng nhẹ giá tốt', 'pavilion_aero.jpg', 'LSP02', 70, 'HP'),
('SP033', 'Lenovo IdeaPad Slim 5', 19000000, 'Cấu hình tốt trong tầm giá', 'ideapad5.jpg', 'LSP02', 90, 'Lenovo'),
('SP034', 'Asus TUF Gaming F15', 26000000, 'Laptop gaming giá rẻ', 'tufgamingf15.jpg', 'LSP02', 80, 'Asus'),
('SP035', 'MSI Katana GF66', 24000000, 'Thiết kế gaming', 'msikatana.jpg', 'LSP02', 65, 'MSI'),
('SP036', 'Gigabyte Aorus 15', 40000000, 'Màn hình tần số quét cao', 'aorus15.jpg', 'LSP02', 25, 'Gigabyte'),
('SP037', 'Acer Nitro 5', 23000000, 'Tản nhiệt tốt', 'acernitro5.jpg', 'LSP02', 110, 'Acer'),
('SP038', 'Dell Alienware M15', 65000000, 'Cỗ máy gaming đỉnh cao', 'alienwarem15.jpg', 'LSP02', 15, 'Dell'),
('SP039', 'HP Omen 16', 39000000, 'Thiết kế tối giản', 'hpomen16.jpg', 'LSP02', 30, 'HP'),
('SP040', 'Lenovo Legion 5', 32000000, 'Hiệu năng/giá thành tốt', 'legion5.jpg', 'LSP02', 90, 'Lenovo');

-- Máy tính bảng (20)
INSERT INTO SanPham (sp_id, sp_name, sp_price, sp_description, sp_image, sp_category_id, sp_stock, sp_brand) VALUES
('SP041', 'iPad Pro M2 12.9', 32000000, 'Máy tính bảng mạnh nhất', 'ipadpro129.jpg', 'LSP03', 40, 'Apple'),
('SP042', 'Samsung Galaxy Tab S8 Ultra', 26000000, 'Màn hình siêu lớn', 'tabs8ultra.jpg', 'LSP03', 30, 'Samsung'),
('SP043', 'iPad Air 5', 16000000, 'Hiệu năng mạnh, giá tốt', 'ipadair5.jpg', 'LSP03', 70, 'Apple'),
('SP044', 'Xiaomi Pad 6', 9000000, 'Giải trí đỉnh cao', 'xiaomipad6.jpg', 'LSP03', 100, 'Xiaomi'),
('SP045', 'Lenovo Tab P11 Pro Gen 2', 11000000, 'Màn hình OLED đẹp', 'lenovotabp11.jpg', 'LSP03', 50, 'Lenovo'),
('SP046', 'Microsoft Surface Pro 9', 28000000, 'Thay thế laptop', 'surfacepro9.jpg', 'LSP03', 25, 'Microsoft'),
('SP047', 'iPad Gen 10', 12000000, 'Thiết kế mới', 'ipad10.jpg', 'LSP03', 90, 'Apple'),
('SP048', 'Samsung Galaxy Tab S7 FE', 10000000, 'Bút S Pen đi kèm', 'tabs7fe.jpg', 'LSP03', 80, 'Samsung'),
('SP049', 'Oppo Pad Air', 6000000, 'Mỏng nhẹ, giá rẻ', 'oppopadair.jpg', 'LSP03', 120, 'Oppo'),
('SP050', 'Nokia T21', 5000000, 'Bền bỉ, pin lâu', 'nokiat21.jpg', 'LSP03', 60, 'Nokia'),
('SP051', 'Amazon Fire HD 10', 4000000, 'Giá rẻ cho giải trí', 'firehd10.jpg', 'LSP03', 150, 'Amazon'),
('SP052', 'Realme Pad', 5500000, 'Màn hình 2K', 'realmepad.jpg', 'LSP03', 75, 'Realme'),
('SP053', 'Huawei MatePad Pro', 15000000, 'Hệ sinh thái Huawei', 'matepadpro.jpg', 'LSP03', 35, 'Huawei'),
('SP054', 'iPad Mini 6', 14000000, 'Nhỏ gọn, mạnh mẽ', 'ipadmini6.jpg', 'LSP03', 55, 'Apple'),
('SP055', 'Samsung Galaxy Tab A8', 7000000, 'Giá rẻ, màn hình lớn', 'taba8.jpg', 'LSP03', 110, 'Samsung'),
('SP056', 'Lenovo Yoga Tab 13', 18000000, 'Có cổng micro-HDMI', 'yogatab13.jpg', 'LSP03', 20, 'Lenovo'),
('SP057', 'Xiaomi Redmi Pad', 5000000, 'Màn 90Hz giá rẻ', 'redmipad.jpg', 'LSP03', 130, 'Xiaomi'),
('SP058', 'Surface Go 3', 13000000, 'Nhỏ gọn cho công việc', 'surfacego3.jpg', 'LSP03', 40, 'Microsoft'),
('SP059', 'TCL NxtPaper 11', 7500000, 'Màn hình giả giấy', 'tcl_nxtpaper.jpg', 'LSP03', 30, 'TCL'),
('SP060', 'Alldocube iPlay 50 Pro', 4500000, 'Giá siêu rẻ', 'iplay50.jpg', 'LSP03', 100, 'Alldocube');

-- Đồng hồ thông minh (20)
INSERT INTO SanPham (sp_id, sp_name, sp_price, sp_description, sp_image, sp_category_id, sp_stock, sp_brand) VALUES
('SP061', 'Apple Watch Ultra', 22000000, 'Bền bỉ, pin trâu', 'aw_ultra.jpg', 'LSP04', 50, 'Apple'),
('SP062', 'Samsung Galaxy Watch 5 Pro', 11000000, 'Thiết kế thể thao', 'gw5pro.jpg', 'LSP04', 60, 'Samsung'),
('SP063', 'Garmin Fenix 7X', 25000000, 'Đỉnh cao cho dân thể thao', 'fenix7x.jpg', 'LSP04', 30, 'Garmin'),
('SP064', 'Apple Watch Series 8', 12000000, 'Theo dõi sức khỏe toàn diện', 'aw_s8.jpg', 'LSP04', 100, 'Apple'),
('SP065', 'Huawei Watch GT 3 Pro', 9000000, 'Thiết kế sang trọng', 'gt3pro.jpg', 'LSP04', 70, 'Huawei'),
('SP066', 'Amazfit GTR 4', 5000000, 'Giá tốt, nhiều tính năng', 'gtr4.jpg', 'LSP04', 120, 'Amazfit'),
('SP067', 'Xiaomi Watch S1 Pro', 6000000, 'Thiết kế cổ điển', 'watchs1pro.jpg', 'LSP04', 80, 'Xiaomi'),
('SP068', 'Google Pixel Watch', 8000000, 'WearOS mượt mà', 'pixelwatch.jpg', 'LSP04', 40, 'Google'),
('SP069', 'Fitbit Sense 2', 7000000, 'Theo dõi stress', 'sense2.jpg', 'LSP04', 50, 'Fitbit'),
('SP070', 'Coros Apex 2 Pro', 15000000, 'Chuyên cho chạy bộ', 'apex2pro.jpg', 'LSP04', 25, 'Coros'),
('SP071', 'Samsung Galaxy Watch 5', 7000000, 'Nhiều phiên bản', 'gw5.jpg', 'LSP04', 150, 'Samsung'),
('SP072', 'Apple Watch SE 2022', 7500000, 'Giá rẻ nhất của Apple', 'aw_se.jpg', 'LSP04', 130, 'Apple'),
('SP073', 'Garmin Forerunner 955', 14000000, 'Hỗ trợ triathlon', 'forerunner955.jpg', 'LSP04', 35, 'Garmin'),
('SP074', 'Amazfit T-Rex 2', 5500000, 'Siêu bền', 'trex2.jpg', 'LSP04', 90, 'Amazfit'),
('SP075', 'Huawei Band 7', 1200000, 'Vòng tay thông minh', 'band7.jpg', 'LSP04', 200, 'Huawei'),
('SP076', 'Xiaomi Mi Band 8', 1000000, 'Vòng tay quốc dân', 'miband8.jpg', 'LSP04', 300, 'Xiaomi'),
('SP077', 'Garmin Venu 2 Plus', 10000000, 'Nghe gọi trên đồng hồ', 'venu2plus.jpg', 'LSP04', 60, 'Garmin'),
('SP078', 'Mobvoi TicWatch Pro 5', 9000000, 'Hai màn hình độc đáo', 'ticwatchpro5.jpg', 'LSP04', 30, 'Mobvoi'),
('SP079', 'Suunto 9 Peak Pro', 18000000, 'Thiết kế từ Phần Lan', 'suunto9.jpg', 'LSP04', 20, 'Suunto'),
('SP080', 'Oppo Watch 3 Pro', 8500000, 'Chip Snapdragon W5', 'oppowatch3.jpg', 'LSP04', 45, 'Oppo');

-- Tai nghe (20)
INSERT INTO SanPham (sp_id, sp_name, sp_price, sp_description, sp_image, sp_category_id, sp_stock, sp_brand) VALUES
('SP081', 'AirPods Pro 2', 6000000, 'Chống ồn đỉnh cao', 'airpodspro2.jpg', 'LSP05', 150, 'Apple'),
('SP082', 'Sony WF-1000XM5', 7000000, 'Chất âm tuyệt vời', 'wf1000xm5.jpg', 'LSP05', 100, 'Sony'),
('SP083', 'Bose QuietComfort Earbuds II', 6500000, 'Chống ồn chủ động tốt nhất', 'boseqc2.jpg', 'LSP05', 80, 'Bose'),
('SP084', 'Samsung Galaxy Buds 2 Pro', 4500000, 'Âm thanh Hi-Fi 24-bit', 'buds2pro.jpg', 'LSP05', 120, 'Samsung'),
('SP085', 'Sennheiser Momentum True Wireless 3', 7500000, 'Chất âm audiophile', 'sennheiser_mtw3.jpg', 'LSP05', 60, 'Sennheiser'),
('SP086', 'Jabra Elite 7 Pro', 5000000, 'Đàm thoại tốt', 'jabra_elite7.jpg', 'LSP05', 90, 'Jabra'),
('SP087', 'Google Pixel Buds Pro', 4800000, 'Tích hợp sâu với hệ sinh thái Google', 'pixelbudspro.jpg', 'LSP05', 70, 'Google'),
('SP088', 'Anker Soundcore Liberty 4', 3500000, 'Giá tốt, nhiều tính năng', 'liberty4.jpg', 'LSP05', 200, 'Anker'),
('SP089', 'AirPods 3', 4500000, 'Thiết kế mới, âm thanh không gian', 'airpods3.jpg', 'LSP05', 180, 'Apple'),
('SP090', 'Sony WH-1000XM5', 9000000, 'Tai nghe over-ear chống ồn', 'wh1000xm5.jpg', 'LSP05', 90, 'Sony'),
('SP091', 'Beats Fit Pro', 5200000, 'Thiết kế cho thể thao', 'beatsfitpro.jpg', 'LSP05', 110, 'Beats'),
('SP092', 'Nothing Ear (2)', 2800000, 'Thiết kế trong suốt độc đáo', 'nothingear2.jpg', 'LSP05', 130, 'Nothing'),
('SP093', 'Xiaomi Buds 4 Pro', 3800000, 'Hỗ trợ LDAC', 'xiaomibuds4.jpg', 'LSP05', 100, 'Xiaomi'),
('SP094', 'Huawei FreeBuds Pro 2', 4300000, 'Hợp tác cùng Devialet', 'freebudspro2.jpg', 'LSP05', 80, 'Huawei'),
('SP095', 'Audio-Technica ATH-M50xBT2', 5500000, 'Tai nghe kiểm âm không dây', 'm50xbt2.jpg', 'LSP05', 50, 'Audio-Technica'),
('SP096', 'Marshall Major IV', 3800000, 'Thiết kế hoài cổ, pin 80 giờ', 'major4.jpg', 'LSP05', 120, 'Marshall'),
('SP097', 'Shure AONIC 50 Gen 2', 10000000, 'Chất lượng phòng thu', 'aonic50.jpg', 'LSP05', 30, 'Shure'),
('SP098', 'Bowers & Wilkins Pi7 S2', 11000000, 'Âm thanh Hi-End', 'pi7s2.jpg', 'LSP05', 25, 'B&W'),
('SP099', 'Edifier NeoBuds Pro', 2500000, 'Chứng nhận Hi-Res giá rẻ', 'neobudspro.jpg', 'LSP05', 150, 'Edifier'),
('SP100', '1MORE EVO', 4000000, 'Âm thanh chi tiết', '1more_evo.jpg', 'LSP05', 70, '1MORE');

-- Bàn phím (20)
INSERT INTO SanPham (sp_id, sp_name, sp_price, sp_description, sp_image, sp_category_id, sp_stock, sp_brand) VALUES
('SP101', 'Logitech MX Keys S', 2800000, 'Bàn phím văn phòng tốt nhất', 'mxkeys_s.jpg', 'LSP06', 100, 'Logitech'),
('SP102', 'Keychron Q1 Pro', 5000000, 'Bàn phím cơ custom', 'keychron_q1.jpg', 'LSP06', 80, 'Keychron'),
('SP103', 'Razer BlackWidow V4 Pro', 5500000, 'Bàn phím cơ gaming', 'blackwidow_v4.jpg', 'LSP06', 60, 'Razer'),
('SP104', 'Corsair K100 RGB', 6000000, 'Bàn phím quang-cơ', 'corsair_k100.jpg', 'LSP06', 50, 'Corsair'),
('SP105', 'SteelSeries Apex Pro TKL', 4800000, 'Switch có thể điều chỉnh', 'apex_pro.jpg', 'LSP06', 70, 'SteelSeries'),
('SP106', 'Apple Magic Keyboard', 3000000, 'Dành cho hệ sinh thái Apple', 'magic_keyboard.jpg', 'LSP06', 90, 'Apple'),
('SP107', 'Filco Majestouch 2', 3500000, 'Build quality Nhật Bản', 'filco_majestouch.jpg', 'LSP06', 40, 'Filco'),
('SP108', 'Leopold FC900R', 3800000, 'Keycap PBT chất lượng cao', 'leopold_fc900r.jpg', 'LSP06', 45, 'Leopold'),
('SP109', 'Akko 3087', 1800000, 'Bàn phím cơ giá rẻ', 'akko_3087.jpg', 'LSP06', 150, 'Akko'),
('SP110', 'NuPhy Air75', 3200000, 'Bàn phím cơ low-profile', 'nuphy_air75.jpg', 'LSP06', 65, 'NuPhy'),
('SP111', 'Logitech G913 TKL', 4500000, 'Bàn phím cơ không dây low-profile', 'g913_tkl.jpg', 'LSP06', 75, 'Logitech'),
('SP112', 'Asus ROG Azoth', 6200000, 'Bàn phím cơ custom từ Asus', 'rog_azoth.jpg', 'LSP06', 35, 'Asus'),
('SP113', 'Microsoft Sculpt Ergonomic', 2000000, 'Bàn phím công thái học', 'ms_sculpt.jpg', 'LSP06', 80, 'Microsoft'),
('SP114', 'Razer Huntsman Mini', 3000000, 'Bàn phím 60% switch quang', 'huntsman_mini.jpg', 'LSP06', 100, 'Razer'),
('SP115', 'HyperX Alloy Origins Core', 2500000, 'Switch HyperX Red', 'alloy_origins.jpg', 'LSP06', 120, 'HyperX'),
('SP116', 'Ducky One 3', 3300000, 'Cảm giác gõ tốt', 'ducky_one3.jpg', 'LSP06', 60, 'Ducky'),
('SP117', 'Glorious GMMK Pro', 4200000, 'Bàn phím barebone custom', 'gmmk_pro.jpg', 'LSP06', 55, 'Glorious'),
('SP118', 'IQUNIX F97', 4800000, 'Thiết kế độc đáo', 'iqunix_f97.jpg', 'LSP06', 30, 'IQUNIX'),
('SP119', 'Royal Kludge RK61', 1200000, 'Bàn phím cơ không dây giá rẻ', 'rk61.jpg', 'LSP06', 200, 'Royal Kludge'),
('SP120', 'HHKB Professional Hybrid', 7000000, 'Switch Topre', 'hhkb.jpg', 'LSP06', 20, 'HHKB');

-- Chuột (20)
INSERT INTO SanPham (sp_id, sp_name, sp_price, sp_description, sp_image, sp_category_id, sp_stock, sp_brand) VALUES
('SP121', 'Logitech MX Master 3S', 2600000, 'Chu��t văn phòng tốt nhất', 'mx_master3s.jpg', 'LSP07', 120, 'Logitech'),
('SP122', 'Razer DeathAdder V3 Pro', 3500000, 'Chuột gaming công thái học', 'deathadder_v3.jpg', 'LSP07', 80, 'Razer'),
('SP123', 'Logitech G Pro X Superlight', 3200000, 'Chuột gaming siêu nhẹ', 'gpro_x.jpg', 'LSP07', 100, 'Logitech'),
('SP124', 'SteelSeries Aerox 5 Wireless', 3000000, 'Chuột gaming lỗ siêu nhẹ', 'aerox5.jpg', 'LSP07', 70, 'SteelSeries'),
('SP125', 'Razer Viper V2 Pro', 3400000, 'Chuột đối xứng siêu nhẹ', 'viper_v2.jpg', 'LSP07', 75, 'Razer'),
('SP126', 'Apple Magic Mouse', 2200000, 'Thiết kế tối giản', 'magic_mouse.jpg', 'LSP07', 90, 'Apple'),
('SP127', 'Pulsar X2', 2400000, 'Chuột gaming được ưa chuộng', 'pulsar_x2.jpg', 'LSP07', 60, 'Pulsar'),
('SP128', 'Lamzu Atlantis', 2500000, 'Chuột gaming đối xứng', 'lamzu_atlantis.jpg', 'LSP07', 55, 'Lamzu'),
('SP129', 'Corsair Dark Core RGB Pro', 2800000, 'Nhiều tính năng', 'dark_core.jpg', 'LSP07', 50, 'Corsair'),
('SP130', 'Asus ROG Gladius III Wireless', 3100000, 'Hotswap switch', 'gladius3.jpg', 'LSP07', 45, 'Asus'),
('SP131', 'Logitech MX Anywhere 3S', 2000000, 'Chuột di động', 'mx_anywhere3s.jpg', 'LSP07', 110, 'Logitech'),
('SP132', 'Microsoft Sculpt Ergonomic Mouse', 1500000, 'Chuột công thái học', 'ms_sculpt_mouse.jpg', 'LSP07', 80, 'Microsoft'),
('SP133', 'Razer Basilisk V3 Pro', 4000000, 'Cuộn vô cực, sạc không dây', 'basilisk_v3.jpg', 'LSP07', 65, 'Razer'),
('SP134', 'HyperX Pulsefire Haste', 1200000, 'Chuột lỗ giá rẻ', 'pulsefire_haste.jpg', 'LSP07', 150, 'HyperX'),
('SP135', 'Glorious Model O Wireless', 2200000, 'Chuột lỗ không dây', 'model_o.jpg', 'LSP07', 90, 'Glorious'),
('SP136', 'Zowie EC2-C', 1800000, 'Chuột cho game thủ CS:GO', 'zowie_ec2.jpg', 'LSP07', 70, 'Zowie'),
('SP137', 'Endgame Gear XM1r', 1600000, 'Form chuột claw grip', 'xm1r.jpg', 'LSP07', 80, 'Endgame Gear'),
('SP138', 'Cooler Master MM712', 1900000, 'Nhẹ, không lỗ', 'mm712.jpg', 'LSP07', 60, 'Cooler Master'),
('SP139', 'Logitech G502 X Plus', 3600000, 'Phiên bản mới của G502', 'g502x.jpg', 'LSP07', 85, 'Logitech'),
('SP140', 'Roccat Kone Pro Air', 2700000, 'Thiết kế công thái học độc đáo', 'kone_pro_air.jpg', 'LSP07', 40, 'Roccat');

-- Màn hình (20)
INSERT INTO SanPham (sp_id, sp_name, sp_price, sp_description, sp_image, sp_category_id, sp_stock, sp_brand) VALUES
('SP141', 'Dell UltraSharp U2723QE', 15000000, 'Màn hình 4K cho đồ họa', 'u2723qe.jpg', 'LSP08', 50, 'Dell'),
('SP142', 'LG UltraGear 27GP950-B', 20000000, 'Màn hình gaming 4K 144Hz', '27gp950.jpg', 'LSP08', 30, 'LG'),
('SP143', 'Samsung Odyssey Neo G9', 45000000, 'Màn hình siêu cong 49 inch', 'neo_g9.jpg', 'LSP08', 15, 'Samsung'),
('SP144', 'Apple Studio Display', 40000000, 'Màn hình 5K cho Mac', 'studio_display.jpg', 'LSP08', 20, 'Apple'),
('SP145', 'Asus ProArt PA279CV', 13000000, 'Màn hình đồ họa giá tốt', 'pa279cv.jpg', 'LSP08', 40, 'Asus'),
('SP146', 'Gigabyte M28U', 16000000, 'Màn hình gaming 4K có KVM', 'm28u.jpg', 'LSP08', 35, 'Gigabyte'),
('SP147', 'BenQ MOBIUZ EX2710Q', 10000000, 'Màn hình gaming 2K 165Hz', 'ex2710q.jpg', 'LSP08', 60, 'BenQ'),
('SP148', 'ViewSonic VX2758-2KP-MHD', 7000000, 'Màn hình 2K giá rẻ', 'vx2758.jpg', 'LSP08', 100, 'ViewSonic'),
('SP149', 'Xiaomi Mi Curved Gaming Monitor 34', 9000000, 'Màn hình ultrawide giá tốt', 'mi_curved34.jpg', 'LSP08', 80, 'Xiaomi'),
('SP150', 'LG 27UP850N-W', 11000000, 'Màn hình 4K, có USB-C', '27up850n.jpg', 'LSP08', 70, 'LG'),
('SP151', 'Dell S2721DGF', 9500000, 'Màn gaming 2K Dell', 's2721dgf.jpg', 'LSP08', 80, 'Dell'),
('SP152', 'Alienware AW3423DWF', 30000000, 'Màn hình QD-OLED', 'aw3423dwf.jpg', 'LSP08', 25, 'Alienware'),
('SP153', 'HP Omen 27c', 12000000, 'Màn hình gaming cong', 'omen27c.jpg', 'LSP08', 45, 'HP'),
('SP154', 'Acer Predator XB273U', 14000000, 'Màn hình gaming 2K', 'xb273u.jpg', 'LSP08', 50, 'Acer'),
('SP155', 'MSI Optix MAG274QRF-QD', 12500000, 'Màu sắc chấm lượng tử', 'mag274qrf.jpg', 'LSP08', 55, 'MSI'),
('SP156', 'Samsung Odyssey G7', 17000000, 'Màn hình cong 1000R', 'odyssey_g7.jpg', 'LSP08', 40, 'Samsung'),
('SP157', 'LG DualUp 28MQ780-B', 18000000, 'Màn hình tỷ lệ 16:18 độc đáo', 'dualup.jpg', 'LSP08', 20, 'LG'),
('SP158', 'Lenovo Legion Y27q-20', 11500000, 'Màn hình gaming Lenovo', 'y27q20.jpg', 'LSP08', 60, 'Lenovo'),
('SP159', 'Corsair Xeneon 32UHD144', 22000000, 'Màn hình gaming 4K Corsair', 'xeneon32.jpg', 'LSP08', 25, 'Corsair'),
('SP160', 'Razer Raptor 27', 19000000, 'Thiết kế đậm chất Razer', 'raptor27.jpg', 'LSP08', 30, 'Razer');

-- Loa (20)
INSERT INTO SanPham (sp_id, sp_name, sp_price, sp_description, sp_image, sp_category_id, sp_stock, sp_brand) VALUES
('SP161', 'Devialet Phantom I', 60000000, 'Loa không dây hi-end', 'phantom1.jpg', 'LSP09', 10, 'Devialet'),
('SP162', 'Bowers & Wilkins Zeppelin', 20000000, 'Thiết kế biểu tượng, âm thanh hay', 'zeppelin.jpg', 'LSP09', 20, 'B&W'),
('SP163', 'Sonos Five', 15000000, 'Loa không dây đa phòng', 'sonos_five.jpg', 'LSP09', 40, 'Sonos'),
('SP164', 'Harman Kardon Aura Studio 3', 7000000, 'Thiết kế trong suốt, âm thanh 360 độ', 'aura3.jpg', 'LSP09', 80, 'Harman Kardon'),
('SP165', 'Marshall Stanmore III', 10000000, 'Loa bluetooth hoài cổ', 'stanmore3.jpg', 'LSP09', 60, 'Marshall'),
('SP166', 'JBL Boombox 3', 12000000, 'Loa di động công suất lớn', 'boombox3.jpg', 'LSP09', 50, 'JBL'),
('SP167', 'Apple HomePod Gen 2', 8000000, 'Loa thông minh cho hệ sinh thái Apple', 'homepod2.jpg', 'LSP09', 70, 'Apple'),
('SP168', 'Google Nest Audio', 3000000, 'Loa thông minh Google Assistant', 'nest_audio.jpg', 'LSP09', 100, 'Google'),
('SP169', 'Amazon Echo Studio', 5500000, 'Loa thông minh hỗ trợ Dolby Atmos', 'echo_studio.jpg', 'LSP09', 60, 'Amazon'),
('SP170', 'Edifier S2000MKIII', 8500000, 'Loa bookshelf active', 's2000mkiii.jpg', 'LSP09', 30, 'Edifier'),
('SP171', 'Klipsch The Fives', 22000000, 'Loa bookshelf active có HDMI-ARC', 'the_fives.jpg', 'LSP09', 25, 'Klipsch'),
('SP172', 'KEF LS50 Wireless II', 70000000, 'Loa bookshelf hi-end không dây', 'ls50w2.jpg', 'LSP09', 15, 'KEF'),
('SP173', 'Audioengine A2+', 7000000, 'Loa để bàn nhỏ gọn, chất âm tốt', 'a2plus.jpg', 'LSP09', 90, 'Audioengine'),
('SP174', 'Razer Nommo Chroma', 4000000, 'Loa gaming có RGB', 'nommo_chroma.jpg', 'LSP09', 70, 'Razer'),
('SP175', 'Logitech G560', 5000000, 'Loa gaming có Lightsync', 'g560.jpg', 'LSP09', 80, 'Logitech'),
('SP176', 'Sony SRS-XG500', 9000000, 'Loa di động tiệc tùng', 'xg500.jpg', 'LSP09', 45, 'Sony'),
('SP177', 'Bose SoundLink Revolve+', 8000000, 'Loa di động 360 độ', 'revolve_plus.jpg', 'LSP09', 65, 'Bose'),
('SP178', 'Anker Soundcore Motion+', 2500000, 'Loa di động giá rẻ, chất âm tốt', 'motion_plus.jpg', 'LSP09', 150, 'Anker'),
('SP179', 'Ultimate Ears Wonderboom 3', 2800000, 'Loa di động siêu bền, chống nước', 'wonderboom3.jpg', 'LSP09', 120, 'Ultimate Ears'),
('SP180', 'Bang & Olufsen Beosound A1 2nd Gen', 8000000, 'Loa di động cao cấp', 'a1_2nd.jpg', 'LSP09', 50, 'B&O');

-- Phụ kiện khác (20)
INSERT INTO SanPham (sp_id, sp_name, sp_price, sp_description, sp_image, sp_category_id, sp_stock, sp_brand) VALUES
('SP181', 'Anker 737 Power Bank', 3500000, 'Sạc dự phòng 140W', 'anker737.jpg', 'LSP10', 100, 'Anker'),
('SP182', 'Logitech C922 Pro Stream Webcam', 2500000, 'Webcam cho streamer', 'c922.jpg', 'LSP10', 80, 'Logitech'),
('SP183', 'Elgato Stream Deck MK.2', 4000000, 'Bàn điều khiển cho streamer', 'streamdeck.jpg', 'LSP10', 60, 'Elgato'),
('SP184', 'Wacom Intuos Pro', 9000000, 'Bảng vẽ đồ họa chuyên nghiệp', 'intuos_pro.jpg', 'LSP10', 40, 'Wacom'),
('SP185', 'Apple Pencil 2', 3500000, 'Bút cảm ứng cho iPad', 'apple_pencil2.jpg', 'LSP10', 120, 'Apple'),
('SP186', 'Samsung SmartTag+', 800000, 'Thiết bị định vị thông minh', 'smarttag.jpg', 'LSP10', 200, 'Samsung'),
('SP187', 'Apple AirTag', 750000, 'Thiết bị định vị thông minh', 'airtag.jpg', 'LSP10', 300, 'Apple'),
('SP188', 'Razer Mouse Bungee V3 Chroma', 1000000, 'Giữ dây chuột', 'bungee_v3.jpg', 'LSP10', 90, 'Razer'),
('SP189', 'Logitech MX Palm Rest', 500000, 'Kê tay bàn phím', 'mx_palmrest.jpg', 'LSP10', 150, 'Logitech'),
('SP190', 'Ugreen 100W GaN Charger', 1200000, 'Củ sạc nhanh nhiều cổng', 'ugreen100w.jpg', 'LSP10', 250, 'Ugreen'),
('SP191', 'CalDigit TS4 Thunderbolt 4 Dock', 10000000, 'Hub chuyển đổi cao cấp', 'caldigit_ts4.jpg', 'LSP10', 30, 'CalDigit'),
('SP192', 'Blue Yeti USB Microphone', 3200000, 'Microphone USB phổ biến', 'blue_yeti.jpg', 'LSP10', 100, 'Blue'),
('SP193', 'Shure MV7', 6500000, 'Microphone cho podcast', 'shure_mv7.jpg', 'LSP10', 50, 'Shure'),
('SP194', 'Lian Li Strimer Plus V2', 1800000, 'Dây nguồn RGB', 'strimer_plus.jpg', 'LSP10', 70, 'Lian Li'),
('SP195', 'Noctua NH-D15', 2500000, 'Tản nhiệt khí hiệu năng cao', 'nhd15.jpg', 'LSP10', 60, 'Noctua'),
('SP196', 'Corsair iCUE H150i Elite Capellix', 4500000, 'Tản nhiệt nước AIO', 'h150i.jpg', 'LSP10', 55, 'Corsair'),
('SP197', 'WD Black SN850X 2TB', 5000000, 'SSD NVMe Gen4 tốc độ cao', 'sn850x.jpg', 'LSP10', 80, 'Western Digital'),
('SP198', 'Samsung 980 Pro 1TB', 3000000, 'SSD NVMe Gen4', '980pro.jpg', 'LSP10', 110, 'Samsung'),
('SP199', 'G.Skill Trident Z5 RGB 32GB', 4000000, 'RAM DDR5 bus cao', 'trident_z5.jpg', 'LSP10', 75, 'G.Skill'),
('SP200', 'Seagate BarraCuda 4TB', 2200000, 'Ổ cứng HDD lưu trữ', 'barracuda4tb.jpg', 'LSP10', 130, 'Seagate');

-- KhachHang (5 customers)
INSERT INTO KhachHang (kh_id, kh_name, kh_password, kh_phone, kh_mail) VALUES
('KH001', 'Nguyễn Văn A', 'password123', '0912345678', 'nguyenvana@email.com'),
('KH002', 'Trần Thị B', 'password123', '0987654321', 'tranthib@email.com'),
('KH003', 'Lê Văn C', 'password123', '0905123456', 'levanc@email.com'),
('KH004', 'Phạm Thị D', 'password123', '0333444555', 'phamthid@email.com'),
('KH005', 'Hoàng Văn E', 'password123', '0777888999', 'hoangvane@email.com');

-- NhanVien (1 employee)
INSERT INTO NhanVien (nv_id, nv_name, nv_password, nv_phone, nv_mail, nv_address, nv_role, nv_birth) VALUES
('NV001', 'Admin', 'adminpass', '0123456789', 'admin@polyubs.com', '123 FPT Polytechnic', 'Quản lý', '1990-01-01');
