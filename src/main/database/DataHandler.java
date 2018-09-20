package database;

import database.models.Customer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Handler provides a simplified interface for interacting with the database.
 */
public class DataHandler {
    private static final String PERSISTENCE_UNIT_NAME = "sample";
    private static SessionFactory sf = HibernateUtils.getSessionFactory ();

    private Session session;

    public DataHandler () {
        session = sf.openSession ();
    }

    public void addCustomer (Customer c) {
        Transaction tx = session.beginTransaction ();
        session.persist (c);
        tx.commit ();
    }

    public List<Customer> getCustomerList () {
        Query q = session.createQuery ("from CUSTOMERS");

        Iterator<Customer> it = q.iterate ();
        List<Customer> csl = new ArrayList<Customer> ();
        while (it.hasNext ()) csl.add (it.next ());

        return csl;
    }

}
