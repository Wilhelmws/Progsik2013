package amu.model;

public class Review {

	int id = 0 , votes = 0, bookid = 0;
	String name = "default", message = "default";
	
	/**
	 * Constructor for creating review fetched from the database
	 * @param id
	 * @param name
	 * @param message
	 * @param votes
	 */
	public Review(int id, String name, String message, int votes){
		this.id = id;
		this.name = name;
		this.message = message;
		this.votes = votes;
	}
	
	/**
	 * Creating new review only requires the username and message
	 * @param name
	 * @param message
	 */
	public Review(String name, String message, int bookid){
		this.id = -1;
		this.name = name;
		this.message = message;
		this.votes = 0;
		this.bookid = bookid;
	}

	public int getBookid() {
		return bookid;
	}

	public void setBookid(int bookid) {
		this.bookid = bookid;
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
