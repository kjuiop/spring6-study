package io.gig.spring6;

import io.gig.spring6.data.OrderRepository;
import io.gig.spring6.order.Order;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.math.BigDecimal;

/**
 * @author : JAKE
 * @date : 2025/05/25
 */
public class DataClient {
    public static void main(String[] args) {
        BeanFactory beanFactory = new AnnotationConfigApplicationContext(DataConfig.class);
        OrderRepository orderRepository = beanFactory.getBean(OrderRepository.class);

        Order order = new Order("100", BigDecimal.TEN);
        orderRepository.save(order);

        System.out.println(order);

        Order order2 = new Order("100", BigDecimal.TEN);
        orderRepository.save(order2);

        System.out.println(order2);

    }
}
