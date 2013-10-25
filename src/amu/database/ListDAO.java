package amu.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

import amu.model.Book;
import amu.model.Customer;
import amu.model.List;
import amu.model.Order;

public class ListDAO {

	public ArrayList<List> browse(Customer customer){

		ArrayList<List> lists = new ArrayList<List>();
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;


		try {
			connection = Database.getConnection();

			/*
			 * "SELECT order_items.book_id, order_items.quantity " +
				"FROM `order_items` " +
				"JOIN `order` ON order.id = order_items.order_id " +
				"WHERE order.customer_id = ? " +
				"AND order.id = ?";
			 * 
			 */
			String query = "SELECT * " +
					"FROM `list` " +
					"JOIN `list_x_book` ON id = list_id " +
					"WHERE user_id = ?";
			statement = connection.prepareStatement(query);
			statement.setInt(1, customer.getId());
			resultSet = statement.executeQuery();

			ArrayList<Book> books = new ArrayList<Book>();
			int currentListID = -1;	
			String description = "";
			String title ="";
			int userId = 0;
			while(resultSet.next()){
				System.out.println("Got some results");

				if(currentListID != resultSet.getInt("id")) {
					System.out.println("New ID");

					if(books.size() > 0){
						System.out.println("New list created and cleareaddd");
						lists.add(new List(userId, title, description, books));
						books.clear();
						description = "";
						title = "";
						userId = 0;
					}
					currentListID = resultSet.getInt("id");
					if(books.size() == 0){
						System.out.println("Books added");
						BookDAO bookDAO = new BookDAO();
						books.add(bookDAO.findByISBN(String.valueOf(resultSet.getInt("book_id"))));
						description = resultSet.getString("description");
						title = resultSet.getString("title");
						userId = resultSet.getInt("user_id");
					}
				}else{
					BookDAO bookDAO = new BookDAO();
					System.out.println("Books added");
					books.add(bookDAO.findByISBN(String.valueOf(resultSet.getInt("book_id"))));
					description = resultSet.getString("description");
					title = resultSet.getString("title");
					userId = resultSet.getInt("user_id");					
				}
			}			
			lists.add(new List(userId, title, description, books));				
		} catch (SQLException exception) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, exception);
		} finally {
			Database.close(connection, statement, resultSet);
		}


		for (int i = 0; i < lists.size(); i++) {
			System.out.println("hwiasdasdasdasdasd*****");
			System.out.println(lists.get(i));
		}
		System.out.println("list is size "+ lists.size());

		return lists;
	}


	/**
	 * ADDS A REVIEW. THIS REQUIRE THAT YOU HAVE EXECUTED review.setBookId(int id) WITH CORRECT bookID
	 * @param review
	 * @return
	 */
	public boolean add(List list) {

		//Must do it this way to not get concurrecnty problems
		if(insertList(list)){
			for (int i = 0; i < list.getBooks().size(); i++) {
				return insertListBookRelation(list, i);
			}
		}
		return false;
	}

	private boolean insertList(List list){
		PreparedStatement statement = null;
		Connection connection = null;
		ResultSet resultSet = null;

		/**
		 * INSERT NEW LIST
		 */
		try {
			connection = Database.getConnection();

			String query = "INSERT INTO `list` (user_id, title, description) VALUES (?, ?, ?)";

			statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

			statement.setInt(1, list.getUserId());
			statement.setString(2, list.getTitle());
			statement.setString(3, list.getDescription());
			statement.executeUpdate();

			resultSet = statement.getGeneratedKeys();
			if (resultSet.next()) {
				int newID = resultSet.getInt(1);

				//Set the ID created by InnoDB to the review object. 
				//This ID is later used in insertReviewBookRelation()
				list.setId(newID);
				return true;
			}

		} catch (SQLException exception) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, exception);
		} finally {
			Database.close(connection, statement, resultSet);
		}

		return false;
	}/**
	 * INSERT RELATION BETWEEN LIST AND BOOKS
	 */
	private boolean insertListBookRelation(List list, Integer bookId){
		PreparedStatement statement = null;
		Connection connection = null;
		ResultSet resultSet = null;

		try{
			connection = Database.getConnection();

			String query = "INSERT INTO `list_x_book` (list_id, book_id) VALUES (?, ?)";
			statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

			statement.setInt(1, list.getUserId());
			statement.setInt(2, bookId);
			statement.executeUpdate();

			resultSet = statement.getGeneratedKeys();
			if(resultSet.next()){
				return true;
			}


		} catch (SQLException exception) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, exception);
		} finally {
			Database.close(connection, statement, resultSet);
		}

		return false;
	}

}
