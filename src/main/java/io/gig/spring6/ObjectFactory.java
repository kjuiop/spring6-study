package io.gig.spring6;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author : JAKE
 * @date : 2025/05/28
 */
// spring bean 등록
@Configuration
@ComponentScan
public class ObjectFactory {

    /**
     *
     *     기존에는 생성자를 하나하나 @Bean 으로 선언해야 했지만 ComponentScan 으로 자동으로 스캔해서 빈등록을 진행한다.
     *     빈 등록은 @Component 어노테이션이 선언된 클래스이다.
     *
     *     @Bean
     *     public PaymentService paymentService() {
     *         return new PaymentService(exRateProvider());
     *     }
     *
     *     @Bean
     *     public ExRateProvider exRateProvider() {
     *         return new SimpleExRateProvider();
     *     }
     */

}
