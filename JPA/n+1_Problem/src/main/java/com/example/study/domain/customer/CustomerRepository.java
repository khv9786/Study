package com.example.study.domain.customer;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    //     findAll()를 통해 조회 하는 경우
    //     DB에 N을 조회하느라 1, 이후 N명의 데이터를 조회하느라 N+1 의 조회가 발생.
    List<Customer> findAll();
}

