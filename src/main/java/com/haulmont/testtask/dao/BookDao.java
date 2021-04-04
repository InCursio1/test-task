package com.haulmont.testtask.dao;

import com.haulmont.testtask.dao.hibernate.HibernateSessionFactory;
import com.haulmont.testtask.entity.Book;
import org.hibernate.Session;

import javax.persistence.criteria.CriteriaQuery;
import java.util.ArrayList;
import java.util.List;

public class BookDao extends AbstractDao<Book> implements DaoServices<Book> {

    public Book getById(Long id) {
        Session session;
        Book book = null;
        try {
            session = HibernateSessionFactory.getSessionFactory().openSession();
            book = session.load(Book.class, id);
            session.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return book;
    }

    public List<Book> findAll() {
        Session session;
        List<Book> books = new ArrayList<>();
        try {
            session = HibernateSessionFactory.getSessionFactory().openSession();
            CriteriaQuery<Book> criteriaQuery = session.getCriteriaBuilder().createQuery(Book.class);
            criteriaQuery.from(Book.class);
            books = session.createQuery(criteriaQuery).getResultList();
            session.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return books;
    }
}