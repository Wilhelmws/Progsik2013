package amu.database;

import amu.Config;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public final class Database {
    
    private Database() { }

    public static Connection getConnection() {
        Connection connection = null;

        try {
            connection = ((DataSource) new InitialContext().lookup(Config.JDBC_RESOURCE)).getConnection();
        } catch (NamingException exception) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, "Getting DataSource failed.", exception);
        } catch (SQLException exception) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, "Opening Connection failed.", exception);
        }
        
        return connection;
    }

    public static void close(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException exception) {
                Logger.getLogger(Database.class.getName()).log(Level.SEVERE, "Closing Connection failed.", exception);
            }
        }
    }

    public static void close(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException exception) {
                Logger.getLogger(Database.class.getName()).log(Level.SEVERE, "Closing Statement failed.", exception);
            }
        }
    }

    public static void close(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException exception) {
                Logger.getLogger(Database.class.getName()).log(Level.SEVERE, "Closing ResultSet failed.", exception);
            }
        }
    }

    public static void close(Connection connection, Statement statement, ResultSet resultSet) {
        close(resultSet);
        close(statement);
        close(connection);
    }
    
    public static void close(Connection connection, Statement statement) {
        close(statement);
        close(connection);
    }
}
