package io.gig.spring6.payment;

import io.gig.spring6.exrate.WebApiExtRateProvider;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author : JAKE
 * @date : 2025/06/03
 */
class PaymentServiceTest {

    @Test
    @DisplayName("prepare 메소드가 요구사항 3가지를 충족했는지 검사")
    void prepare() throws IOException {

        // given
        PaymentService paymentService = new PaymentService(new WebApiExtRateProvider());

        // when
        Payment payment = paymentService.prepare(1L, "USD", BigDecimal.TEN);

        // then

        // 환율정보를 가져온다.
        assertThat(payment.getExRate()).isNotNull();

        // 원화환산 금액 계산
        // 환율 * 외환금액 = 원화환산금액
        assertThat(payment.getConvertedAmount())
                .isEqualTo(payment.getExRate().multiply(payment.getForeignCurrencyAmount()));

        // 원화환산금액의 유효시간
        assertThat(payment.getValidUntil()).isAfter(LocalDateTime.now());
        assertThat(payment.getValidUntil()).isBefore(LocalDateTime.now().plusMinutes(30));
    }
}