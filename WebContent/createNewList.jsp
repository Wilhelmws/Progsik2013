<%@page import="java.sql.*"%>
<%@page import="amu.database.Database"%>

<div class="container">
    <h1>Custom Booklist</h1>
    <div class="general-form">
        <form method="post" action="addList.do">
            <table class="general-table">
                <tr>
      			<td>Title</td>
                    <td><input name="title" type="text"></input></td>
            </table>
            <td>Description
            <div> <textarea name="description" rows="10" cols="40"></textarea></div>
            <%

    Connection connection = null;
    PreparedStatement statement = null;
    ResultSet resultSet = null;
    
    try {
        connection = Database.getConnection();
        
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
        statement = connection.prepareStatement(query);
        
        resultSet = statement.executeQuery();
        
        while (resultSet.next()) {
            out.println("<h2>");
            out.println("<a href='../addList.do?isbn=" + resultSet.getString("book.isbn13") + "'>");
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
            <div> <input type="submit" value="Save" /></div>
        </form>
    </div>
</div>