package com.pysite.sbb.question;

import com.pysite.sbb.answer.Answer;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Setter
@Getter
public class Question {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private int id;

    private LocalDateTime createDate;

    @Column(length = 200)
    private String subject;

    @Column(columnDefinition = "TEXT")
    private String content;

    // mappedBy 속성을 지정하지 않으면 중간 테이블을 만든다.
    @OneToMany(mappedBy = "question", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<Answer> answers = new ArrayList<>(); // Answer_ID_List

    public Answer addAnswer(String content) {
        Answer answer = new Answer();
        answer.setContent(content);
        answer.setQuestion(this);
        answer.setCreateDate(LocalDate.now());
        answers.add(answer);
        return answer;
    }
}
