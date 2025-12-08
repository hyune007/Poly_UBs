package com.poly.ubs.service;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Lớp trừu tượng cung cấp triển khai mặc định cho các thao tác CRUD cơ bản.
 *
 * @param <T>  Kiểu thực thể.
 * @param <ID> Kiểu dữ liệu của khóa chính.
 * @param <R>  Kiểu Repository tương ứng.
 */
public abstract class GenericServiceImpl<T, ID, R extends JpaRepository<T, ID>> implements IGenericService<T, ID> {

    protected abstract R getRepository();

    @Override
    public T save(T entity) {
        return getRepository().save(entity);
    }

    @Override
    public T update(T entity) {
        return getRepository().save(entity);
    }

    @Override
    public T findById(ID id) {
        return getRepository().findById(id).orElse(null);
    }

    @Override
    public List<T> findAll() {
        return getRepository().findAll();
    }

    @Override
    public void deleteById(ID id) {
        getRepository().deleteById(id);
    }

    @Override
    public boolean existsById(ID id) {
        return getRepository().existsById(id);
    }

    @Override
    public long count() {
        return getRepository().count();
    }
}