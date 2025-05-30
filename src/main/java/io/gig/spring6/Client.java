package io.gig.spring6;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

/**
 * @author : JAKE
 * @date : 2025/05/25
 */
public class Client {
    // main 함수로 실제 애플리케이션의 시작 함수를 의미한다.
    public static void main(String[] args) throws IOException, InterruptedException {
        // object factory 를 통해 어떤 구현체를 선택할 책임을 client 가 아닌 objectFactory 에서 다루게 됨
        // object factory -> Spring 의 bean factory 에 빈을 등록하여 사용
        BeanFactory beanFactory = new AnnotationConfigApplicationContext(ObjectFactory.class);
        PaymentService paymentService = beanFactory.getBean(PaymentService.class);

        Payment payment1 = paymentService.prepare(100L, "USD", BigDecimal.valueOf(50.7));
        System.out.println("Payment1: " + payment1);
        System.out.println("-------------------------------------------");

        Payment payment2 = paymentService.prepare(100L, "USD", BigDecimal.valueOf(50.7));
        System.out.println("Payment2: " + payment2);
        System.out.println("-------------------------------------------");

        TimeUnit.SECONDS.sleep(3);

        Payment payment3 = paymentService.prepare(100L, "USD", BigDecimal.valueOf(50.7));
        System.out.println("Payment2: " + payment3);
    }
}
