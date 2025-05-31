# spring 6 

## 오브젝트와 의존관계

- 스프링의 관심은 오브젝트이다.
- 클래스와 오브젝트는 다르다.
- 자바의 오브젝트는 클래스의 인스턴스 또는 배열이다.

### OOP

- OOP (Object-Oriented Programming) : 객체 지향 프로그래밍
- 객체 (Object) : 데이터(속성)와 행동(메서드)을 가진 독립된 단위
    - 실제 프로그램에서 독립된 단위로서 행동하는 것
    - 클래스의 인스턴스 = Object, 객체
    - 자바에서는 배열(Array) 도 오브젝트이다.
- 클래스 (Class) : 객체를 만들어내기 위한 설계도, 객체는 클래스의 인스턴스
    - Object 를 만들기 위한 것
    - 프로그래머가 코딩하는 단위, 클래스 파일
- 캡슐화 (Encapsulation) : 데이터와 메서드를 하나로 묶고, 외부에서 직접 접근하지 못하게 보호
- 상속 (Inheritance) : 기존 클래스의 기능을 물려받아 새로운 클래스를 만들 수 있음
- 다형성 (Polymorphism) : 같은 이름의 메서드가 상황에 따라 다르게 동작할 수 있음

### 다형성의 예시

```jsx
class Animal {
    void sound() {
        System.out.println("동물이 소리를 낸다");
    }
}

class Dog extends Animal {
    void sound() {
        System.out.println("멍멍!");
    }
}

```

### 의존관계

- A와 B가 있을 때, A가 B에 의존한다 라고 하게 됨
- Client → Supplier
    - Client 의 기능이 제대로 동작하려면 Supplier 가 필요
    - Client 가 Supplier 를 사용, 호출, 생성, 인스턴스화, 전송
    - 클래스 레벨(코드 레벨) 의 의존관계, Supplier 가 변경되면 Client 코드가 영향을 받는다.
    - *클래스 레벨의 의존관계와 런타임 때의 의존관계가 다를 수 있다.*
- 의존관계는 반드시 두 개 이상의 대상이 존재하고, 하나가 다른 하나를 사용, 호출, 생성, 인스턴스화, 전송 등을 할 때 의존관계에 있다고 이야기한다.
- 클래스 사이에 의존관계가 있을 때 의존 대상이 변경되면 이를 사용하는 클래스의 코드도 영향을 받는다.
- 오브젝트 사이에 의존관계는 실행되는 런타임에 의존관계가 만들어지고 실행 기능에 영향을 받지만 클래스 레벨의 의존관계는 다를 수 있다.
- https://www.ibm.com/docs/en/dmrt/9.5?topic=diagrams-dependency-relationships

<br />

### 관심사의 분리

- 관심사는 동일한 이유로 변경되는 코드의 집합
- API 를 이용해서 환율정보를 가져오고 JSON 을 오브젝트에 매핑하는 관심과 Payment 를 준비하는 로직은 관심이 다르다. 변경의 이유와 시점을 살펴보고 이를 분리한다.

<br />

### 메소드 추출 리팩터링

- https://refactoring.guru/extract-method
- https://martinfowler.com/books/refactoring.html
- https://refactoring.guru/ko/design-patterns

<br />

### 기존

![Image](images/a1.png)

- PaymentService 는 환율 정보를 가져와 계산하는 비즈니스 로직을 가지고 있다.
- getExRate() 는 고정 환율을 사용할 것인지, 외부 API 의 환율정보를 가져와 사용할 것인지 경우가 나눠진다.
- 이때 if 문으로 분기처리, 상속으로 다중 클래스가 아닌 인터페이스 추상화를 사용하였다.
- 그리고 추상화 때 구현체를 결정하고 생성하는 주체는 PaymentService 가 아닌 ObjectFactory 에서 결정한다.
- ObjectFactory 는 Client 클래스에서 애플리케이션을 구동할 때 생성되며, 그때 작성된 코드 기반으로 구현체를 선택한다.


<br />

### 스프링

![Image](images/a2.png)

### 스프링의 빈 팩토리

- Bean
  - Java 의 빈, 자바에서 컴포넌트 오브젝트를 의미하며, 오브젝트라고 생각해도 된다.
  - 애플리케이션을 구성하는 기능을 담당하고 제공하는 오브젝트를 의미
  - 오브젝트 팩토리 = 빈 팩토리 → 스프링의 빈 팩토리를 사용
- Spring 의 Bean Factory
  - 스프링의 빈 팩토리는 애플리케이션 내부 로직 (PaymentService) 와 같은 로직을 알지 못한다.
