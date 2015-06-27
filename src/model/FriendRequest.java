package model;

public class FriendRequest {
	User sender;
	User reciever;
	boolean seen;
	public FriendRequest(User sender, User reciever, boolean seen) {
		super();
		this.sender = sender;
		this.reciever = reciever;
		this.seen = seen;
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
	public boolean isSeen() {
		return seen;
	}
	public void setSeen(boolean seen) {
		this.seen = seen;
	}
	

}
