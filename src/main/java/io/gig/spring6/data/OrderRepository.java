package io.gig.spring6.data;

import io.gig.spring6.order.Order;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;

/**
 * @author : JAKE
 * @date : 2025/06/13
 */
public class OrderRepository {

    private final EntityManagerFactory emf;

    public OrderRepository(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public void save(Order order) {

        // em
        EntityManager em = emf.createEntityManager();

        // transaction
        EntityTransaction transaction = em.getTransaction();

        transaction.begin();

        try {
            em.persist(order);
            em.flush();

            // em.persist
            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }
    }
}
