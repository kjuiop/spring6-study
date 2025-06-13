package io.gig.spring6.data;

import io.gig.spring6.order.Order;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

/**
 * @author : JAKE
 * @date : 2025/06/13
 */
public class OrderRepository {

    @PersistenceContext
    private EntityManager em;

    public void save(Order order) {
        em.persist(order);
    }
}
