package model;

import java.sql.Date;

public class Quiz {
	String quizName;
	String description;
	boolean isOnePage;
	boolean feedback;
	boolean random;
	Date date;
	Question[] questions;
	User ownes;
	
	public User getOwnes() {
		return ownes;
	}

	public void setOwnes(User ownes) {
		this.ownes = ownes;
	}

	public Quiz(String quizName, String url, Date date) {
		super();
		this.quizName = quizName;
		this.date = date;
	}

	public Question[] getQuestions() {
		return questions;
	}
	
	public void setQuestions(Question[] questions) {
		this.questions = questions;
	}
	
	public String getQuizName() {
		return quizName;
	}
	public void setQuizName(String quizName) {
		this.quizName = quizName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public boolean isOnePage() {
		return isOnePage;
	}
	public void setOnePage(boolean isOnePage) {
		this.isOnePage = isOnePage;
	}
	public boolean isFeedback() {
		return feedback;
	}
	public void setFeedback(boolean feedback) {
		this.feedback = feedback;
	}
	public boolean isRandom() {
		return random;
	}
	public void setRandom(boolean random) {
		this.random = random;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	

}
