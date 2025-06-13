package io.gig.spring6;

import io.gig.spring6.data.JdbcOrderRepository;
import io.gig.spring6.order.OrderRepository;
import io.gig.spring6.order.OrderService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

/**
 * @author : JAKE
 * @date : 2025/06/13
 */
@Configuration
@Import(DataConfig.class)
public class OrderConfig {

    @Bean
    public OrderService orderService(PlatformTransactionManager transactionManager,
                                     OrderRepository orderRepository
                                     ) {
        return new OrderService(orderRepository, transactionManager);
    }

    @Bean
    public OrderRepository orderRepository(DataSource dataSource) {
        return new JdbcOrderRepository(dataSource);
    }
}
