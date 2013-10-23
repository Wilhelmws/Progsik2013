package amu.database;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import amu.model.Order;
import amu.model.Review;

public class ReviewDAO {
	
	public Review findByReviewID(int reviewID){
		
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		Review review = null;
		
		try{
			connection = Database.getConnection();
			statement = connection.createStatement();
			
			String query = "SELECT review.id, review.name, review.message, review.votes, review_x_book.book_id " +
					"FROM review, review_x_book WHERE id = '" + reviewID + "'" +
					"AND review.id = review_x_book.review_id;";
			
			resultSet = statement.executeQuery(query);
			
			while(resultSet.next()){
				review = new Review(
						resultSet.getInt("review.id"),
						resultSet.getString("review.name"),
						resultSet.getString("review.message"),
						resultSet.getInt("review.votes"),
						resultSet.getInt("review_x_book.book_id")
						);
			}
		}catch(SQLException exception){
		}finally{
			Database.close(connection, statement, resultSet);
		}
		
		return review;
	}
	
	public boolean updateReview(Review review){
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		
		try{
			connection = Database.getConnection();
			
			String query = "UPDATE review SET votes=? WHERE id=?";
			statement = connection.prepareStatement(query);
			statement.setInt(1, review.getVotes());
			statement.setInt(2, review.getId());

			if (statement.executeUpdate() > 0) {
				return true;
			}
			
		}catch(SQLException exception){
		}finally{
			Database.close(connection, statement, resultSet);
		}
		
		return false;
	}

	public ArrayList<Review> findByBookID(int bookID) {

		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		ArrayList<Review> reviews = new ArrayList<Review>();
		
		try{
			connection = Database.getConnection();
			statement = connection.createStatement();
			
//			String query = "SELECT * FROM review ";
	
			
			//TODO: fix this query
			String query = 
					"SELECT " +
					"review.id, " +
					"review.name, " +
					"review.message, " +
					"review.votes " +
					"FROM review " +
					"JOIN review_x_book ON review.id = review_x_book.review_id " +
					"WHERE review_x_book.book_id = '" + bookID + "'" +
							"ORDER BY review.votes DESC;";
			
			resultSet = statement.executeQuery(query);

			
			while(resultSet.next()){
				reviews.add(new Review(
						resultSet.getInt("review.id"), 
						resultSet.getString("review.name"), 
						resultSet.getString("review.message"), 
						resultSet.getInt("review.votes"),
						bookID));
			}
		}catch(SQLException exception){
		}finally{
			Database.close(connection, statement, resultSet);
		}
		
		return reviews;
	}
	
	/**
	 * ADDS A REVIEW. THIS REQUIRE THAT YOU HAVE EXECUTED review.setBookId(int id) WITH CORRECT bookID
	 * @param review
	 * @return
	 */
    public boolean add(Review review) {
    	
    	//Must do it this way to not get concurrecnty problems
    	 if(insertReview(review)){
    		 return insertReviewBookRelation(review);
    	 }
    	 return false;
    }
    
    private boolean insertReview(Review review){
     	PreparedStatement statement = null;
    	Connection connection = null;
    	ResultSet resultSet = null;
    	
    	/**
    	 * INSERT NEW REVIEW
    	 */
        try {
            connection = Database.getConnection();

            String query = "INSERT INTO `review` (name, message, votes) VALUES (?, ?, ?)";
            
            statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            
            statement.setString(1, review.getName());
            statement.setString(2, review.getMessage());
            statement.setInt(3, review.getVotes());
            statement.executeUpdate();
            
            resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
            	int newID = resultSet.getInt(1);
            	
            	//Set the ID created by InnoDB to the review object. 
            	//This ID is later used in insertReviewBookRelation()
            	review.setId(newID);
            	return true;
            }
            
        } catch (SQLException exception) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, exception);
        } finally {
            Database.close(connection, statement, resultSet);
        }
        
        return false;
    }
    
    /**
     * INSERT RELATION BETWEEN REVIEW AND BOOKS
     */
    private boolean insertReviewBookRelation(Review review){
      	PreparedStatement statement = null;
    	Connection connection = null;
    	ResultSet resultSet = null;
  
        try{
        	connection = Database.getConnection();
        	
        	String query = "INSERT INTO `review_x_book` (review_id, book_id) VALUES (?, ?)";
        	statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        	statement.setInt(1, review.getId());
        	statement.setInt(2, review.getBookid());
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
