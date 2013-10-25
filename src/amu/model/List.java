package amu.model;

import java.util.ArrayList;

public class List {
	
	private Integer userId;
	private String title;
	private String description;
	private ArrayList<Book> books;

	public List (Integer userId, String title, String description, ArrayList<Book> books) {
		this.userId = userId;
		this.title = title;
		this.books = books;
		this.description = description;
	}

	public Integer getUserId() {
		return userId;
	}

	public String getTitle() {
		return this.title;
	}
	
	public String getDescription() {
		return this.description;
	}

	public void setId(int newID) {
		this.userId = newID;
	}

	public ArrayList<Book> getBooks() {
		return books;
	}
}
