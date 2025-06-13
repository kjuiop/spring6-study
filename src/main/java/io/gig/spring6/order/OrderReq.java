package io.gig.spring6.order;

import java.math.BigDecimal;

/**
 * @author : JAKE
 * @date : 2025/06/13
 */
public record OrderReq(String no, BigDecimal total) {
}
