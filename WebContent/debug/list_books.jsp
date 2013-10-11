<%@page import="java.sql.*"%>
<%@page import="amu.database.Database"%>
<h1>The TEMPORARY (in lieu of categories and such) List Of All Books In The Store</h1>
<%

    Connection connection = null;
    Statement statement = null;
    ResultSet resultSet = null;
    
    try {
        connection = Database.getConnection();
        statement = connection.createStatement();
        
        String query = "SELECT "
                + "book.id, "
                + "title.name, "
                + "book.published, "
                + "publisher.name, "
                + "book.edition, "
                + "book.binding, "
                + "book.isbn10, "
                + "book.isbn13, "
                + "book.description, "
                + "book.price "
                + "FROM title, book, publisher "
                + "WHERE title.id = book.title_id AND book.publisher_id = publisher.id;";
        resultSet = statement.executeQuery(query);
        
        while (resultSet.next()) {
            out.println("<h2>");
            out.println("<a href='../viewBook.do?isbn=" + resultSet.getString("book.isbn13") + "'>");
            out.println(resultSet.getString("title.name"));
            out.println("</a>");
            out.println("</h2>");
        }
    } catch (SQLException ex) {
        System.out.println("SQLException: " + ex.getMessage());
    } finally {
        Database.close(connection, statement, resultSet);
    }
%>