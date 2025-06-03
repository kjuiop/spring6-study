package io.gig.spring6.payment;

import java.math.BigDecimal;

/**
 * @author : JAKE
 * @date : 2025/06/04
 */
public record ExRateTestCase(BigDecimal exRate, BigDecimal expectedConverted) {
}
