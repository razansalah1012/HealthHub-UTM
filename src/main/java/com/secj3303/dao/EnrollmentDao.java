package com.secj3303.dao;

import com.secj3303.model.Enrollment;
import java.util.List;

public interface EnrollmentDao {
    void save(Enrollment enrollment);
    List<Enrollment> findByMember(Integer memberId);
    void updatePaymentStatus(Integer enrollmentId, String status);
}
