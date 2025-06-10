package io.gig.spring6.exrate;

import io.gig.spring6.payment.ExRateProvider;

import java.math.BigDecimal;

/**
 * @author : JAKE
 * @date : 2025/05/25
 */
//@Component
public class SimpleExRateProvider implements ExRateProvider {

    @Override
    public BigDecimal getExRate(String currency) {
        if ("USD".equals(currency)) {
            return BigDecimal.valueOf(1000);
        }
        throw new IllegalArgumentException("지원하지 않는 통화입니다.");
    }
}
