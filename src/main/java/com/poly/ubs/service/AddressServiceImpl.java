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
public class AddressServiceImpl extends GenericServiceImpl<Address, String, AddressRepository> {

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
        // Nếu chưa có địa chỉ mặc định nào thì gán địa chỉ đầu tiên là mặc định
        boolean hasDefault = customer.getAddresses() != null && customer.getAddresses().stream().anyMatch(a -> Boolean.TRUE.equals(a.getIsDefault()));
        if (!hasDefault) {
            address.setIsDefault(true);
        }
        return addressRepository.save(address);
    }

    /**
     * Đặt địa chỉ mặc định cho khách hàng
     */
    public void setDefaultAddress(Customer customer, String addressId) {
        if (customer.getAddresses() == null) return;
        customer.getAddresses().forEach(a -> {
            if (a.getId().equals(addressId)) {
                a.setIsDefault(true);
            } else {
                a.setIsDefault(false);
            }
            addressRepository.save(a);
        });
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