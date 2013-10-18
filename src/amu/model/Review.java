package amu.model;

public class Review {

	int id, votes;
	String name, message;
	
	public Review(int id, String name, String message, int votes){
		this.id = id;
		this.name = name;
		this.message = message;
		this.votes = votes;
	}

	public int getVotes() {
		return votes;
	}

	public void setVotes(int votes) {
		this.votes = votes;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
