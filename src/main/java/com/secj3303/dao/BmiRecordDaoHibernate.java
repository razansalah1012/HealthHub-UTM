package com.secj3303.dao;

import com.secj3303.model.BmiRecord;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BmiRecordDaoHibernate implements BmiRecordDao {

    @Autowired private SessionFactory sessionFactory;

    @Override
    public void save(BmiRecord record) {
        Session s = sessionFactory.openSession();
        s.beginTransaction();
        s.save(record);
        s.getTransaction().commit();
        s.close();
    }

    @Override
    public List<BmiRecord> findByMember(Integer memberId) {
        Session s = sessionFactory.openSession();
        List<BmiRecord> list = s.createQuery(
                "FROM BmiRecord b WHERE b.member.id = :mid ORDER BY b.createdAt DESC",
                BmiRecord.class
        ).setParameter("mid", memberId).list();
        s.close();
        return list;
    }
}
