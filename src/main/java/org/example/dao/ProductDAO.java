package org.example.dao;

import org.example.models.Product;
import org.example.utils.SessionFactoryUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.print.attribute.standard.PrinterURI;
import java.util.List;

@Component
public class ProductDAO implements DAO<Product> {
    private SessionFactoryUtil sessionFactory;

    @Autowired
    public ProductDAO(SessionFactoryUtil sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void add(Product item) {
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
        session.delete(session.get(Product.class, id));
        transaction.commit();
        session.close();
    }

    @Override
    public void update(int id, Product item) {
        item.setId(id);
        Session session = sessionFactory.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.update(item);
        transaction.commit();
        session.close();
    }

    @Override
    public List<Product> getAll() {
        Session session = sessionFactory.getSessionFactory().openSession();
        return session.createQuery("from Products").list();
    }

    @Override
    public Product getById(int id) {
        Session session = sessionFactory.getSessionFactory().openSession();
        return session.get(Product.class, id);
    }
}
