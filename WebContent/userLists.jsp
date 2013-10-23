<%@page import="java.awt.print.Printable"%>
<%@page import="java.sql.*"%>
<%@page import="amu.database.Database"%>
<h1>All public user lists</h1>
<%

    Connection connection = null;
    Statement statement = null;
    ResultSet resultSet = null;
    
    try {
        connection = Database.getConnection();
        statement = connection.createStatement();
        
        String query = "SELECT * "
                + "FROM list";
        resultSet = statement.executeQuery(query);
        
        while (resultSet.next()) {
            out.println("<h2>");
            // Insert link to list of books here.
            out.println("<a href='../viewBook.do?isbn=" + resultSet.getString("book.isbn13") + "'>");
            out.println("</a>");
            out.println("</h2>");
        }
    } catch (SQLException ex) {
        System.out.println("SQLException: " + ex.getMessage());
    } finally {
        Database.close(connection, statement, resultSet);
    }
%>