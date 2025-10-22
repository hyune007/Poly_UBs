package com.poly.ubs.service;

import com.poly.ubs.entity.Brand;
import com.poly.ubs.repository.BrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Cài đặt dịch vụ cho thực thể Brand sử dụng dịch vụ chung
 */
@Service
public class BrandServiceImpl extends GenericServiceImpl<Brand, String, BrandRepository> {

    @Autowired
    private BrandRepository brandRepository;

    @Override
    protected BrandRepository getRepository() {
        return brandRepository;
    }

    public List<Brand> getBrands() {
        return brandRepository.findAll();
    }

}