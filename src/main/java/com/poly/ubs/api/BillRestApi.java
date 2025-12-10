package com.poly.ubs.api;

import com.poly.ubs.entity.Bill;
import com.poly.ubs.service.BillServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")  // cho phép Vue gọi
@RestController
@RequestMapping("/api/bills")
public class BillRestApi {

    @Autowired
    BillServiceImpl billService;

    // GET ALL
    @GetMapping
    public List<Bill> findAll() {
        return billService.findAll();
    }

    // GET ONE
    @GetMapping("/{id}")
    public Bill findById(@PathVariable("id") String id) {
        return billService.findById(id);
    }

    // CREATE
    @PostMapping
    public Bill create(@RequestBody Bill bill) {
        return billService.save(bill);
    }

    // UPDATE
    @PutMapping("/{id}")
    public Bill update(@PathVariable("id") String id, @RequestBody Bill bill) {
        bill.setId(id);       // Gắn ID từ URL
        return billService.update(bill);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") String id) {
        billService.deleteById(id);
    }
}
