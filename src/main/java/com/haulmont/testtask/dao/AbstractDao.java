package com.haulmont.testtask.dao;

import com.haulmont.testtask.dao.hibernate.HibernateSessionFactory;
import org.hibernate.Session;

public class AbstractDao<E> {

    public void save(E e) {
        Session session;
        try {
            session = HibernateSessionFactory.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(e);
            session.getTransaction().commit();
            session.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void delete(E e) {
        Session session;
        try {
            session = HibernateSessionFactory.getSessionFactory().openSession();
            session.beginTransaction();
            session.delete(e);
            session.getTransaction().commit();
            session.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void update(E e) {
        Session session;
        try {
            session = HibernateSessionFactory.getSessionFactory().openSession();
            session.beginTransaction();
            session.update(e);
            session.getTransaction().commit();
            session.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
