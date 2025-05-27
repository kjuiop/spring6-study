package io.gig.spring6;

import java.io.IOException;
import java.math.BigDecimal;

/**
 * @author : JAKE
 * @date : 2025/05/25
 */
public class Client {
    // main 함수로 실제 애플리케이션의 시작 함수를 의미한다.
    public static void main(String[] args) throws IOException {
        PaymentService paymentService = new PaymentService(new WebApiExtRateProvider());
        Payment payment = paymentService.prepare(100L, "USD", BigDecimal.valueOf(50.7));
        System.out.println(payment);
    }
}
