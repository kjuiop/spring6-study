package io.gig.spring6.order;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author : JAKE
 * @date : 2025/06/13
 */
public interface OrderService {

    Order createOrder(String no, BigDecimal total);

    List<Order> createOrders(List<OrderReq> reqs);
}
