package io.gig.spring6.payment;

import io.gig.spring6.TestPaymentConfig;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;

import static java.math.BigDecimal.valueOf;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author : JAKE
 * @date : 2025/05/25
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestPaymentConfig.class)
public class PaymentServiceSpringTest {

    /**
    // objectFactory 를 빈으로 가져와서 클래스를 선택하는 방식
    @Autowired
    private BeanFactory beanFactory;
     */

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private Clock clock;


    // TestFactory 의 개념은 적용할 수 없나?
    // 구현체를 가져와서 set 으로 바꿔주는 건 별로 안좋은 방법으로 보이는데..
    /**
     @Autowired
     private ExRateProviderStub exRateProviderStub;
     */

    @Test
    void convertedAmount() throws IOException {

        // objectFactory 를 생성하는 것에서 Spring 의 Bean 을 통해 주입받는 것으로 변경
        // PaymentService paymentService = beanFactory.getBean(PaymentService.class);
        Payment payment1 = this.paymentService.prepare(1L, "USD", BigDecimal.TEN);
        assertThat(payment1.getExRate()).isEqualByComparingTo(valueOf(1_000));
        assertThat(payment1.getConvertedAmount()).isEqualByComparingTo(valueOf(10_000));

        /**
         exRateProviderStub.setExRate(valueOf(500));
         Payment payment2 = paymentService.prepare(1L, "USD", BigDecimal.TEN);

         assertThat(payment2.getExRate()).isEqualByComparingTo(valueOf(500));
         assertThat(payment2.getConvertedAmount()).isEqualByComparingTo(valueOf(5_000));
         */

    }

    @Test
    void validUntil() throws IOException {

        Payment payment = this.paymentService.prepare(1L, "USD", BigDecimal.TEN);

        // this.clock 은 fixed clock 임으로 30분 더해져있는지에 대한 여부를 검사할 수 있음
        LocalDateTime now = LocalDateTime.now(this.clock);
        LocalDateTime expectedValidUntil = now.plusMinutes(30);

        Assertions.assertThat(payment.getValidUntil()).isEqualTo(expectedValidUntil);
    }

    @Test
    void convertedAmountTestRecord() throws IOException {

        List<ExRateTestCase> testCases = List.of(
                new ExRateTestCase(valueOf(1000), valueOf(10_000)),
                new ExRateTestCase(valueOf(500), valueOf(5_000))
        );

        for (ExRateTestCase tc : testCases) {
            ExRateProviderStub stub = new ExRateProviderStub(tc.exRate());
            PaymentService paymentService = new PaymentService(stub, this.clock);

            Payment payment = paymentService.prepare(1L, "USD", BigDecimal.TEN);

            assertThat(payment.getExRate()).isEqualByComparingTo(tc.exRate());
            assertThat(payment.getConvertedAmount()).isEqualByComparingTo(tc.expectedConverted());
        }
    }

    @Test
    void convertedAmountTestInnerClass() throws IOException {

        // 함수 안에서 직접 테스트 케이스 타입 정의 (inner 클래스)
        class TestCase {
            final BigDecimal exRate;
            final BigDecimal expectedExRate;
            final BigDecimal expectedConverted;

            TestCase(BigDecimal exRate, BigDecimal expectedExRate, BigDecimal expectedConverted) {
                this.exRate = exRate;
                this.expectedExRate = expectedExRate;
                this.expectedConverted = expectedConverted;
            }
        }

        List<TestCase> testCases = List.of(
                new TestCase(valueOf(1000), valueOf(1000), valueOf(10_000)),
                new TestCase(valueOf(500), valueOf(500), valueOf(5_000))
        );

        for (TestCase tc : testCases) {

            ExRateProviderStub stub = new ExRateProviderStub(tc.exRate);
            PaymentService paymentService = new PaymentService(stub, this.clock);

            Payment payment = paymentService.prepare(1L, "USD", BigDecimal.TEN);

            assertThat(payment.getExRate()).isEqualByComparingTo(tc.expectedExRate);
            assertThat(payment.getConvertedAmount()).isEqualByComparingTo(tc.expectedConverted);
        }
    }
}
