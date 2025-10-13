# Triển khai Dịch vụ Chung

Gói này cung cấp một triển khai dịch vụ chung có thể được sử dụng bởi tất cả các lớp dịch vụ để tránh lặp lại mã.

## Thành phần

1. `IGenericService<T, ID>` - Giao diện chung với các thao tác CRUD phổ biến
2. `GenericServiceImpl<T, ID, R>` - Triển khai trừu tượng của dịch vụ chung
3. `ProductServiceImpl` - Ví dụ triển khai cho thấy cách mở rộng dịch vụ chung

## Giải thích các tham số generic

Trong `GenericServiceImpl<T, ID, R>`:
- `T` là kiểu của Entity (thực thể), ví dụ: Product, Brand, Category
- `ID` là kiểu của khóa chính của Entity, ví dụ: String, Long, Integer
- `R` là kiểu của Repository tương ứng với Entity

## Cách sử dụng

Để sử dụng dịch vụ chung trong các triển khai dịch vụ của bạn:

1. Tạo lớp dịch vụ của bạn kế thừa `GenericServiceImpl`:
```java
@Service
public class ProductServiceImpl extends GenericServiceImpl<Product, String, ProductRepository> {
    
    @Autowired
    private ProductRepository productRepository;
    
    @Override
    protected ProductRepository getRepository() {
        return productRepository;
    }
}
```

2. Đảm bảo kho chứa của bạn kế thừa `JpaRepository`:
```java
@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
    // Các phương thức tùy chỉnh của bạn
}
```

## Ví dụ cụ thể trong dự án

### Với thực thể Brand:
```java
@Service
public class BrandServiceImpl extends GenericServiceImpl<Brand, String, BrandRepository> {
    
    @Autowired
    private BrandRepository brandRepository;
    
    @Override
    protected BrandRepository getRepository() {
        return brandRepository;
    }
}
```

### Với thực thể Category:
```java
@Service
public class CategoryServiceImpl extends GenericServiceImpl<Category, String, CategoryRepository> {
    
    @Autowired
    private CategoryRepository categoryRepository;
    
    @Override
    protected CategoryRepository getRepository() {
        return categoryRepository;
    }
}
```

## Các phương thức có sẵn

Dịch vụ chung cung cấp các phương thức sau:
- `save(T entity)` - Lưu một thực thể
- `update(T entity)` - Cập nhật một thực thể
- `findById(ID id)` - Tìm một thực thể theo ID
- `findAll()` - Tìm tất cả các thực thể
- `deleteById(ID id)` - Xóa một thực thể theo ID
- `existsById(ID id)` - Kiểm tra xem một thực thể có tồn tại theo ID không
- `count()` - Đếm tất cả các thực thể

Bạn cũng có thể thêm các phương thức tùy chỉnh vào các triển khai dịch vụ cụ thể của mình khi cần thiết.