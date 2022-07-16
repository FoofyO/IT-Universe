package org.example.dao;

import org.example.models.User;
import org.example.utils.SessionFactoryUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserDAO implements DAO<User>{
    private SessionFactoryUtil sessionFactory;

    @Autowired
    public UserDAO(SessionFactoryUtil sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void add(User item) {
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
        session.delete(session.get(User.class, id));
        transaction.commit();
        session.close();
    }

    @Override
    public void update(int id, User item) {
        item.setId(id);
        Session session = sessionFactory.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.update(item);
        transaction.commit();
        session.close();
    }

    @Override
    public List<User> getAll() {
        Session session = sessionFactory.getSessionFactory().openSession();
        return session.createQuery("from Users").list();
    }

    @Override
    public User getById(int id) {
        Session session = sessionFactory.getSessionFactory().openSession();
        return session.get(User.class, id);
    }
}
