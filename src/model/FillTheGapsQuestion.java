package model;

public class FillTheGapsQuestion extends Question {
	String question;
	String[] choices;
	int score;

	public FillTheGapsQuestion() {
		super(Question.QuestionType.FILL_THE_GAPS);
	}

	public FillTheGapsQuestion(QuestionType type, String question,
			String[] choices, int score) {
		this();
		this.question = question;
		this.choices = choices;
		this.score = score;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String[] getChoices() {
		return choices;
	}

	public void setChoices(String[] choices) {
		this.choices = choices;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
	

}
