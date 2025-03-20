package com.example.study.domain.customer;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    //1. 방법 FETCH JOIN
    //고객과 연관된 주문을 한번에 가져오는 방식을 선택.
    //한번에 모든 데이터를 가져오므로 빠르고, 추가적인 쿼리 X
    //단 조인되는 데이터가 너무 많으면 성능 저하 우려.
    //또한 여러 개 사용이 어려워 복잡해질 수 있는 단점이 존재.
    @Query("SELECT c FROM Customer c JOIN FETCH c.orders")
    List<Customer> findAllWithOrders();
}

