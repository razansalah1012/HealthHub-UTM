package com.secj3303.dao;

import com.secj3303.model.Program;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


import java.util.List;

import javax.transaction.Transactional;

@Repository
@Transactional   // <<< THIS IS REQUIRED
public class ProgramDaoHibernate implements ProgramDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<Program> findAll() {
        return sessionFactory
                .getCurrentSession()
                .createQuery("from Program", Program.class)
                .list();
    }

    @Override
    public Program findById(Integer id) {
        return sessionFactory
                .getCurrentSession()
                .get(Program.class, id);
    }

    @Override
    public void save(Program program) {
        sessionFactory
                .getCurrentSession()
                .saveOrUpdate(program);
    }

    @Override
    public void delete(Integer id) {
        Session s = sessionFactory.getCurrentSession();
        Program p = s.get(Program.class, id);
        if (p != null) {
            s.delete(p);
        }
    }
}   