package com.poly.ubs.service;

import com.poly.ubs.entity.Address;
import com.poly.ubs.entity.Customer;
import com.poly.ubs.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Triển khai dịch vụ xử lý nghiệp vụ liên quan đến địa chỉ.
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
     * Tạo mới địa chỉ cho khách hàng.
     * Nếu khách hàng chưa có địa chỉ mặc định, địa chỉ mới sẽ được thiết lập là mặc định.
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
        // Nếu chưa có địa chỉ mặc định nào thì gán địa chỉ đầu tiên là mặc định
        boolean hasDefault = customer.getAddresses() != null && customer.getAddresses().stream().anyMatch(a -> Boolean.TRUE.equals(a.getIsDefault()));
        if (!hasDefault) {
            address.setIsDefault(true);
        }
        return addressRepository.save(address);
    }

    /**
     * Thiết lập địa chỉ mặc định cho khách hàng.
     * Các địa chỉ khác sẽ được bỏ đánh dấu mặc định.
     *
     * @param customer  Đối tượng khách hàng.
     * @param addressId ID của địa chỉ cần đặt làm mặc định.
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
     * Sinh mã định danh ngẫu nhiên cho địa chỉ.
     *
     * @return Chuỗi ID địa chỉ.
     */
    private String generateAddressId() {
        return "DC" + UUID.randomUUID().toString().substring(0, 6).toUpperCase();
    }
}