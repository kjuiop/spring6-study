package io.gig.spring6;

import io.gig.spring6.data.JdbcOrderRepository;
import io.gig.spring6.order.OrderRepository;
import io.gig.spring6.order.OrderService;
import io.gig.spring6.order.OrderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * @author : JAKE
 * @date : 2025/06/13
 */
@Configuration
@Import(DataConfig.class)
@EnableTransactionManagement
public class OrderConfig {

    @Bean
    public OrderService orderService(
            OrderRepository orderRepository
    ) {
        return new OrderServiceImpl(orderRepository);
    }

    @Bean
    public OrderRepository orderRepository(DataSource dataSource) {
        return new JdbcOrderRepository(dataSource);
    }
}
