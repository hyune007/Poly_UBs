# Repository Pattern trong Spring Data JPA

## Tại sao cần Repository cho các thực thể?

### 1. Vai trò của Repository

Repository đóng vai trò như một lớp truy xuất dữ liệu, cung cấp một giao diện sạch sẽ và dễ hiểu để làm việc với các thực thể trong cơ sở dữ liệu. Trong kiến trúc Spring, repository nằm giữa tầng service và cơ sở dữ liệu.

### 2. Lợi ích chính

#### a. Trừu tượng hóa truy xuất dữ liệu
Thay vì phải viết các câu lệnh SQL phức tạp hoặc làm việc trực tiếp với JDBC, repository cung cấp một API đơn giản để thực hiện các thao tác CRUD (Create, Read, Update, Delete).

#### b. Tự động tạo truy vấn
Spring Data JPA tự động tạo các truy vấn dựa trên tên phương thức trong interface repository. Ví dụ:
```java
public interface ProductRepository extends JpaRepository<Product, String> {
    List<Product> findByName(String name);
    List<Product> findByPriceGreaterThan(int price);
}
```

#### c. Các phương thức CRUD được tích hợp sẵn
Khi kế thừa từ JpaRepository, repository tự động có các phương thức:
- save()
- findById()
- findAll()
- delete()
- count()
- exists()

#### d. Quản lý giao dịch
Repository xử lý việc quản lý giao dịch tự động, đảm bảo tính nhất quán của dữ liệu.

#### e. Kết nối hiệu quả với cơ sở dữ liệu
Quản lý pool kết nối cơ sở dữ liệu một cách hiệu quả.

### 3. Cách hoạt động trong dự án của bạn

#### Trong lớp Service
Lớp service sử dụng repository thông qua phương thức getRepository():
```java
@Override
protected ProductRepository getRepository() {
    return productRepository;
}
```

#### Trong GenericServiceImpl
Lớp dịch vụ chung sử dụng repository để thực hiện các thao tác:
```java
@Override
public T save(T entity) {
    return getRepository().save(entity);
}
```

### 4. Ví dụ minh họa cụ thể

Giả sử chúng ta cần xây dựng chức năng quản lý sản phẩm trong hệ thống bán hàng. Chúng ta sẽ có các thành phần sau:

#### a. Entity - Thực thể Product
```java
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(nullable = false)
    private String name;
    
    @Column
    private String description;
    
    @Column(nullable = false)
    private Double price;
    
    @Column(name = "stock_quantity")
    private Integer stockQuantity;
    
    // Constructors
    public Product() {}
    
    public Product(String name, String description, Double price, Integer stockQuantity) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }
    
    // Getters and Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }
    
    public Integer getStockQuantity() { return stockQuantity; }
    public void setStockQuantity(Integer stockQuantity) { this.stockQuantity = stockQuantity; }
}
```

#### b. Repository - ProductRepository
```java
@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    // Các phương thức tùy chỉnh
    List<Product> findByNameContainingIgnoreCase(String name);
    List<Product> findByPriceBetween(Double minPrice, Double maxPrice);
    List<Product> findByStockQuantityGreaterThan(Integer quantity);
    
    // Phương thức đếm sản phẩm hết hàng
    Long countByStockQuantityEquals(0);
    
    // Phương thức tìm kiếm nâng cao
    @Query("SELECT p FROM Product p WHERE p.name LIKE %:keyword% OR p.description LIKE %:keyword%")
    List<Product> searchProducts(@Param("keyword") String keyword);
}
```

#### c. Service - ProductServiceImpl
```java
@Service
public class ProductServiceImpl extends GenericServiceImpl<Product, Integer, ProductRepository> 
    implements IGenericService<Product, Integer> {
    
    @Autowired
    private ProductRepository productRepository;
    
    @Override
    protected ProductRepository getRepository() {
        return productRepository;
    }
    
    // Các phương thức tùy chỉnh
    public List<Product> searchByNameOrDescription(String keyword) {
        return productRepository.searchProducts(keyword);
    }
    
    public List<Product> findInStockProducts() {
        return productRepository.findByStockQuantityGreaterThan(0);
    }
    
    public Long countOutOfStockProducts() {
        return productRepository.countByStockQuantityEquals(0);
    }
}
```

