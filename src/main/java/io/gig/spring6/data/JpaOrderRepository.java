package io.gig.spring6.data;

import io.gig.spring6.order.Order;
import io.gig.spring6.order.OrderRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

/**
 * @author : JAKE
 * @date : 2025/06/13
 */
public class JpaOrderRepository implements OrderRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void save(Order order) {
        em.persist(order);
    }
}
