package com.poly.ubs.service;

import com.poly.ubs.entity.DetailBill;
import com.poly.ubs.repository.DetailBillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DetailBillServiceImpl extends GenericServiceImpl<DetailBill, Integer, DetailBillRepository> {
    @Autowired
    private DetailBillRepository detailBillRepository;
    @Override
    protected DetailBillRepository getRepository() {return detailBillRepository;}
}
