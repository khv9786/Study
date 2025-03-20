package com.example.study.domain.customer;

import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    //2. 방법 @EntityGraph 사용
    //간단한 어노테이션을 통해 FETCH JOIN을 편하게 사용할 수 있다.
    //JPQL과도 함께 사용 할 수 있으나 left outer join만 지원한다.
    //또한 attribute를 직접 지정해야하므로 유연성이 떨어진다.
    //예제에서는 "orders"처럼 지정해야한다.
    @EntityGraph(attributePaths = "orders")
    List<Customer> findAllWithOrders();
}

