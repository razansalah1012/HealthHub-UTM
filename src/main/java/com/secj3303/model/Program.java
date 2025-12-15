package com.secj3303.model;

import javax.persistence.*;
@Entity
@Table(name = "hhutm_program")
public class Program {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String description;

    private int duration_weeks;

    private double monthly_fee;

    // getters & setters
}

