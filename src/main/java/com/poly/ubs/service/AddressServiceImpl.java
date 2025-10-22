package com.poly.ubs.service;

import com.poly.ubs.entity.Address;
import com.poly.ubs.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Cài đặt dịch vụ cho thực thể Address
 */
@Service
public class AddressServiceImpl extends GenericServiceImpl<Address, String, AddressRepository> {
    @Autowired
    private AddressRepository addressRepository;

    @Override
    protected AddressRepository getRepository() {
        return addressRepository;
    }
}
