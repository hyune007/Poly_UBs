package com.poly.ubs.service;

import com.poly.ubs.entity.Address;
import com.poly.ubs.entity.Customer;
import com.poly.ubs.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Dịch vụ xử lý nghiệp vụ liên quan đến địa chỉ.
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
     * Tạo mới địa chỉ cho khách hàng.
     *
     * @param customer      Đối tượng khách hàng.
     * @param city          Tên thành phố.
     * @param ward          Tên phường/xã.
     * @param detailAddress Địa chỉ chi tiết.
     * @return Đối tượng Address vừa được tạo.
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
     * Sinh mã định danh ngẫu nhiên cho địa chỉ.
     *
     * @return Chuỗi ID địa chỉ.
     */
    private String generateAddressId() {
        return "DC" + UUID.randomUUID().toString().substring(0, 6).toUpperCase();
    }
}

