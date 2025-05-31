package io.gig.spring6;

import io.gig.spring6.exrate.CachedExRateProvider;
import io.gig.spring6.payment.ExRateProvider;
import io.gig.spring6.exrate.WebApiExtRateProvider;
import io.gig.spring6.payment.PaymentService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author : JAKE
 * @date : 2025/05/28
 */
// spring bean 등록
@Configuration
// @ComponentScan
public class ObjectFactory {

    /**
     *
     *     기존에는 생성자를 하나하나 @Bean 으로 선언해야 했지만 ComponentScan 으로 자동으로 스캔해서 빈등록을 진행한다.
     *     빈 등록은 @Component 어노테이션이 선언된 클래스이다.
     *     이 함수는 최초 실행 시 실행되며 어떤 구현체를 주입할 것인지를 결정한다.
     */
    @Bean
    public PaymentService paymentService() {
        return new PaymentService(cachedExRateProvider());
    }

    // cachedExRateProvider 구현체 사용
    @Bean
    public ExRateProvider cachedExRateProvider() {
        return new CachedExRateProvider(exRateProvider(), 3 * 1000);
    }

    // WebApiExtRateProvider 구현체 사용
    @Bean
    public ExRateProvider exRateProvider() {
        return new WebApiExtRateProvider();
    }
}
