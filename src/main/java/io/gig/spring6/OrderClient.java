package io.gig.spring6;

import io.gig.spring6.order.Order;
import io.gig.spring6.order.OrderService;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.orm.jpa.JpaTransactionManager;

import java.math.BigDecimal;

/**
 * @author : JAKE
 * @date : 2025/05/25
 */
public class OrderClient {
    public static void main(String[] args) {
        BeanFactory beanFactory = new AnnotationConfigApplicationContext(OrderConfig.class);
        OrderService orderService = beanFactory.getBean(OrderService.class);
        Order order = orderService.createOrder("0100", BigDecimal.TEN);
        System.out.println(order);
    }
}
