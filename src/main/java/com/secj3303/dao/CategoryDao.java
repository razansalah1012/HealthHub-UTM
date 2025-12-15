package com.secj3303.dao;

import com.secj3303.model.Category;
import java.util.List;

public interface CategoryDao {
    List<Category> findAll();
    void save(Category category);
}
