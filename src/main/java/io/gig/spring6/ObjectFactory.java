package io.gig.spring6;

/**
 * @author : JAKE
 * @date : 2025/05/28
 */
public class ObjectFactory {

    public PaymentService paymentService() {
        return new PaymentService(exRateProvider());
    }

    public ExRateProvider exRateProvider() {
        return new WebApiExtRateProvider();
    }
}
