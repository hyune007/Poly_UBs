# 📋 QUY TRÌNH ĐẶT HÀNG - POLY_UBS

## 🎯 Tổng quan
Quy trình đặt hàng đã được hoàn thiện với đầy đủ các bước từ chọn sản phẩm đến tạo hóa đơn và lưu vào database.

## 🔄 Luồng xử lý

### 1️⃣ **Chọn sản phẩm và thêm vào giỏ hàng**
- Người dùng chọn sản phẩm và số lượng
- Bấm nút "Thêm vào giỏ hàng"
- Endpoint: `POST /cart/add`
- Dữ liệu được lưu vào bảng `GioHang` (giohang)

### 2️⃣ **Xem giỏ hàng**
- Đường dẫn: `/order/shopping-cart`
- Hiển thị danh sách sản phẩm trong giỏ
- Có thể cập nhật số lượng hoặc xóa sản phẩm
- Bấm "Tiến hành đặt hàng" → chuyển sang bước 3

### 3️⃣ **Điền thông tin đặt hàng**
- Đường dẫn: `/order/infor-order`
- Thu thập thông tin:
  - Họ tên, số điện thoại
  - Địa chỉ giao hàng (Thành phố, Quận/Huyện, Địa chỉ chi tiết)
  - Phương thức giao hàng (Giao hàng tiêu chuẩn 40.000₫ hoặc Nhận tại cửa hàng miễn phí)
  - Ghi chú (không bắt buộc)
- Bấm "Đặt hàng ngay" → submit form qua `POST /order/submit-info`
- Thông tin được lưu vào **Session** với key `orderInfo`
- Chuyển sang trang thanh toán

### 4️⃣ **Trang thanh toán**
- Đường dẫn: `/order/payment`
- Hiển thị thông tin đã điền từ bước trước (chỉ đọc - readonly)
- Hiển thị danh sách sản phẩm trong giỏ hàng
- Hiển thị tổng tiền (bao gồm phí vận chuyển)
- Chọn phương thức thanh toán (hiện tại chỉ để hiển thị, chưa xử lý)
- Bấm "Xác nhận thanh toán" → `POST /order/confirm-payment`

### 5️⃣ **Xác nhận thanh toán và tạo hóa đơn**
Khi người dùng bấm "Xác nhận thanh toán", hệ thống thực hiện:

1. **Tạo địa chỉ mới** → Lưu vào bảng `DiaChi` (diachi)
   - Tự động tạo ID địa chỉ (format: DC + 6 ký tự random)
   - Lưu thông tin: thành phố, quận/huyện, địa chỉ chi tiết
   - Liên kết với khách hàng

2. **Tạo hóa đơn** → Lưu vào bảng `HoaDon` (hoadon)
   - Tự động tạo ID hóa đơn (format: HD + 6 ký tự random)
   - Ngày tạo: ngày hiện tại
   - Trạng thái: "Chờ xác nhận"
   - Liên kết với khách hàng và địa chỉ giao hàng
   - Employee = null (đơn hàng online)

3. **Tạo chi tiết hóa đơn** → Lưu vào bảng `ChiTietHoaDon` (chitiethoadon)
   - Duyệt qua từng sản phẩm trong giỏ hàng
   - Tạo ID chi tiết (format: CT + 6 ký tự random)
   - Lưu: sản phẩm, số lượng, tổng tiền (hdct_total)

4. **Cập nhật tồn kho** → Bảng `SanPham` (sanpham)
   - Giảm số lượng tồn kho (sp_stock) của từng sản phẩm

5. **Xóa giỏ hàng** → Xóa dữ liệu trong bảng `GioHang`
   - Xóa toàn bộ sản phẩm trong giỏ của khách hàng

6. **Chuyển sang trang hoàn thành**

### 6️⃣ **Trang hoàn thành**
- Đường dẫn: `/order/complete`
- Hiển thị thông báo đặt hàng thành công
- Hiển thị thông tin hóa đơn:
  - Mã đơn hàng
  - Ngày đặt
  - Trạng thái
  - Thông tin khách hàng
  - Địa chỉ giao hàng
- Nút "Quay về trang chủ"

## 📊 Cấu trúc Database

