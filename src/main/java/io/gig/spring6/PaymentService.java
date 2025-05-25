package io.gig.spring6;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author : JAKE
 * @date : 2025/05/24
 */
// abstract 선언으로 getExRate 에 대한 구현은 다른 곳에서 구현하도록 추상화하였다.
public class PaymentService {

    // final : 한번 할당한 뒤 변경하지 못하게 하는 것
    private final WebApiExtRateProvider exRateProvider;

    public PaymentService() {
        this.exRateProvider = new WebApiExtRateProvider();
    }

    // java 는 HTTP 를 어떻게 다룰까? -> 블로그 주제?
    public Payment prepare(Long orderId, String currency, BigDecimal foreignCurrencyAmount) throws IOException {


        BigDecimal exRate = this.exRateProvider.getWebExRate(currency);

        // 위의 로직과 아래의 로직은 관심사가 다르다.
        // 위의 로직은 환율 정보를 가져오는 로직으로, 가져오는 API 에 대한 변경에 대한 요인으로 변경된다.
        // 그러나 아래의 로직은 우리 서비스 내에서 환율을 계산하는 로직이 변경될 때 변경된다.
        // 따라서 두 로직은 다른 시점에서 다른 이유로 변경되기 때문에 분리되는 것이 적합하다.

        BigDecimal convertedAmount = foreignCurrencyAmount.multiply(exRate);
        LocalDateTime validUntil = LocalDateTime.now().plusMinutes(30);

        return new Payment(orderId, currency, foreignCurrencyAmount, exRate, convertedAmount, validUntil);
    }
}
