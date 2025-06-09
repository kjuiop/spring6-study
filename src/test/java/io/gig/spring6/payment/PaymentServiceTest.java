package io.gig.spring6.payment;

import io.gig.spring6.TestPaymentConfig;
import io.gig.spring6.exrate.WebApiExtRateProvider;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import static java.math.BigDecimal.valueOf;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author : JAKE
 * @date : 2025/06/03
 */
class PaymentServiceTest {

    private Clock clock;

    @BeforeEach
    void beforeEach() {
        this.clock = Clock.fixed(Instant.now(), ZoneId.systemDefault());
    }

    @Test
    @DisplayName("prepare 메소드가 요구사항 3가지를 충족했는지 검사")
    void prepare() throws IOException {

        BeanFactory beanFactory = new AnnotationConfigApplicationContext(TestPaymentConfig.class);
        PaymentService paymentService = beanFactory.getBean(PaymentService.class);

        // when
        Payment payment = paymentService.prepare(1L, "USD", BigDecimal.TEN);

        // then
        assertThat(payment.getExRate()).isEqualByComparingTo(valueOf(1_000));
        assertThat(payment.getConvertedAmount()).isEqualByComparingTo(valueOf(10_000));


        assertThat(payment.getValidUntil()).isAfter(LocalDateTime.now());
        assertThat(payment.getValidUntil()).isBefore(LocalDateTime.now().plusMinutes(30));
    }

    @Test
    @DisplayName("prepare 메소드가 요구사항 3가지를 충족했는지 검사")
    void convertedAmount() throws IOException {

        // given
        testAmount(valueOf(500), valueOf(5_000), this.clock);
        testAmount(valueOf(1_000), valueOf(10_000), this.clock);
        testAmount(valueOf(3_000), valueOf(30_000), this.clock);
    }

    private void testAmount(BigDecimal exRate, BigDecimal convertedAmount, Clock clock) throws IOException {

        // given
        PaymentService paymentService = new PaymentService(new ExRateProviderStub(exRate), clock);

        // when
        Payment payment = paymentService.prepare(1L, "USD", BigDecimal.TEN);

        // then
        // BigDecimal 은 소수점 6째자리까지 비교하기 때문에 실패할 가능성이 있다.
        // assertThat(payment.getExRate()).isEqualTo(exRate);
        // assertThat(payment.getConvertedAmount()).isEqualTo(convertedAmount);
        assertThat(payment.getExRate()).isEqualByComparingTo(exRate);
        assertThat(payment.getConvertedAmount()).isEqualByComparingTo(convertedAmount);
    }

    @Test
    void validUntil() throws IOException {
        PaymentService paymentService = new PaymentService(new ExRateProviderStub(valueOf(1000)), this.clock);

        Payment payment = paymentService.prepare(1L, "USD", BigDecimal.TEN);

        // this.clock 은 fixed clock 임으로 30분 더해져있는지에 대한 여부를 검사할 수 있음
        LocalDateTime now = LocalDateTime.now(this.clock);
        LocalDateTime expectedValidUntil = now.plusMinutes(30);

        Assertions.assertThat(payment.getValidUntil()).isEqualTo(expectedValidUntil);
    }


    // WebApiExtRateProvider 서비스에 의존적인 테스트 코드, 외부 시스템에 문제가 생기면?
    // ExRateProvider 가 제공하는 환율 값으로 계산한 것인지 모름
    // 환율 유효 시간 계산이 정확한지 모름
    @Test
    @DisplayName("prepare 메소드가 요구사항 3가지를 충족했는지 검사")
    void prepareByWebApiExtRateProvider() throws IOException {

        // given
        PaymentService paymentService = new PaymentService(new WebApiExtRateProvider(), this.clock);

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