package com.pysite.sbb;

import jakarta.persistence.*;

import java.time.LocalDate;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
public class Answer {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private int id;

    private LocalDate createDate;

    @ManyToOne
    private Question question; // Question_ID 칼럼이 됨

    @Column(columnDefinition = "TEXT")
    private String content;
}
