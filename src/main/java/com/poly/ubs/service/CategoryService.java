package com.poly.ubs.service;

import com.poly.ubs.entity.Category;

/**
 * Giao diện dịch vụ cho Category
 * Kế thừa từ GenericService để dùng CRUD mặc định
 */
public interface CategoryService extends IGenericService<Category, String> {
    // Nếu sau này bạn cần thêm các phương thức đặc thù cho Category thì viết thêm ở đây
}
