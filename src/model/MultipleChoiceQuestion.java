package model;

import com.google.gson.JsonElement;

public class MultipleChoiceQuestion extends Question {
	String question;
	String[] answers;
	String correctAnswer;
	int score;

	public MultipleChoiceQuestion(String question, String[] answer, String correctAnswer, int point) {
		super(Question.QuestionType.MULTIPLE_CHOICE);
		this.question = question;
		this.answers = answer;
		this.correctAnswer = correctAnswer;
		this.score = point;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getAnswer1() {
		return answers[0];
	}

	public void setAnswer1(String answer1) {
		this.answers[0] = answer1;
	}

	public String getAnswer2() {
		return answers[1];
	}

	public void setAnswer2(String answer2) {
		this.answers[1] = answer2;
	}

	public String getAnswer3() {
		return answers[2];
	}

	public void setAnswer3(String answer3) {
		this.answers[2] = answer3;
	}

	public String getAnswer4() {
		return answers[3];
	}

	public void setAnswer4(String answer4) {
		this.answers[3] = answer4;
	}

	public String[] getAnswers(){
		return answers;
	}
	
	public String getCorrectAnswer() {
		return correctAnswer;
	}

	public void setCorrectAnswer(String correctAnswer) {
		this.correctAnswer = correctAnswer;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int point) {
		this.score = point;
	}

	@Override
	public int checkAnswer(JsonElement elem) {
		return elem.getAsString().equalsIgnoreCase(correctAnswer) ? 1 : 0;
	}
	

}
