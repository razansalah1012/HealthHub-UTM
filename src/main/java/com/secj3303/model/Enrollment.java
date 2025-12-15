package com.secj3303.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ENROLLMENT")
public class Enrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne
    @JoinColumn(name = "program_id", nullable = false)
    private Program program;

    @Column(nullable = false)
    private String paymentStatus = "UNPAID"; // PAID / UNPAID

    private LocalDateTime enrolledAt = LocalDateTime.now();

    public Enrollment() {}

    public Enrollment(Member member, Program program) {
        this.member = member;
        this.program = program;
    }

    public Integer getId() { return id; }
    public Member getMember() { return member; }
    public Program getProgram() { return program; }
    public String getPaymentStatus() { return paymentStatus; }
    public void setPaymentStatus(String paymentStatus) { this.paymentStatus = paymentStatus; }
    public LocalDateTime getEnrolledAt() { return enrolledAt; }
}