### Bảng được sử dụng:
1. **GioHang** (giohang) - Lưu giỏ hàng tạm thời
2. **DiaChi** (diachi) - Lưu địa chỉ giao hàng
3. **HoaDon** (hoadon) - Lưu thông tin hóa đơn
4. **ChiTietHoaDon** (chitiethoadon) - Lưu chi tiết sản phẩm trong hóa đơn
5. **SanPham** (sanpham) - Cập nhật tồn kho
6. **KhachHang** (khachhang) - Thông tin khách hàng

### Mối quan hệ:
```
HoaDon
  ├── KhachHang (kh_id)
  ├── NhanVien (nv_id) - NULL cho đơn online
  └── DiaChi (dc_id)

ChiTietHoaDon
  ├── HoaDon (hd_id)
  └── SanPham (sp_id)

DiaChi
  └── KhachHang (kh_id)

GioHang
  ├── KhachHang (kh_id)
  └── SanPham (sp_id)
```

## 🗂️ Các file đã tạo/cập nhật

### Services:
- ✅ `BillService.java` - Xử lý nghiệp vụ hóa đơn
- ✅ `AddressService.java` - Xử lý nghiệp vụ địa chỉ
- ✅ `ShoppingCartService.java` - Xử lý giỏ hàng (đã có sẵn)

### Controllers:
- ✅ `OrderController.java` - Điều khiển toàn bộ quy trình đặt hàng
- ✅ `ShoppingCartController.java` - Điều khiển giỏ hàng (đã có sẵn)

### DTOs:
- ✅ `OrderInfoDTO.java` - Lưu thông tin đơn hàng tạm thời

### Entities:
- ✅ `DetailBill.java` - Đã thêm trường `hdct_total`

### Repositories:
- ✅ `DetailBillRepository.java` - Đã thêm method `findByBillId()`

### Templates:
- ✅ `shopping-cart.html` - Trang giỏ hàng (đã có)
- ✅ `infor-order.html` - Trang điền thông tin (đã cập nhật)
- ✅ `payment.html` - Trang thanh toán (đã cập nhật)
- ✅ `complete.html` - Trang hoàn thành (đã cập nhật)

## 🔑 Các điểm quan trọng

### Session Management:
- `loggedInUser` - Lưu thông tin khách hàng đăng nhập
- `orderInfo` - Lưu thông tin đơn hàng tạm (từ infor-order → payment)
- `completedBillId` - Lưu ID hóa đơn vừa tạo (để hiển thị trang complete)

### Validation:
- Kiểm tra đăng nhập trước khi cho phép đặt hàng
- Kiểm tra giỏ hàng không rỗng
- Kiểm tra số lượng tồn kho khi thêm vào giỏ và khi đặt hàng

### Transaction:
- Method `createBillFromCart()` được đánh dấu `@Transactional`
- Đảm bảo tất cả thao tác database thành công hoặc rollback hết

## 🚀 Cách sử dụng

1. **Đăng nhập** vào hệ thống
2. **Chọn sản phẩm** và thêm vào giỏ hàng
3. Vào **giỏ hàng** (`/order/shopping-cart`)
4. Bấm **"Tiến hành đặt hàng"**
5. Điền **thông tin giao hàng** và bấm **"Đặt hàng ngay"**
6. Kiểm tra thông tin và bấm **"Xác nhận thanh toán"**
7. Hoàn thành! Xem mã đơn hàng và thông tin

## 📝 Ghi chú

- Phần **phương thức thanh toán** hiện chỉ để hiển thị, chưa xử lý logic thanh toán thực tế
- Tất cả đơn hàng online có `nv_id = NULL`
- Trạng thái mặc định của hóa đơn là **"Chờ xác nhận"**
- ID được tạo tự động bằng UUID (6 ký tự cuối)

## ✅ Checklist hoàn thành

- ✅ Giỏ hàng hoạt động đầy đủ
- ✅ Thu thập thông tin khách hàng
- ✅ Lưu địa chỉ giao hàng tự động vào database
- ✅ Tạo hóa đơn (HoaDon)
- ✅ Tạo chi tiết hóa đơn (ChiTietHoaDon)
- ✅ Cập nhật tồn kho
- ✅ Xóa giỏ hàng sau khi đặt hàng
- ✅ Hiển thị thông tin hóa đơn sau khi hoàn thành

---

**Tác giả:** GitHub Copilot  
**Ngày hoàn thành:** 22/10/2025

