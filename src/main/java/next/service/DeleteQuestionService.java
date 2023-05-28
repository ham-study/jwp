package next.service;

import java.util.List;

import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.model.Answer;
import next.model.Question;

public class DeleteQuestionService {
	private QuestionDao questionDao = new QuestionDao();
	private AnswerDao answerDao = new AnswerDao();

	public void delete(long questionId) {
		Question question = questionDao.findById(questionId);
		List<Answer> answers = answerDao.findAllByQuestionId(questionId);

		if (!answers.isEmpty() && isAnyAnswerNotWrittenBy(answers, question.getWriter())) {
			throw new IllegalStateException("Can not delete question");
		}

		questionDao.deleteById(questionId);
	}

	private boolean isAnyAnswerNotWrittenBy(List<Answer> answers, String writer) {
		return answers.stream()
			.anyMatch(answer -> !answer.getWriter().equals(writer));
	}
}
