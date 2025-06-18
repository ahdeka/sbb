package com.pysite.sbb.answer;

import com.pysite.sbb.question.Question;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Setter
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
