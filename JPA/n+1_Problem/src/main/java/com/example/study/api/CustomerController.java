package com.example.study.api;

import com.example.study.application.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @GetMapping("/s1")
    public String getCustomersAndOrders() {
        customerService.findCustomersAndOrders();
        return "주문 내역 조회 N+1 발생";
    }
}