package amu.database;

import amu.model.Book;
import amu.model.Publisher;
import amu.model.Title;

import java.sql.*;
import java.util.logging.*;

public class BookDAO {
	public Book findByISBN(String isbn) {
		Book book = null;

		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			connection = Database.getConnection();

			String query = "";
			if(isbn != null){

				if(isbn.length() < 13){
					query = "SELECT * FROM book, publisher, title "
							+ "WHERE book.id = '"
									+ isbn + "' "
									+ "AND book.title_id = title.id "
									+ "AND book.publisher_id = publisher.id;";
				}
				else{
					query = "SELECT * FROM book, publisher, title "
							+ "WHERE book.isbn13 = '"
							+ isbn + "' "
							+ "AND book.title_id = title.id "
							+ "AND book.publisher_id = publisher.id;";
				}
			}

			statement = connection.prepareStatement(query);
			resultSet = statement.executeQuery();
			Logger.getLogger(this.getClass().getName()).log(Level.FINE, "findByISBN SQL Query: " + query);

			if (resultSet.next()) {
				AuthorDAO authorDAO = new AuthorDAO();

				book = new Book();
				book.setId(resultSet.getInt("book.id"));
				book.setTitle(new Title(resultSet.getInt("title.id"), resultSet.getString("title.name")));
				book.setPublisher(new Publisher(resultSet.getInt("publisher.id"), resultSet.getString("publisher.name")));
				book.setPublished(resultSet.getString("book.published"));
				book.setEdition(resultSet.getInt("book.edition"));
				book.setBinding(resultSet.getString("book.binding"));
				book.setIsbn10(resultSet.getString("book.isbn10"));
				book.setIsbn13(resultSet.getString("book.isbn13"));
				book.setDescription(resultSet.getString("book.description"));
				book.setAuthor(authorDAO.findByBookID(resultSet.getInt("book.id")));
				book.setPrice(resultSet.getFloat("book.price"));
				// TODO: Reviews, Categories
			}
		} catch (SQLException exception) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, exception);
		} finally {
			Database.close(connection, statement, resultSet);
		}

		return book;
	}


}
