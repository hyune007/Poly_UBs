package com.poly.ubs.api;

import com.poly.ubs.entity.Customer;
import com.poly.ubs.service.CustomerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")  // cho phép Vue gọi
@RestController
@RequestMapping("/api/customers")
public class CustomerRestApi {

    @Autowired
    CustomerServiceImpl service;

    // GET all
    @GetMapping
    public List<Customer> getAll() {
        return service.findAll();
    }

    // GET by ID
    @GetMapping("{id}")
    public Customer getOne(@PathVariable String id) {
        return service.findById(id);
    }

    // POST - Create
    @PostMapping
    public Customer create(@RequestBody Customer customer) {
        return service.save(customer);
    }

    // PUT - Update
    @PutMapping("{id}")
    public Customer update(@PathVariable String id, @RequestBody Customer customer) {
        customer.setId(id);
        return service.update(customer);
    }

    // DELETE
    @DeleteMapping("{id}")
    public void delete(@PathVariable String id) {
        service.deleteById(id);
    }
}