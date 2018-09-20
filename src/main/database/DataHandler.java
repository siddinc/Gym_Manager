package database;

import database.models.Customer;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

/**
 * Handler provides a simplified interface for interacting with the database.
 */
public class DataHandler {
    private static final String PERSISTENCE_UNIT_NAME = "sample";
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory (PERSISTENCE_UNIT_NAME);

    private EntityManager em;

    public DataHandler () {
        Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml");
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

    protected void setUp() throws {
        // A SessionFactory is set up once for an application!
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder ()
                .configure() // configures settings from hibernate.cfg.xml
                .build();
        try {
            sessionFactory = new MetadataSources ( registry ).buildMetadata().buildSessionFactory();
        }
        catch (Exception e) {
            // The registry would be destroyed by the SessionFactory, but we had trouble building the SessionFactory
            // so destroy it manually.
            StandardServiceRegistryBuilder.destroy( registry );
        }
    }
}
