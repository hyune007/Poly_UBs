package com.poly.ubs.service;

import java.util.List;

/**
 * Giao diện chung cho các thao tác CRUD cơ bản
 *
 * @param <T>  Loại thực thể
 * @param <ID> Loại khóa chính
 */
public interface IGenericService<T, ID> {

    /**
     * Lưu một thực thể
     *
     * @param entity thực thể cần lưu
     * @return thực thể đã được lưu
     */
    T save(T entity);

    /**
     * Cập nhật một thực thể
     *
     * @param entity thực thể cần cập nhật
     * @return thực thể đã được cập nhật
     */
    T update(T entity);

    /**
     * Tìm một thực thể theo id
     *
     * @param id id của thực thể cần tìm
     * @return thực thể nếu tìm thấy, null nếu không tìm thấy
     */
    T findById(ID id);

    /**
     * Tìm tất cả các thực thể
     *
     * @return danh sách tất cả các thực thể
     */
    List<T> findAll();

    /**
     * Xóa một thực thể theo id
     *
     * @param id id của thực thể cần xóa
     */
    void deleteById(ID id);

    /**
     * Kiểm tra xem một thực thể có tồn tại theo id không
     *
     * @param id id cần kiểm tra
     * @return true nếu thực thể tồn tại, false nếu không tồn tại
     */
    boolean existsById(ID id);

    /**
     * Đếm tổng số thực thể
     *
     * @return số lượng thực thể
     */
    long count();
}