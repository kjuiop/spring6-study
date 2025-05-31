package io.gig.spring6.payment;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author : JAKE
 * @date : 2025/05/24
 */
// abstract 선언으로 getExRate 에 대한 구현은 다른 곳에서 구현하도록 추상화하였다.
@Component
public class PaymentService {

    // final : 한번 할당한 뒤 변경하지 못하게 하는 것
    private final ExRateProvider exRateProvider;

    public PaymentService(ExRateProvider exRateProvider) {
        // 인스터의 구현체를 주입함
        // paymentService -> WebApiExtRateProvider 를 의존하게 되어 있음 (RunTime 에서의 의존관계)
        // RunTime 에 내가 어떤 클래스를 쓰는 건지 필요하기 때문에 의존
        // 이는 생성자를 통해 어떤 구현체를 선택하는지가 나타나있음, 관계 설정 책임의 분리
        // 따라서 구현체는 Client 클래스에서 생성해서 주입해주고, 이 클래스는 인터페이스를 받아서 사용하면 된다.
        // this.exRateProvider = new WebApiExtRateProvider();
        this.exRateProvider = exRateProvider;
    }

    // java 는 HTTP 를 어떻게 다룰까? -> 블로그 주제?
    public Payment prepare(Long orderId, String currency, BigDecimal foreignCurrencyAmount) throws IOException {


        BigDecimal exRate = this.exRateProvider.getExRate(currency);

        // 위의 로직과 아래의 로직은 관심사가 다르다.
        // 위의 로직은 환율 정보를 가져오는 로직으로, 가져오는 API 에 대한 변경에 대한 요인으로 변경된다.
        // 그러나 아래의 로직은 우리 서비스 내에서 환율을 계산하는 로직이 변경될 때 변경된다.
        // 따라서 두 로직은 다른 시점에서 다른 이유로 변경되기 때문에 분리되는 것이 적합하다.

        BigDecimal convertedAmount = foreignCurrencyAmount.multiply(exRate);
        LocalDateTime validUntil = LocalDateTime.now().plusMinutes(30);

        // 상속은 상위 클래스와 하위 클래스가 강하게 결합되어 있음
        // 상속된 클래스가 변경이 되면 분기처리를 위해 여러 클래스들을 만들어야 한다.
        // 예를 들어 환율에 혜택을 부여한다고 가정했을 때, simpleRate, webRate * 혜택 o,x 별로 늘어나게 된다.
        return new Payment(orderId, currency, foreignCurrencyAmount, exRate, convertedAmount, validUntil);
    }
}
