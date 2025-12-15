package com.secj3303.dao;

import com.secj3303.model.BmiRecord;
import java.util.List;

public interface BmiRecordDao {
    void save(BmiRecord record);
    List<BmiRecord> findByMember(Integer memberId);
}
