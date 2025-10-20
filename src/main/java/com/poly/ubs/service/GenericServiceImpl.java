package com.poly.ubs.service;

import org.springframework.data.jpa.repository.JpaRepository;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Optional;

/**
 * Lớp trừu tượng dịch vụ chung cài đặt các thao tác CRUD cơ bản
 * @param <T> Loại thực thể
 * @param <ID> Loại khóa chính
 * @param <R> Loại repository
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

    // ✅ Thêm hàm này
    public Optional<T> findOptionalById(ID id) {
        return getRepository().findById(id);
    }
}