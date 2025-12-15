package com.secj3303.dao;

import com.secj3303.model.WorkoutPlan;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class WorkoutPlanDaoHibernate implements WorkoutPlanDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void save(WorkoutPlan plan) {
        Session s = sessionFactory.openSession();
        s.beginTransaction();
        s.save(plan);
        s.getTransaction().commit();
        s.close();
    }

    @Override
    public List<WorkoutPlan> findAll() {
        Session s = sessionFactory.openSession();
        List<WorkoutPlan> list =
                s.createQuery("FROM WorkoutPlan", WorkoutPlan.class).list();
        s.close();
        return list;
    }
}
