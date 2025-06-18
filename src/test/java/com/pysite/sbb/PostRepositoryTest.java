package com.pysite.sbb;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@ActiveProfiles("test")
@SpringBootTest
class PostRepositoryTest {
	@Autowired
	private QuestionRepository questionRepository;

	@Autowired
	private AnswerRepository answerRepository;

	@Test
	@DisplayName("findAll")
	void t1() {
		List<Question> questions = questionRepository.findAll();

		assertThat(questions).hasSize(2);
	}

	@Test
	@DisplayName("findById")
	void t2() {
		Question question = questionRepository.findById(1).get();
		assertThat(question.getSubject()).isEqualTo("sbb가 무엇인가요?");
	}

	@Test
	@DisplayName("findBySubject")
	void t3() {
		Question question = questionRepository.findBySubject("sbb가 무엇인가요?").get();
		assertThat(question.getId()).isEqualTo(1);
	}

	@Test
	@DisplayName("findBySubjectAndContent")
	void t4() {
		Question question = questionRepository.findBySubjectAndContent("sbb가 무엇인가요?", "sbb에 대해서 알고 싶습니다.").get();
		assertThat(question.getId()).isEqualTo(1);
	}

	@Test
	@DisplayName("findBySubjectLike")
	void t5() {
		List<Question> questions = questionRepository.findBySubjectLike("sbb%");

		Question question = questions.get(0);
		assertThat(question.getId()).isEqualTo(1);
	}

	@Test
	@DisplayName("수정")
	@Transactional
	void t6() { // 가장 먼저 실행시키기 위해서 t6이 아닌 t0로 메서드명 변경
		Question question = questionRepository.findById(1).get();
		assertThat(question).isNotNull();

		question.setSubject("수정된 제목");
		this.questionRepository.save(question);

		Question foundQuestion = questionRepository.findBySubject("수정된 제목").get();
		assertThat(foundQuestion).isNotNull();
	}

	@Test
	@DisplayName("삭제")
	@Transactional
	void t7() {
		assertThat(questionRepository.count()).isEqualTo(2);

		Question question = questionRepository.findById(1).get();
		questionRepository.delete(question);

		assertThat(questionRepository.count()).isEqualTo(1);
	}

	@Test
	@DisplayName("답변 생성")
	@Transactional
	void t8() {
		Question question = questionRepository.findById(2).get();

		Answer answer = new Answer();
		answer.setContent("네 자동으로 생성됩니다.");
		answer.setQuestion(question);
		answer.setCreateDate(LocalDate.now());
		answerRepository.save(answer);
	}


	@Test
	@DisplayName("답변 생성 by oneToMany")
	@Transactional
	void t9() {
		Question question = questionRepository.findById(2).get();

		int beforeCount = question.getAnswers().size();

		Answer newAnswer = question.addAnswer("네 자동으로 생성됩니다.");

		// 트랜잭션이 종료된 이후에
		assertThat(newAnswer.getId()).isEqualTo(0);

		int afterCount = question.getAnswers().size();

		assertThat(afterCount).isEqualTo(beforeCount + 1);
	}

	@Test
	@DisplayName("답변 조회")
	@Transactional
	void t10() {
		Answer answer = answerRepository.findById(1).get();

		assertThat(answer.getId()).isEqualTo(1);
	}

	@Test
	@DisplayName("답변 조회 by oneToMany")
	@Transactional
	void t11() {
		Question question = questionRepository.findById(2).get();

		List<Answer> answers = question.getAnswers();
		int beforeCount = question.getAnswers().size();

		Answer newAnswer = question.addAnswer("네 자동으로 생성됩니다.");

		// 트랜잭션이 종료된 이후에
		assertThat(newAnswer.getId()).isEqualTo(0);

		int afterCount = question.getAnswers().size();

		assertThat(afterCount).isEqualTo(beforeCount + 1);
	}


	@Test
	@DisplayName("findAnswer by question")
	void t12() {
		Question question = questionRepository.findById(2).get();

		Answer answer1 = question.getAnswers().get(0);

		assertThat(answer1.getId()).isEqualTo(1);
	}
}
