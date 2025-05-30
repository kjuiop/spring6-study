package io.gig.spring6.payment;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author : JAKE
 * @date : 2025/05/24
 */
public class Payment {

    // 주문번호
    private Long orderId;

    // 외국통화종류
    private String currency;

    // double, float 은 계산의 오차가 생길 수 있다.
    // 부동소수점 계산 -> 정확히 어떤 개념?
    // 외국 통화 기준 결제 금액
    private BigDecimal foreignCurrencyAmount;

    // 적용 환율
    private BigDecimal exRate;

    // 원화 환산 금액
    private BigDecimal convertedAmount;

    // 원화 환산 금액 유효시간
    private LocalDateTime validUntil;

    // 생성자의 경우 파라미터의 데이터 타입이 같은 경우 그 값이 변경되어도 알림을 주지 않음
    // 이를 방지하기 위해서는 Lombok 의 builder 패턴을 활용하면 명시적으로 데이터를 넣기 때문에 좋음
    public Payment(Long orderId, String currency, BigDecimal foreignCurrencyAmount, BigDecimal exRate, BigDecimal convertedAmount, LocalDateTime validUntil) {
        this.orderId = orderId;
        this.currency = currency;
        this.foreignCurrencyAmount = foreignCurrencyAmount;
        this.exRate = exRate;
        this.convertedAmount = convertedAmount;
        this.validUntil = validUntil;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "orderId=" + orderId +
                ", currency='" + currency + '\'' +
                ", foreignCurrencyAmount=" + foreignCurrencyAmount +
                ", exRate=" + exRate +
                ", convertedAmount=" + convertedAmount +
                ", validUntil=" + validUntil +
                '}';
    }
}
