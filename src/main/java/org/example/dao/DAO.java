package org.example.dao;

import java.util.List;

public interface DAO<T> {
    public void add(T item);
    public void delete(int id);
    public void update(int id, T item);
    public List<T> getAll();
    public T getById(int id);
}
