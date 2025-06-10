package io.gig.spring6.payment;

import java.math.BigDecimal;

/**
 * @author : JAKE
 * @date : 2025/05/27
 */
public interface ExRateProvider {
    BigDecimal getExRate(String currency);
}