- 위의 ObjectFactory 를 Spring Bean 으로 등록한다.
- Client 가 애플리케이션을 실행할 때 Spring 에서 알아서 빈으로 등록된 객체를 생성하고 주입시켜준다.
- 실제 WebApiExRateProvider 를 생성하고자 할 때 PaymentService 에서 객체를 생성하고 사용한 것이 아닌 외부의 스프링에서 생성되고 주입받았기 때문에 제어의 역전이라 한다.
- DI 가 필요한 이유는 매번 요청이 들어올때마다 클래스 객체를 생성하게 되면 대용량 트래픽이 왔을 때의 자원 낭비가 심하다. 그렇기 때문에 애플리케이션에서 처음 시작할 때 필요한 객체들은 단 한번만 생성해서 스프링 빈 팩토리에 등록하고, 그 이후에 사용하는 것이 효율적이기 때문이다.
  DI 는 이러한 구조를 프레임워크 툴로서 제시한다.
- PaymentService , WebApiExRateProvider 에서는 어떤 상태 정보를 저장하지 않기 때문에 여러 쓰레드에서 동시에 빈에 접근해도 괜찮다. 그렇기 때문에 Object 를 하나만 만들어서 사용해도 된다.
- 서블릿 : 서블릿 컨테이너 위에서 만들어지고 사용된다.
  - 톰캣으로부터 요청이 들어옴
  - 서블릿 컨테이너에서 오브젝트를 가져와서 요청을 수행한다.

<br />

### POJO

![Image](images/a3.png)


- POJO (Plain Old Java Object)
  - 아무런 특별한 규약이나 프레임워크에 의존하지 않는 순수한 Java 객체
  - 단순한 필드, getter/setter, 생성자, 메서드만 가짐
  - User, Order, Product 등 도메인 객체

- Business 로직을 개발한 클래스는 순수 Java 객체로 만듬
- 실제 런타임에서 의존성을 주입하는 부분은 스프링 프레임워크 기능을 사용함
- POJO 와 Spring Framework 부분이 결합되서 실제 운용되는 애플리케이션이 됨

<br />

### 싱글톤 레지스트리 (Singleton Registry)

- 스프링이 스스로의 정체성을 싱글톤 레지스트리
- 애플리케이션에서 하나의 오브젝트만 생성해서 공유하는 경우


<br />

### DI와 디자인패턴


![Image](images/a4.png)


- Design Patterns : Elements of Reusable Object-Oriented Software

- 패턴을 목적과 범위로 구분함
  - Class : 상속 → 클래스 상속을 통한 확장은 클래스가 n*n 으로 많아지는 단점이 있음
  - Object : 합성 → Object 2개를 만들어서 Runtime 환경에 의존관계를 주입하는 방법

- Object 합성을 이용하는 디자인 패턴을 적용할 때 스프링의 의존관계 주입(Dependency Injection)을 사용

### 환율 정보가 필요할 때 매번 Web API 를 호출해야 할까?

- 환율 정보가 필요한 기능 증가
- 응답시간
- 환율 변동 주기

### WebApiExRateProvider 에 캐시 기능 추가

- A) WebApiExRateProvider 코드 수정
- B) 데코레이터(Decorator) 디자인 패턴
  - 오브젝트에 부가적인 기능/책임을 동적으로 부여한다.

![Image](images/a5.png)


<br />

## 의존성 역전 원칙 (Dependency Inversion Principle )

- ***상위 수준의 모듈은 하위 수준의 모듈에 의존해서는 안된다.*** 둘 모두 추상화에 의존해야 한다.
- 추상화는 구체적인 사항에 의존해서는 안된다. 구체적인 사항은 추상화에 의존해야 한다.
- 모듈은 패키지 단위로 구분될 수 있고, Jar 파일 단위로도 구분될 수 있다.

![Image](images/a6.png)

- 상위 모듈인 PaymentService 도 인터페이스를 통해 주입받고 있고, 하위 모듈인 WebApiExRateProvider 도 인터페이스를 통해 주입받고 있다.
- 그러나 현재 소스 상 PaymentService 에서는 하위 모듈의 ExRateProvider 인터페이스를 의존하고 있기 때문에 의존성 역전 원칙에 위배된다.
- 따라서 인터페이스 소유권의 역전이 필요하다.
  - Separated Interface 패턴

![Image](images/a7.png)

- 인터페이스 소유권을 상위모듈로 이전한 그림
- 상위 모듈은 하위 모듈에 의존하지 않을 수 있게 됨
- 상위 모듈에서 구현이 필요한 메서드를 인터페이스를 통해 제시할 수 있게 됨으로써 하위모듈 제작에 도움을 줌

<br />