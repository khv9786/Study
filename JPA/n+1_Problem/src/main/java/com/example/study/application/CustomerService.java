package com.example.study.application;

import com.example.study.domain.customer.Customer;
import com.example.study.domain.customer.CustomerRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;

    @Transactional
    public void findCustomersAndOrders() {
        List<Customer> customers = customerRepository.findAll();
        for (Customer customer : customers) {
            System.out.println("고객 ID: " + customer.getId());
            System.out.println("주문 개수: " + customer.getOrders().size());
            System.out.println();
        }
    }
}
