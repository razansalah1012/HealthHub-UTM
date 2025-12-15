package com.secj3303.model;

import javax.persistence.*;

@Entity
@Table(name = "HHUTM_WORKOUT_PLAN")
public class WorkoutPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;
    private String description;
    private Integer durationWeeks;

    public WorkoutPlan() {}

    public WorkoutPlan(String title, String description, Integer durationWeeks) {
        this.title = title;
        this.description = description;
        this.durationWeeks = durationWeeks;
    }

    // getters & setters
}
