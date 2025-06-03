package io.gig.spring6;

import io.gig.spring6.payment.ExRateProvider;
import io.gig.spring6.payment.ExRateProviderStub;
import io.gig.spring6.payment.PaymentService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

/**
 * @author : JAKE
 * @date : 2025/05/28
 */
@Configuration
public class TestObjectFactory {

    @Bean
    public PaymentService paymentService() {
        return new PaymentService(exRateProvider());
    }

    @Bean
    public ExRateProvider exRateProvider() {
        return new ExRateProviderStub(BigDecimal.valueOf(1_000));
    }
}
