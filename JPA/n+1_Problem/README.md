# JPA N+1을 해결해봅시다 ! ✨

# 🚀 N+1 문제, LAZY 로딩, JOIN vs FETCH JOIN 정리

## 1️⃣ N+1 문제란?

### 🔥 **N+1 문제 발생 원인**
1. `findAll()` 등으로 **기본 엔티티(N)를 조회**
2. 연관된 엔티티를 **Lazy 로딩**으로 설정했을 경우, 각 엔티티의 연관 데이터를 조회할 때 **추가 쿼리(N번) 발생**
3. 결과적으로 **1 + N번의 쿼리가 실행**되어 성능 저하 발생

### 💡 **LAZE 로딩**
```java
@OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
private List<Order> orders;
```
- customer.getOrders() 호출 시점까지 연관된 엔티티를 조회하지 않음
- 실제 사용될 때 추가 쿼리가 실행되기 때문에 ⚠️ N+1 문제 발생 가능

### 💡 **예제 코드 (N+1 문제 발생)**
```java
public void findCustomersAndOrders() {
    List<Customer> customers = customerRepository.findAll();  // (1) 고객 리스트 조회 (쿼리 1번 실행)

    for (Customer customer : customers) {
        System.out.println(customer.getOrders().size()); // (2) 고객별 주문 조회 (쿼리 N번 실행)
    }
}
```

## 2️⃣ JOIN으로 해결하면 되지 않나?

#### ❌ 일반 JOIN은 LAZE 속성을 해제하지 못한다.
```java
@Query("SELECT c FROM Customer c JOIN c.orders")
List<Customer> findAllWithJoin();
```

- SQL에서 사용하는 일반적인 JOIN
- 연관된 엔티티를 가져오지만, Lazy 로딩일 경우 추가 쿼리 발생 가능
- JPA 영속성 컨텍스트에 연관 엔티티 저장되지 않음

**💡 왜 해결되지 않을까?**
- "JOIN은 단순히 데이터를 합치는 역할만 할 뿐, Lazy 로딩을 해제하지 않기 때문!"

## 3️⃣ FETCH JOIN을 사용하면? 
✅ FETCH JOIN 적용
```java
@Query("SELECT c FROM Customer c JOIN FETCH c.orders")
List<Customer> findAllWithOrders();
```
- 한 번의 쿼리로 연관된 엔티티까지 즉시 로딩
- Lazy 설정이더라도 즉시 데이터를 가져옴
- JPA 영속성 컨텍스트에 연관 엔티티까지 저장됨

## 📌 일반 JOIN vs FETCH JOIN의 차이점

| 구분 | 일반 JOIN | FETCH JOIN |
|------|-----------|-----------|
| **목적** | 단순히 두 테이블을 조인하여 데이터 조회 | 연관된 엔티티를 한 번에 가져오기 |
| **쿼리 실행 방식** | `SELECT c.*, o.* FROM customer c JOIN orders o ON c.id = o.customer_id;` | `SELECT c.*, o.* FROM customer c JOIN FETCH c.orders;` |
| **N+1 문제 발생 여부** | ✅ 발생 가능 (Lazy Loading 시 추가 쿼리 실행) | ❌ 해결됨 (한 번의 쿼리로 모든 데이터 로딩) |
| **JPA 캐시 반영 여부** | 🚨 개별 엔티티로 조회되지 않을 수 있음 | ✅ 연관 엔티티까지 포함하여 JPA 영속성 컨텍스트 반영 |
| **성능** | 조인한 후 필요한 경우 추가 쿼리 실행 → 성능 저하 가능 | 한 번의 쿼리로 모든 데이터 조회 → 성능 향상 |

---

## 🎭 예시: 식당에서 손님과 주문 관리하기

### 🍽️ 1. 일반 JOIN: 알바가 메뉴 주문만 확인하는 경우
- Customer들이 레스토랑에 들어와서 테이블에 앉음
- 알바가 손님 명단을 보고 주문(Order)이 있는 손님을 찾아감
- 한 명씩 주문을 확인하고 주방에서 요리를 요청함

💡 **문제점:**
- 손님 명단을 가져온 후, **손님마다 주문을 따로 가져오는 쿼리**가 실행됨 (N+1 문제 발생)
- 예를 들어, 손님이 100명이면 최대 100번의 추가 쿼리가 실행될 수 있음

---

### 🍽️ 2. FETCH JOIN: 한 번에 손님과 주문을 가져오기
- 이번에는 알바가 손님 명단을 받을 때, **주문 정보까지 한 번에 가져오는 방식**
- 즉, "이 손님은 어떤 주문을 했는지?"를 바로 확인할 수 있음
- 더 이상 추가로 주문을 물어볼 필요 없이, **한 번에 모든 정보를 가져와서 한꺼번에 전달**

💡 **결과:**
- 🚀 **딱 한 번의 쿼리만 실행되므로 빠르다.**
- 🚀 **N+1 문제 발생 ❌**

---

💡 이렇게 하면?
- 고객을 조회하면서 연관된 주문 정보도 한 번에 가져오기 때문에 추가 쿼리가 실행되지 않음!
- 🚀 Lazy 속성이더라도 즉시 로딩됨 → 성능 최적화!

## 🔥 결론: 언제 FETCH JOIN을 써야 할까?

1. **연관된 데이터를 함께 조회해야 하고, N+1 문제가 발생하는 경우**
- @OneToMany 같은 관계에서 Lazy 로딩으로 인해 추가 쿼리가 발생한다면? → FETCH JOIN을 사용!

2. **자주 조회해야 하는 연관 엔티티가 있는 경우**
- 예를 들어, Customer와 Orders를 항상 함께 조회하는 경우 → FETCH JOIN으로 최적화!

3. **JPA 영속성 컨텍스트에 포함해야 하는 경우**
- 일반 JOIN은 데이터베이스에서 조인하는 역할만 하고, JPA 캐시에 저장되지 않음
- 반면 FETCH JOIN은 연관된 엔티티를 한 번에 가져와 영속성 컨텍스트에 저장

👉 즉, **Lazy 로딩으로 인해 발생하는 추가적인 SQL을 줄이고 싶다면** FETCH JOIN으로