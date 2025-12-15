package com.secj3303.model;

import javax.persistence.*;

@Entity
@Table(name = "HHUTM_CATEGORY")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String name;

    public Category() {}
    public Category(String name) { this.name = name; }

    public Integer getId() { return id; }
    public String getName() { return name; }
    public void setId(Integer id) { this.id = id; }
    public void setName(String name) { this.name = name; }
}
