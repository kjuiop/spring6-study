package io.gig.spring6.payment;

import io.gig.spring6.TestObjectFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static java.math.BigDecimal.valueOf;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author : JAKE
 * @date : 2025/05/25
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestObjectFactory.class)
public class PaymentServiceSpringTest {

    /**
    // objectFactory 를 빈으로 가져와서 클래스를 선택하는 방식
    @Autowired
    private BeanFactory beanFactory;
     */

    @Autowired
    private PaymentService paymentService;


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
        Payment payment1 = paymentService.prepare(1L, "USD", BigDecimal.TEN);
        assertThat(payment1.getExRate()).isEqualByComparingTo(valueOf(1_000));
        assertThat(payment1.getConvertedAmount()).isEqualByComparingTo(valueOf(10_000));

        /**
         exRateProviderStub.setExRate(valueOf(500));
         Payment payment2 = paymentService.prepare(1L, "USD", BigDecimal.TEN);

         assertThat(payment2.getExRate()).isEqualByComparingTo(valueOf(500));
         assertThat(payment2.getConvertedAmount()).isEqualByComparingTo(valueOf(5_000));
         */

    }
}
