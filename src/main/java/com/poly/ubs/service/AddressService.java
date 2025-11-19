package com.poly.ubs.service;

import com.poly.ubs.entity.Address;
import com.poly.ubs.entity.Customer;
import com.poly.ubs.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Service xử lý nghiệp vụ địa chỉ
 */
@Service
public class AddressService extends GenericServiceImpl<Address, String, AddressRepository> {

    @Autowired
    private AddressRepository addressRepository;

    @Override
    protected AddressRepository getRepository() {
        return addressRepository;
    }

    /**
     * Tạo địa chỉ mới cho khách hàng
     *
     * @param customer      khách hàng
     * @param city          thành phố
     * @param ward          phường/xã
     * @param detailAddress địa chỉ chi tiết
     * @return địa chỉ đã tạo
     */
    public Address createAddress(Customer customer, String city, String ward, String detailAddress) {
        Address address = new Address();
        address.setId(generateAddressId());
        address.setCustomer(customer);
        address.setCity(city);
        address.setWard(ward);
        address.setDetailAddress(detailAddress);
        return addressRepository.save(address);
    }

    /**
     * Tạo ID ngẫu nhiên cho địa chỉ
     *
     * @return ID địa chỉ
     */
    private String generateAddressId() {
        return "DC" + UUID.randomUUID().toString().substring(0, 6).toUpperCase();
    }
}

