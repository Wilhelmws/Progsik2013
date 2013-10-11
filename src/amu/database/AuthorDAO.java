package amu.database;

import amu.model.Author;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AuthorDAO {
    public List<Author> findByBookID(int bookID) {
        List<Author> authors = new ArrayList<Author>();
        
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        
        try {
            connection = Database.getConnection();
            statement = connection.createStatement();
            
            String query = "SELECT "
                    + "author.id, "
                    + "author.name "
                    + "FROM author, author_x_book "
                    + "WHERE author_x_book.book_id=" + bookID + " "
                    + "AND author.id = author_x_book.author_id";
            resultSet = statement.executeQuery(query);
            Logger.getLogger(this.getClass().getName()).log(Level.FINE, "findByBookID SQL Query: " + query);
            
            while (resultSet.next()) {
                authors.add(new Author(resultSet.getInt("author.id"), resultSet.getString("author.name")));
            }
        } catch (SQLException exception) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, exception);
        } finally {
            Database.close(connection, statement, resultSet);
        }
        
        return authors;
    }
}
