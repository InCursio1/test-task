package com.haulmont.testtask.dao.hibernate;

import com.haulmont.testtask.entity.Author;
import com.haulmont.testtask.entity.Book;
import com.haulmont.testtask.entity.Genre;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;


public class HibernateSessionFactory {
    private static SessionFactory sessionFactory;

    static {
        Configuration cfg = new Configuration().configure();
        cfg.addAnnotatedClass(Author.class);
        cfg.addAnnotatedClass(Genre.class);
        cfg.addAnnotatedClass(Book.class);

        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
                .applySettings(cfg.getProperties());
        sessionFactory = cfg.buildSessionFactory(builder.build());
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}