package com.haulmont.testtask.dao;

import com.haulmont.testtask.dao.hibernate.HibernateSessionFactory;
import com.haulmont.testtask.entity.Author;
import org.hibernate.Session;

import javax.persistence.criteria.CriteriaQuery;
import java.util.ArrayList;
import java.util.List;

public class AuthorDao extends AbstractDao<Author> implements DaoServices<Author> {

    public Author getById(Long id) {
        Session session;
        Author author = null;
        try {
            session = HibernateSessionFactory.getSessionFactory().openSession();
            author = session.load(Author.class, id);
            session.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return author;
    }

    public List<Author> findAll() {
        Session session;
        List<Author> authors = new ArrayList<>();
        try {
            session = HibernateSessionFactory.getSessionFactory().openSession();
            CriteriaQuery<Author> criteriaQuery = session.getCriteriaBuilder().createQuery(Author.class);
            criteriaQuery.from(Author.class);
            authors = session.createQuery(criteriaQuery).getResultList();
            session.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return authors;
    }
}