package com.secj3303.dao;

import com.secj3303.model.WorkoutPlan;
import java.util.List;

public interface WorkoutPlanDao {
    void save(WorkoutPlan plan);
    List<WorkoutPlan> findAll();
}
