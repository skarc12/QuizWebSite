package model;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

public class FillTheGapsQuestion extends Question {
	String question;
	String[] answers;
	int score;

	public FillTheGapsQuestion() {
		super(Question.QuestionType.FILL_THE_GAPS);
	}

	public FillTheGapsQuestion(String question,
			String[] answers, int score) {
		this();
		this.question = question;
		this.answers = answers;
		this.score = score;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String[] getAnswers() {
		return answers;
	}

	public void setAnswers(String[] answers) {
		this.answers = answers;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	@Override
	public int checkAnswer(JsonElement elem) {
		int count=0;
		JsonArray arr = elem.getAsJsonArray();
		for(int i=0; i<arr.size(); i++){
			if(arr.get(i).getAsString().equalsIgnoreCase(answers[i])){
				count++;
			}
		}
		return count;
	}
	

}
