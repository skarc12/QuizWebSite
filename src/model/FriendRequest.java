package model;

public class FriendRequest {
	int id;
	User sender;
	User reciever;
	boolean seen;
	public FriendRequest(int id,User sender, User reciever, boolean seen) {
		super();
		this.id = id;
		this.sender = sender;
		this.reciever = reciever;
		this.seen = seen;
	}
	
	public FriendRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
