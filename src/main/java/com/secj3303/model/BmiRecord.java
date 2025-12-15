package com.secj3303.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "BMI_RECORD")
public class BmiRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    private double height;
    private double weight;
    private double bmi;
    private int age;

    private LocalDateTime createdAt = LocalDateTime.now();

    public BmiRecord() {}

    public BmiRecord(Member member, double height, double weight, double bmi, int age) {
        this.member = member;
        this.height = height;
        this.weight = weight;
        this.bmi = bmi;
        this.age = age;
    }

    public Integer getId() { return id; }
    public double getHeight() { return height; }
    public double getWeight() { return weight; }
    public double getBmi() { return bmi; }
    public int getAge() { return age; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
