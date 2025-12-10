package com.poly.ubs.service;

import java.util.List;

/**
 * Giao diện định nghĩa các thao tác CRUD cơ bản cho dịch vụ.
 *
 * @param <T>  Kiểu thực thể.
 * @param <ID> Kiểu dữ liệu của khóa chính.
 */
public interface IGenericService<T, ID> {

    /**
     * Lưu trữ một thực thể mới hoặc cập nhật thực thể hiện có.
     *
     * @param entity Đối tượng thực thể cần lưu.
     * @return Đối tượng thực thể đã được lưu.
     */
    T save(T entity);

    /**
     * Cập nhật thông tin của một thực thể.
     *
     * @param entity Đối tượng thực thể cần cập nhật.
     * @return Đối tượng thực thể đã được cập nhật.
     */
    T update(T entity);

    /**
     * Tìm kiếm một thực thể dựa trên khóa chính.
     *
     * @param id Khóa chính của thực thể.
     * @return Đối tượng thực thể nếu tìm thấy, ngược lại trả về null.
     */
    T findById(ID id);

    /**
     * Lấy danh sách toàn bộ các thực thể.
     *
     * @return Danh sách các thực thể.
     */
    List<T> findAll();

    /**
     * Xóa một thực thể dựa trên khóa chính.
     *
     * @param id Khóa chính của thực thể cần xóa.
     */
    void deleteById(ID id);

    /**
     * Kiểm tra sự tồn tại của một thực thể dựa trên khóa chính.
     *
     * @param id Khóa chính cần kiểm tra.
     * @return True nếu thực thể tồn tại, ngược lại trả về False.
     */
    boolean existsById(ID id);

    /**
     * Đếm tổng số lượng thực thể hiện có.
     *
     * @return Số lượng thực thể.
     */
    long count();
}