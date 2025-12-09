package com.poly.ubs.service;

import com.poly.ubs.entity.Brand;
import com.poly.ubs.repository.BrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Triển khai dịch vụ xử lý nghiệp vụ liên quan đến thương hiệu sản phẩm.
 */
@Service
public class BrandServiceImpl extends GenericServiceImpl<Brand, String, BrandRepository> {

    @Autowired
    private BrandRepository brandRepository;

    @Override
    protected BrandRepository getRepository() {
        return brandRepository;
    }

    /**
     * Lấy danh sách toàn bộ thương hiệu.
     *
     * @return Danh sách đối tượng Brand.
     */
    public List<Brand> getBrands() {
        return brandRepository.findAll();
    }
}