#### d. Sử dụng trong Controller
```java
@RestController
@RequestMapping("/api/products")
public class ProductController {
    
    @Autowired
    private ProductServiceImpl productService;
    
    // Lấy tất cả sản phẩm
    @GetMapping
    public List<Product> getAllProducts() {
        return productService.findAll();
    }
    
    // Tìm sản phẩm theo ID
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Integer id) {
        Product product = productService.findById(id);
        if (product != null) {
            return ResponseEntity.ok(product);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    // Tạo mới sản phẩm
    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        return productService.save(product);
    }
    
    // Cập nhật sản phẩm
    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable Integer id, @RequestBody Product productDetails) {
        Product product = productService.findById(id);
        if (product != null) {
            product.setName(productDetails.getName());
            product.setDescription(productDetails.getDescription());
            product.setPrice(productDetails.getPrice());
            product.setStockQuantity(productDetails.getStockQuantity());
            return productService.update(product);
        }
        return null;
    }
    
    // Xóa sản phẩm
    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Integer id) {
        productService.deleteById(id);
    }
    
    // Tìm kiếm sản phẩm theo từ khóa
    @GetMapping("/search")
    public List<Product> searchProducts(@RequestParam String keyword) {
        return productService.searchByNameOrDescription(keyword);
    }
}
```

#### e. Ví dụ sử dụng trong ứng dụng thực tế
```java
// Trong một service khác hoặc controller
@Service
public class OrderService {
    
    @Autowired
    private ProductServiceImpl productService;
    
    public void processOrder(Integer productId, Integer quantity) {
        // Tìm sản phẩm theo ID
        Product product = productService.findById(productId);
        
        if (product != null && product.getStockQuantity() >= quantity) {
            // Cập nhật số lượng tồn kho
            product.setStockQuantity(product.getStockQuantity() - quantity);
            productService.update(product);
            
            // Xử lý đơn hàng...
            System.out.println("Đã đặt hàng thành công: " + product.getName());
        } else {
            System.out.println("Sản phẩm không đủ hàng hoặc không tồn tại");
        }
    }
}
```

Với cấu trúc này:
1. [Product](file:///D:/hyu/FPT/FALL_2025/SOF3022_LapTrinhJava5/Poly_UBs/src/main/java/com/poly/ubs/entity/Product.java) là entity đại diện cho bảng sản phẩm trong cơ sở dữ liệu
2. [ProductRepository](file:///D:/hyu/FPT/FALL_2025/SOF3022_LapTrinhJava5/Poly_UBs/src/main/java/com/poly/ubs/repository/ProductRepository.java) là interface kế thừa JpaRepository, cung cấp các phương thức CRUD và tùy chỉnh
3. [ProductServiceImpl](file:///D:/hyu/FPT/FALL_2025/SOF3022_LapTrinhJava5/Poly_UBs/src/main/java/com/poly/ubs/service/ProductServiceImpl.java) là lớp service thực hiện logic nghiệp vụ
4. [ProductController](file:///D:/hyu/FPT/FALL_2025/SOF3022_LapTrinhJava5/Poly_UBs/src/main/java/com/poly/ubs/DemoProductController.java) là lớp controller xử lý các yêu cầu HTTP

### 5. Kết luận

Repository là thành phần thiết yếu trong kiến trúc Spring Data JPA vì nó:
- Tách biệt logic nghiệp vụ khỏi logic truy xuất dữ liệu
- Giảm thiểu mã nguồn cần viết
- Tăng tính bảo trì và kiểm thử của ứng dụng
- Cung cấp các tính năng mạnh mẽ như caching, lazy loading, v.v.