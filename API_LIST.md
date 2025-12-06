# Danh Sách API & Endpoints - Poly_UBs

Tài liệu này liệt kê toàn bộ các Endpoint (URL) được định nghĩa trong ứng dụng. Các Endpoint được chia thành hai loại chính:
1.  **API Endpoints:** Trả về dữ liệu JSON (thường dùng cho AJAX, Webhook, Mobile App).
2.  **View Endpoints:** Trả về trang HTML (giao diện người dùng).

---

## 1. Payment & Integration (Mới)
Nhóm này xử lý các logic thanh toán, bao gồm tích hợp SePay.

| Phương thức | Đường dẫn (Path) | Loại | Mô tả | Input (Body/Param) | Output |
| :--- | :--- | :--- | :--- | :--- | :--- |
| `POST` | `/api/payment/sepay-webhook` | API | **Webhook nhận dữ liệu từ SePay.** Tự động cập nhật trạng thái hóa đơn khi nhận tiền. | `SePayWebhookDTO` (JSON) | String Status |
| `GET` | `/api/payment/check-status/{billId}` | API | Kiểm tra trạng thái thanh toán của hóa đơn (dùng cho Long-polling). | `billId` (Path Variable) | `{ "status": "..." }` |

---

## 2. Order & Checkout (Đặt hàng)
Quy trình đặt hàng của khách hàng.

| Phương thức | Đường dẫn (Path) | Loại | Mô tả | Input | Output |
| :--- | :--- | :--- | :--- | :--- | :--- |
| `GET` | `/order/shopping-cart` | View | Trang Giỏ hàng. | - | HTML Page |
| `GET` | `/order/infor-order` | View | Trang nhập thông tin nhận hàng. | - | HTML Page |
| `POST` | `/order/submit-info` | Action | Lưu thông tin nhận hàng tạm thời và chuyển sang bước thanh toán. | `OrderInfoDTO` (Form) | Redirect |
| `GET` | `/order/payment` | View | Trang chọn phương thức thanh toán (COD/Bank/Momo). | - | HTML Page |
| `POST` | `/order/confirm-payment` | Action | **Xác nhận đặt hàng.** Tạo hóa đơn trong DB. | `paymentMethod` | Redirect |
| `GET` | `/order/complete` | View | Trang "Hoàn tất". Hiển thị mã QR nếu chọn chuyển khoản. | - | HTML Page |

---

## 3. Authentication (Xác thực)
Quản lý đăng nhập, đăng ký và bảo mật.

| Phương thức | Đường dẫn (Path) | Loại | Mô tả | Input | Output |
| :--- | :--- | :--- | :--- | :--- | :--- |
| `GET` | `/auth/login` | View | Trang Đăng nhập. | - | HTML Page |
| `POST` | `/auth/login-post` | Action | Xử lý logic đăng nhập (kiểm tra Email/Pass). | `email`, `password` | Redirect |
| `GET` | `/auth/logout` | Action | Đăng xuất. | - | Redirect |
| `GET` | `/auth/register` | View | Trang Đăng ký. | - | HTML Page |
| `POST` | `/auth/register-post` | Action | Xử lý đăng ký tài khoản mới. | `email`, `password`, `name`... | Redirect |
| `GET` | `/auth/forgot-password` | View | Trang Quên mật khẩu. | - | HTML Page |
| `POST` | `/auth/forgot-password-post` | Action | Gửi email reset mật khẩu. | `email` | Redirect |
| `GET` | `/auth/reset-password` | View | Trang đặt lại mật khẩu (từ link email). | `token` | HTML Page |
| `POST` | `/auth/reset-password-post` | Action | Thực hiện đổi mật khẩu mới. | `token`, `newPassword` | Redirect |
| `POST` | `/auth/api/auth/firebase-login` | API | Đăng nhập bằng Google (Firebase Auth). | `{ "idToken": "..." }` | JSON Customer Info |

---

## 4. Admin Management (Quản trị)
Các trang dành cho Admin quản lý hệ thống.

### Product Management
| Phương thức | Đường dẫn (Path) | Loại | Mô tả | Input |
| :--- | :--- | :--- | :--- | :--- |
| `GET` | `/admin/products` | View | Danh sách sản phẩm (có phân trang, lọc). | `page`, `size`, `category` |
| `GET` | `/admin/products/create` | View | Form thêm mới sản phẩm. | - |
| `POST` | `/admin/products/save` | Action | Lưu sản phẩm (Thêm/Sửa). | `Product` Entity (Form) |
| `GET` | `/admin/products/edit/{id}` | View | Form chỉnh sửa sản phẩm. | `id` |
| `GET` | `/admin/products/delete/{id}` | Action | Xóa sản phẩm. | `id` |

*(Lưu ý: Các Controller quản lý khác như `EmployeeController`, `CustomerController` có cấu trúc tương tự)*

---

## 5. Public Pages (Trang công khai)
Các trang hiển thị thông tin chung.

| Phương thức | Đường dẫn (Path) | Loại | Mô tả |
| :--- | :--- | :--- | :--- |
| `GET` | `/home` (hoặc `/`) | View | Trang chủ. |
| `GET` | `/products` | View | Trang danh sách sản phẩm (Client). |
| `GET` | `/product/{id}` | View | Trang chi tiết sản phẩm. |
| `GET` | `/about` | View | Trang giới thiệu. |
| `GET` | `/contact` | View | Trang liên hệ. |
