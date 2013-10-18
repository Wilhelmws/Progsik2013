package amu.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import amu.model.Review;

public class ReviewDAO {

	public ArrayList<Review> findByBookID(int bookID) {
		ArrayList<Review> reviews = new ArrayList<Review>();
		
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		
		try{
			connection = Database.getConnection();
			statement = connection.createStatement();
			
			String query = "SELECT "
					+ "review.id, "
					+ "review.user, "
					+ "review.message, "
					+ "review.votes, "
					+ "FROM review";
			
//			String query = "SELECT "
//					+ "review.id, "
//					+ "review.user, "
//					+ "review.message, "
//					+ "review.votes, "
//					+ "FROM review, review_x_book "
//					+ "WHERE review_x_book.book_id =" + bookID + " "
//					+ "AND review.id = review_x_book.review_id";
			resultSet = statement.executeQuery(query);

			
			while(resultSet.next()){
				reviews.add(new Review(resultSet.getInt("review.id"), resultSet.getString("review.name"), resultSet.getString("review.message"), resultSet.getInt("review.votes")));
			}
		}catch(SQLException exception){
		}finally{
			Database.close(connection, statement, resultSet);
		}
		
		return reviews;
	}
}
