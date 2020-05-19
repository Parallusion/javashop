package jshop.DAO;

import jshop.config.HibernateSessionFactoryUtil;
import jshop.model.Ordered;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OrdersDAOImpl implements OrdersDAO {

    @Override
    @SuppressWarnings("unchecked")
    public List<Ordered> allOrders() {
        List<Ordered> ordereds = (List<Ordered>) HibernateSessionFactoryUtil.getSessionFactory().openSession().createQuery("From Ordered").list();
        return ordereds;
    }

    public void save(Ordered ordered) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(ordered);
        tx1.commit();
        session.close();
    }

    public void delete(Ordered ordered) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.delete(ordered);
        tx1.commit();
        session.close();
    }

    public void update(Ordered ordered) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.update(ordered);
        tx1.commit();
        session.close();
    }

    public Ordered findById(int id) {
        return HibernateSessionFactoryUtil.getSessionFactory().openSession().get(Ordered.class, id);
    }
}
