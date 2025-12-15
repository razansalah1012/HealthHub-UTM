package com.secj3303.dao;

import com.secj3303.model.Category;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CategoryDaoHibernate implements CategoryDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<Category> findAll() {
        Session s = sessionFactory.openSession();
        List<Category> list = s.createQuery("FROM Category", Category.class).list();
        s.close();
        return list;
    }

    @Override
    public void save(Category category) {
        Session s = sessionFactory.openSession();
        s.beginTransaction();
        s.save(category);
        s.getTransaction().commit();
        s.close();
    }
}
