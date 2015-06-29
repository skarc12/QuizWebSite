package model;

import com.google.gson.JsonElement;

public class QuestinAnswerQuestion extends Question {
	String question;
	String answer;
	int score;
	public QuestinAnswerQuestion() {
		super(Question.QuestionType.QUESTION_ANSWER);
		
	}
	public QuestinAnswerQuestion(String question,
			String answer, int score) {
		this();
		this.question = question;
		this.answer = answer;
		this.score = score;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	@Override
	public int checkAnswer(JsonElement elem) {
		return elem.getAsString().equalsIgnoreCase(answer) ? 1 : 0;
	}
	

}
