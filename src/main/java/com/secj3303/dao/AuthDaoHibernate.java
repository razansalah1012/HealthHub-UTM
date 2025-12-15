package com.secj3303.dao;

import com.secj3303.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AuthDaoHibernate implements AuthDao {

    @Autowired
    private SessionFactory sessionFactory;

@Override
public User login(String email, String password) {
    Session s = sessionFactory.openSession();

    User user = s.createQuery(
        "FROM User WHERE email = :e AND password = :p", User.class)
        .setParameter("e", email)
        .setParameter("p", password)
        .uniqueResult();

    s.close();
    return user;
}

@Override
public boolean emailExists(String email) {
    Session s = sessionFactory.openSession();
    Long count = s.createQuery(
        "SELECT COUNT(u) FROM User u WHERE u.email = :e", Long.class)
        .setParameter("e", email)
        .uniqueResult();
    s.close();
    return count > 0;
}

@Override
public void register(User user) {
    Session s = sessionFactory.openSession();
    s.beginTransaction();
    s.save(user);
    s.getTransaction().commit();
    s.close();
}

}
