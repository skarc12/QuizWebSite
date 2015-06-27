package model;

import java.sql.Date;

public class Quiz {
	String quizName;
	String description;
	String url;
	boolean isOnePage;
	boolean feedback;
	boolean random;
	Date date;
	Question[] questions;
	String category;
	
	public Quiz(String quizName, String url, Date date) {
		super();
		this.quizName = quizName;
		this.url = url;
		this.date = date;
	}
	
	public String getCategory() {
		return category;
	}


	public void setCategory(String category) {
		this.category = category;
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
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
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
