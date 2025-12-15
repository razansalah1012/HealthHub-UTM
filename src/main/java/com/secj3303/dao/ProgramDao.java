package com.secj3303.dao;

import com.secj3303.model.Program;
import java.util.List;

public interface ProgramDao {
    List<Program> findAll();
    Program findById(Integer id);
    void save(Program program);
    void delete(Integer id);
}
