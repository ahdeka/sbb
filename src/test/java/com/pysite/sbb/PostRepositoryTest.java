package com.pysite.sbb;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@ActiveProfiles("test")
@SpringBootTest
class PostRepositoryTest {
	@Autowired
	private QuestionRepository questionRepository;

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
		Question question = questionRepository.findBySubjectLike("sbb%").get();
		assertThat(question.getId()).isEqualTo(1);
	}
}
