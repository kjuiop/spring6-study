package io.gig.spring6.payment;

import io.gig.spring6.TestObjectFactory;
import io.gig.spring6.exrate.WebApiExtRateProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import static java.math.BigDecimal.valueOf;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author : JAKE
 * @date : 2025/06/03
 */
class PaymentServiceTest {

    @Test
    @DisplayName("prepare 메소드가 요구사항 3가지를 충족했는지 검사")
    void prepare() throws IOException {

        BeanFactory beanFactory = new AnnotationConfigApplicationContext(TestObjectFactory.class);
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
        testAmount(valueOf(500), valueOf(5_000));
        testAmount(valueOf(1_000), valueOf(10_000));
        testAmount(valueOf(3_000), valueOf(30_000));
    }

    private void testAmount(BigDecimal exRate, BigDecimal convertedAmount) throws IOException {

        // given
        PaymentService paymentService = new PaymentService(new ExRateProviderStub(exRate));

        // when
        Payment payment = paymentService.prepare(1L, "USD", BigDecimal.TEN);

        // then
        // BigDecimal 은 소수점 6째자리까지 비교하기 때문에 실패할 가능성이 있다.
        // assertThat(payment.getExRate()).isEqualTo(exRate);
        // assertThat(payment.getConvertedAmount()).isEqualTo(convertedAmount);
        assertThat(payment.getExRate()).isEqualByComparingTo(exRate);
        assertThat(payment.getConvertedAmount()).isEqualByComparingTo(convertedAmount);
    }


    // WebApiExtRateProvider 서비스에 의존적인 테스트 코드, 외부 시스템에 문제가 생기면?
    // ExRateProvider 가 제공하는 환율 값으로 계산한 것인지 모름
    // 환율 유효 시간 계산이 정확한지 모름
    @Test
    @DisplayName("prepare 메소드가 요구사항 3가지를 충족했는지 검사")
    void prepareByWebApiExtRateProvider() throws IOException {

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