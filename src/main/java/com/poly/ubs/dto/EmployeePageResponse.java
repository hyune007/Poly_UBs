package com.poly.ubs.dto;

import com.poly.ubs.entity.Employee;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeePageResponse {
    private List<Employee> items;
    private long totalItems;
    private int totalPages;
    private int currentPage;
}
