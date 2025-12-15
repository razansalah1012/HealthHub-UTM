package com.secj3303.dao;

import com.secj3303.model.Member;

public interface MemberDao {
    Member findByUserId(Integer userId);
    void save(Member member);
}
