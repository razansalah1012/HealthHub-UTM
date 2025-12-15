package com.secj3303.dao;

import com.secj3303.model.Member;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MemberDaoHibernate implements MemberDao {

    @Autowired private SessionFactory sessionFactory;

    @Override
    public Member findByUserId(Integer userId) {
        Session s = sessionFactory.openSession();
        Member m = s.createQuery("FROM Member m WHERE m.user.id = :uid", Member.class)
                .setParameter("uid", userId)
                .uniqueResult();
        s.close();
        return m;
    }

    @Override
    public void save(Member member) {
        Session s = sessionFactory.openSession();
        s.beginTransaction();
        s.save(member);
        s.getTransaction().commit();
        s.close();
    }
}
