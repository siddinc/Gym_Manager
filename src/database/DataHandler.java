package database;

import database.models.Customer;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

/**
 * Handler provides a simplified interface for interacting with the database.
 */
public class DataHandler {
    private static final String PERSISTENCE_UNIT_NAME = "sqlite";
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory (PERSISTENCE_UNIT_NAME);

    private EntityManager em;

    public DataHandler () {
        em = emf.createEntityManager ();
    }

    public void addCustomer (Customer c) {
        em.getTransaction ().begin ();
        em.persist (c);
        em.getTransaction ().commit ();
    }

    public List<Customer> getCustomerList () {
        List<Customer> res = em.createNamedQuery ("Customer.findAll").getResultList ();
        return res;
    }
}
