package com.secj3303.dao;

import com.secj3303.model.Enrollment;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EnrollmentDaoHibernate implements EnrollmentDao {

    @Autowired private SessionFactory sessionFactory;

    @Override
    public void save(Enrollment enrollment) {
        Session s = sessionFactory.openSession();
        s.beginTransaction();
        s.save(enrollment);
        s.getTransaction().commit();
        s.close();
    }

    @Override
    public List<Enrollment> findByMember(Integer memberId) {
        Session s = sessionFactory.openSession();
        List<Enrollment> list = s.createQuery(
                "FROM Enrollment e WHERE e.member.id = :mid ORDER BY e.enrolledAt DESC",
                Enrollment.class
        ).setParameter("mid", memberId).list();
        s.close();
        return list;
    }

    @Override
    public void updatePaymentStatus(Integer enrollmentId, String status) {
        Session s = sessionFactory.openSession();
        s.beginTransaction();
        Enrollment e = s.get(Enrollment.class, enrollmentId);
        if (e != null) {
            e.setPaymentStatus(status);
            s.update(e);
        }
        s.getTransaction().commit();
        s.close();
    }
}
