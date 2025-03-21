# Java, Spring, JPA 면접 질문 및 답변 정리

## Java

### JVM의 구조와 Java의 실행 방식
JVM(Java Virtual Machine)은 Java 프로그램을 실행하는 가상 머신이다. 주요 구성 요소는 다음과 같다.
1. **Class Loader**: 바이트코드를 메모리에 로드
2. **Runtime Data Area**:
   - **Method Area**: 클래스 정보, static 변수 저장
   - **Heap**: 객체 저장
   - **Stack**: 메서드 호출 스택
   - **PC Register**: 현재 실행 중인 명령어 주소 저장
   - **Native Method Stack**: 네이티브 코드 실행
3. **Execution Engine**: 바이트코드를 해석하여 실행 (JIT 컴파일 포함)
4. **Garbage Collector**: 사용하지 않는 객체를 자동으로 제거

Java 프로그램 실행 과정:
1. Java 소스코드를 `.java` 파일로 작성
2. `javac` 컴파일러가 바이트코드(`.class`)로 변환
3. JVM이 `.class` 파일을 로드하고 실행

---

### GC(Garbage Collection)
GC는 JVM이 메모리에서 사용하지 않는 객체를 자동으로 정리하는 기능이다.

- **필요한 이유**:
  - 개발자가 직접 메모리를 해제하는 부담을 줄임
  - 메모리 누수를 방지
  - 안정적인 프로그램 실행 가능

- **동작 방식**:
  1. **Mark**: 사용 중인 객체 식별
  2. **Sweep**: 사용되지 않는 객체 제거
  3. **Compact**: 메모리 단편화 해소

- **GC 알고리즘 종류**:
  - Serial GC, Parallel GC, G1 GC, ZGC

---

### 컬렉션 프레임워크
자바 컬렉션 프레임워크는 데이터 저장 및 조작을 위한 자료구조 및 알고리즘을 제공한다.

| 인터페이스 | 특징 | 대표 구현체 |
|------------|----------|-------------|
| List | 순서 유지, 중복 허용 | ArrayList, LinkedList |
| Set | 중복 허용X, 순서 유지X | HashSet, TreeSet |
| Map | 키-값 저장, 키 중복X | HashMap, TreeMap |
| Queue | FIFO 구조 | LinkedList, PriorityQueue |

---

### 제네릭(Generic)
컴파일 시 타입을 체크하여 타입 안정성을 제공하는 기능이다. 타입 캐스팅을 줄이고, 재사용성을 높이는 장점이 있다.

---

### 애노테이션(Annotation)
애노테이션은 메타데이터를 제공하여 컴파일러나 런타임에서 특정 동작을 수행하도록 한다.

- `@Override`: 메서드 오버라이딩 체크
- `@Deprecated`: 사용 중지된 코드 표시
- `@SuppressWarnings`: 경고 억제

---

### 오버라이딩(Overriding)과 오버로딩(Overloading)의 차이
- **오버라이딩**: 부모 클래스로부터 상속받은 메서드를 재정의하는 것
- **오버로딩**: 같은 이름의 메서드를 파라미터 타입 또는 개수를 다르게 정의하는 것

---

### 인터페이스 vs 추상 클래스
|  | 인터페이스 | 추상 클래스 |
|---|---|---|
| 다중 상속 | 가능 | 불가능 |
| 메서드 구현 | 모든 메서드는 기본적으로 추상 메서드 | 일부 메서드 구현 가능 |
| 필드 | 상수만 가질 수 있음 | 인스턴스 변수 가질 수 있음 |

---

### 객체지향 5대 원칙(SOLID)
1. **SRP (단일 책임 원칙)**: 클래스는 단 하나의 책임만 가져야 한다.
2. **OCP (개방-폐쇄 원칙)**: 확장에는 열려있고, 수정에는 닫혀 있어야 한다.
3. **LSP (리스코프 치환 원칙)**: 하위 클래스는 부모 클래스를 대체할 수 있어야 한다.
4. **ISP (인터페이스 분리 원칙)**: 특정 클라이언트에 의존하는 인터페이스를 분리해야 한다.
5. **DIP (의존성 역전 원칙)**: 추상화된 인터페이스에 의존해야 하며, 구체적인 구현체에 의존하지 않아야 한다.

---

## Spring

### DI/IoC 개념과 동작 방식
- **IoC (Inversion of Control)**: 객체의 생성과 의존성 관리를 개발자가 직접 하는 것이 아니라 컨테이너(Spring)가 관리하는 방식
- **DI (Dependency Injection)**: 객체 간의 의존성을 컨테이너가 주입하여 결합도를 낮춤

---

### Spring Bean이란?
- 스프링 컨테이너에서 관리되는 객체
- `@Component`, `@Service`, `@Repository`, `@Controller` 등으로 등록

---

### Spring Web MVC의 Dispatcher Servlet 동작 원리
1. 클라이언트 요청을 `DispatcherServlet`이 수신
2. `HandlerMapping`을 통해 해당 요청을 처리할 컨트롤러 탐색
3. 컨트롤러가 요청을 처리한 후 `ViewResolver`를 통해 View 반환
4. 클라이언트에게 응답 전송

---

### Servlet Filter vs Spring Interceptor
|  | Filter | Interceptor |
|---|---|---|
| 적용 범위 | 모든 요청 (Servlet 수준) | Spring MVC 내부 요청 |
| 실행 시점 | 요청 전/후 | Controller 실행 전/후 |

---

## JPA

### 영속성 컨텍스트의 이점
1. 1차 캐시 제공 (같은 트랜잭션 내 같은 엔티티 재사용)
2. 변경 감지 (Dirty Checking)
3. 지연 로딩 (Lazy Loading)
4. 트랜잭션 단위의 작업 관리
5. 자동 변경 반영 (Flush)

---

### N+1 문제와 해결 방법
- **발생 이유**: 연관된 엔티티를 조회할 때 추가적인 쿼리가 발생하는 문제
- **해결 방법**:
  - `@EntityGraph`
  - `fetch join` 사용 (`LEFT JOIN FETCH`)
  - `@BatchSize`

---

### JPA Propagation 전파 단계
- `REQUIRED` (기본값): 기존 트랜잭션이 있으면 참여, 없으면 새로 생성
- `REQUIRES_NEW`: 무조건 새로운 트랜잭션 생성
- `SUPPORTS`: 트랜잭션이 있으면 참여, 없으면 없이 실행
- `NOT_SUPPORTED`: 트랜잭션 없이 실행
- `MANDATORY`: 반드시 트랜잭션 내에서 실행
- `NEVER`: 트랜잭션이 존재하면 예외 발생
- `NESTED`: 중첩 트랜잭션 사용

---

## 추가 개념

### 강한 결합 vs 느슨한 결합
- **강한 결합**: 직접 객체를 생성하여 의존하는 방식 (ex. `new`)
- **느슨한 결합**: 인터페이스와 DI를 사용하여 의존성 제거

---

### 직렬화(Serialization)와 역직렬화(Deserialization)
- **직렬화**: 객체를 바이트 스트림으로 변환하는 과정
- **역직렬화**: 바이트 스트림을 다시 객체로 변환하는 과정

---

### Checked vs Unchecked Exception
- **Checked Exception**: 반드시 `try-catch` 또는 `throws`로 처리해야 함 (ex. IOException)
- **Unchecked Exception**: 런타임 예외, 명시적 예외 처리 필요 없음 (ex. NullPointerException)

---
