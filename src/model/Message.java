package model;

public class Message {
	int id;
	User sender;
	User reciever;
	String msg;
	boolean seen;
	
	public Message(int msgID,User sender, User reciever, String msg, boolean seen) {
		super();
		this.id = msgID;
		this.sender = sender;
		this.reciever = reciever;
		this.msg = msg;
		this.seen = seen;
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

	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public boolean isSeen() {
		return seen;
	}
	public void setSeen(boolean seen) {
		this.seen = seen;
	}
	

}
