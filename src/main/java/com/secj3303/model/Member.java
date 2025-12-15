package com.secj3303.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "MEMBER")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "member")
    private List<BmiRecord> bmiRecords;

    @OneToMany(mappedBy = "member")
    private List<Enrollment> enrollments;

    public Member() {}
    public Member(User user) { this.user = user; }

    public Integer getId() { return id; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
}
