package model;

public class Challenge {
	User sender;
	User reciever;
	int firstScore;
	int secondScore;
	String msg;
	boolean challengeSeen;
	boolean challengeStatus;
	
	public Challenge(User sender, User reciever, int firstScore,
			int secondScore, String msg, boolean challengeSeen,
			boolean challengeStatus) {
		super();
		this.sender = sender;
		this.reciever = reciever;
		this.firstScore = firstScore;
		this.secondScore = secondScore;
		this.msg = msg;
		this.challengeSeen = challengeSeen;
		this.challengeStatus = challengeStatus;
	}
	
	public User getSender() {
		return sender;
	}

	public void setSender(User sender) {
		this.sender = sender;
	}

	public User getReciever() {
		return reciever;
	}

	public void setReciever(User reciever) {
		this.reciever = reciever;
	}

	public int getFirstScore() {
		return firstScore;
	}
	public void setFirstScore(int firstScore) {
		this.firstScore = firstScore;
	}
	public int getSecondScore() {
		return secondScore;
	}
	public void setSecondScore(int secondScore) {
		this.secondScore = secondScore;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public boolean isChallengeSeen() {
		return challengeSeen;
	}
	public void setChallengeSeen(boolean challengeSeen) {
		this.challengeSeen = challengeSeen;
	}
	public boolean isChallengeStatus() {
		return challengeStatus;
	}
	public void setChallengeStatus(boolean challengeStatus) {
		this.challengeStatus = challengeStatus;
	}
	

}
