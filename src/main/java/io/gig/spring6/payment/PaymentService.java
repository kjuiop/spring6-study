package io.gig.spring6.payment;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDateTime;

/**
 * @author : JAKE
 * @date : 2025/05/24
 */
@Component
public class PaymentService {

    private final ExRateProvider exRateProvider;
    private final Clock clock;

    public PaymentService(ExRateProvider exRateProvider, Clock clock) {
        this.exRateProvider = exRateProvider;
        this.clock = clock;
    }

    // java 는 HTTP 를 어떻게 다룰까? -> 블로그 주제?
    public Payment prepare(Long orderId, String currency, BigDecimal foreignCurrencyAmount) {
        BigDecimal exRate = this.exRateProvider.getExRate(currency);
        return Payment.createPrepare(orderId, currency, foreignCurrencyAmount, exRate, LocalDateTime.now(clock));
    }
}
