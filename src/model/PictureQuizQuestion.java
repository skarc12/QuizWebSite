package model;

import com.google.gson.JsonElement;

public class PictureQuizQuestion extends Question {
	String url;
	String answer;
	int score;
	public PictureQuizQuestion() {
		super(Question.QuestionType.PICTURE_QUIZ);
		
	}
	public PictureQuizQuestion( String url, String answer,
			int score) {
		this();
		this.url = url;
		this.answer = answer;
		this.score = score;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
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
