package com.haulmont.testtask.dao;

import com.haulmont.testtask.dao.hibernate.HibernateSessionFactory;
import com.haulmont.testtask.entity.Genre;
import org.hibernate.Session;

import javax.persistence.criteria.CriteriaQuery;
import java.util.ArrayList;
import java.util.List;

public class GenreDao extends AbstractDao<Genre> implements DaoServices<Genre>{
    @Override
    public Genre getById(Long id) {
        Session session;
        Genre genre = null;
        try {
            session = HibernateSessionFactory.getSessionFactory().openSession();
            genre = session.load(Genre.class, id);
            session.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return genre;
    }

    @Override
    public List<Genre> findAll() {
        Session session;
        List<Genre> genres = new ArrayList<>();
        try {
            session = HibernateSessionFactory.getSessionFactory().openSession();
            CriteriaQuery<Genre> criteriaQuery = session.getCriteriaBuilder().createQuery(Genre.class);
            criteriaQuery.from(Genre.class);
            genres = session.createQuery(criteriaQuery).getResultList();
            session.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return genres;
    }
}