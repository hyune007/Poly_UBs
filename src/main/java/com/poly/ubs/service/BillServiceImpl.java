package com.poly.ubs.service;

import com.poly.ubs.entity.Bill;
import com.poly.ubs.repository.BillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BillServiceImpl extends GenericServiceImpl<Bill, Integer, BillRepository> {
    @Autowired
    private BillRepository billRepository;
    @Override
    protected BillRepository getRepository() {return billRepository;}
}
