# Kế hoạch Tích hợp Thanh toán SePay (QR Code)

Tài liệu này mô tả các bước cần thiết để tích hợp tính năng hiển thị mã QR chuyển khoản nhanh thông qua SePay vào dự án Poly_UBs.

## 1. Mục tiêu
- Hiển thị mã QR chuyển khoản ngân hàng tại trang "Hoàn tất đơn hàng" (Order Complete) nếu khách hàng chọn phương thức "Chuyển khoản ngân hàng".
- Mã QR được tạo tự động theo định dạng của SePay.
- **Nội dung chuyển khoản (des)** sẽ chính là **Mã hóa đơn (Bill ID)**.
- **Số tiền (amount)** là tổng giá trị đơn hàng.

## 2. Phân tích hiện trạng & Thay đổi yêu cầu

### Hiện trạng
- **OrderController**:
    - `/order/payment`: Hiển thị trang chọn phương thức thanh toán.
    - `/order/confirm-payment`: Tạo `Bill` (Hóa đơn) và lưu xuống DB.
    - `/order/complete`: Hiển thị thông tin hóa đơn đã tạo.
- **View (`payment.html`)**: Có các nút Radio chọn phương thức thanh toán nhưng nằm ngoài thẻ `<form>` submit, dẫn đến việc Backend không nhận được phương thức thanh toán khách hàng chọn.
- **Entity (`Bill`)**: Đã có trường `id` (String) và liên kết để tính tổng tiền.

### Các thay đổi cần thực hiện

#### Bước 1: Cấu hình thông tin Ngân hàng
Thêm thông tin tài khoản nhận tiền vào `application.properties` để dễ dàng quản lý và thay đổi.

#### Bước 2: Cập nhật `payment.html`
Hiện tại nút "Xác nhận thanh toán" nằm trong một form riêng biệt với phần chọn "Hình thức thanh toán". Cần sửa lại để khi submit, giá trị `paymentMethod` được gửi về server.

#### Bước 3: Cập nhật Logic tại `OrderController`
1.  **confirmPayment**: Cần nhận tham số `paymentMethod` từ view.
    *   Lưu phương thức thanh toán vào Hóa đơn (Cần thêm trường `paymentMethod` vào Entity `Bill` hoặc xử lý logic tạm thời).
    *   Nếu chưa muốn sửa Entity `Bill`, ta có thể lưu tạm phương thức thanh toán vào Session hoặc truyền qua RedirectAttributes để xử lý hiển thị ở trang sau.
2.  **complete**:
    *   Lấy thông tin Hóa đơn (`Bill`).
    *   Tạo đường dẫn QR SePay dựa trên thông tin hóa đơn.
    *   Truyền đường dẫn QR sang view `complete.html`.

#### Bước 4: Cập nhật `complete.html`
Hiển thị ảnh QR Code nếu có đường dẫn được truyền sang.

---

## 3. Chi tiết triển khai (Step-by-Step)

### Bước 1: Thêm cấu hình vào `application.properties`
Thêm các key sau vào file cấu hình:
```properties
# Cấu hình SePay
sepay.bank.account=SO_TAI_KHOAN_CUA_BAN
sepay.bank.code=MA_NGAN_HANG (Ví dụ: MB, VCB, TPB)
sepay.template=compact (Hoặc để trống, qronly)
```

### Bước 2: Sửa `OrderController.java`

**2.1. Tiêm giá trị cấu hình:**
```java
@Value("${sepay.bank.account}")
private String sepayAccount;

@Value("${sepay.bank.code}")
private String sepayBankCode;

@Value("${sepay.template}")
private String sepayTemplate;
```

**2.2. Cập nhật phương thức `confirmPayment`:**
Thêm tham số `@RequestParam("paymentMethod") String paymentMethod`.

**2.3. Cập nhật phương thức `complete`:**
Tính toán và tạo URL QR Code:

```java
// Trong method complete()
if (bill != null) {
    // ... logic cũ ...
    
    // Tính tổng tiền (giả sử bạn có method tính hoặc field total)
    // Lưu ý: Cần đảm bảo lấy đúng tổng tiền (bao gồm ship)
    long totalAmount = bill.getTotalAmount(); // Cần kiểm tra lại cách lấy tổng tiền từ Bill hoặc tính lại
    
    // Tạo Link SePay
    // Format: https://qr.sepay.vn/img?acc={acc}&bank={bank}&amount={amount}&des={des}&template={template}
    String qrUrl = String.format("https://qr.sepay.vn/img?acc=%s&bank=%s&amount=%d&des=%s&template=%s",
            sepayAccount,
            sepayBankCode,
            totalAmount,
            bill.getId(), // Nội dung là Mã hóa đơn
            sepayTemplate
    );
    
    model.addAttribute("qrUrl", qrUrl);
    // Logic: Chỉ hiển thị QR nếu phương thức thanh toán là Banking (Cần lưu vết phương thức thanh toán)
}
```

### Bước 3: Sửa `payment.html`
Đảm bảo thẻ `<form>` bao trùm cả phần chọn phương thức thanh toán, hoặc dùng Javascript để set value cho một input hidden bên trong form submit.

**Giải pháp dùng Hidden Input (Đơn giản, ít phá vỡ layout):**
1. Thêm `<input type="hidden" name="paymentMethod" id="hiddenPaymentMethod" value="cod">` vào trong form submit.
2. Thêm sự kiện `onchange` cho các radio button để cập nhật giá trị cho input hidden này.

### Bước 4: Sửa `complete.html`
Thêm phần hiển thị mã QR.

```html
<div th:if="${qrUrl != null}" class="text-center mt-4">
    <h4>Quét mã để thanh toán</h4>
    <p>Nội dung chuyển khoản: <strong th:text="${bill.id}"></strong></p>
    <img th:src="${qrUrl}" alt="Mã QR Thanh Toán" class="img-fluid" style="max-width: 300px;">
    <p class="text-muted mt-2">Vui lòng giữ nguyên nội dung chuyển khoản khi quét mã.</p>
</div>
```

## 4. Lưu ý quan trọng
- **Tổng tiền:** Entity `Bill` hiện tại trong file `Bill.java` tôi đã đọc chưa thấy trường lưu "Tổng tiền" (Total Amount) trực tiếp trong DB (chỉ thấy `shoppingCartService.calculateTotal` tính toán lúc runtime). Bạn cần chắc chắn rằng `Bill` hoặc các bảng chi tiết (`DetailBill`) có thể cung cấp chính xác số tiền cần thanh toán tại trang Complete.
- **Xác nhận tự động:** Kế hoạch này chỉ dừng lại ở việc **hiển thị QR**. Để đơn hàng tự động cập nhật trạng thái thành "Đã thanh toán", bạn cần tích hợp thêm **Webhook** của SePay hoặc đối soát thủ công.

## 5. Hành động tiếp theo
Hãy xác nhận bạn muốn tôi tiến hành chỉnh sửa code theo kế hoạch này (Bắt đầu từ việc sửa `payment.html` hay `OrderController` trước?).
