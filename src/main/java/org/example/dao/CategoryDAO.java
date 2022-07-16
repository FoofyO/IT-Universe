package org.example.dao;

import org.example.models.Category;
import org.example.utils.SessionFactoryUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CategoryDAO implements DAO<Category> {
    private SessionFactoryUtil sessionFactory;

    @Autowired
    public CategoryDAO(SessionFactoryUtil sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void add(Category item) {
        Session session = sessionFactory.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.save(item);
        transaction.commit();
        session.close();
    }

    @Override
    public void delete(int id) {
        Session session = sessionFactory.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(session.get(Category.class, id));
        transaction.commit();
        session.close();
    }

    @Override
    public void update(int id, Category item) {
        item.setId(id);
        Session session = sessionFactory.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.update(item);
        transaction.commit();
        session.close();
    }

    @Override
    public List<Category> getAll() {
        Session session = sessionFactory.getSessionFactory().openSession();
        return session.createQuery("from Categories").list();
    }

    @Override
    public Category getById(int id) {
        Session session = sessionFactory.getSessionFactory().openSession();
        return session.get(Category.class, id);
    }
}
