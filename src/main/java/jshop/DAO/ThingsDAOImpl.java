package jshop.DAO;

import jshop.config.HibernateSessionFactoryUtil;
import jshop.model.Things;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ThingsDAOImpl implements ThingsDAO {

    @Override
    @SuppressWarnings("unchecked")
    //вывод данных из таблицы на страницу
    public List<Things> allThings() {
        List<Things> things = (List<Things>)  HibernateSessionFactoryUtil.getSessionFactory().openSession().createQuery("From Things").list();
        return things;
    }

    public Things findById(int id) {
        return HibernateSessionFactoryUtil.getSessionFactory().openSession().get(Things.class, id);
    }

    public void save(Things things) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(things);
        tx1.commit();
        session.close();
    }
    public void update(Things things) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.update(things);
        tx1.commit();
        session.close();
    }
    public void delete(Things things) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.delete(things);
        tx1.commit();
        session.close();
    }
}
