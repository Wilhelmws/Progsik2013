<%@page import="java.sql.*"%>
<%@page import="amu.database.Database"%>
<h1>Mails sent by Amu-Darya:</h1>
<table>
    <thead>
        <tr>
            <th>Sent</th>
            <th>To</th>
            <th>Subject</th>
            <th>Content</th>
        </tr>
    </thead>
    <tbody>
        <%

            Connection connection = null;
            Statement statement = null;
            ResultSet resultSet = null;

            try {
                connection = Database.getConnection();
                statement = connection.createStatement();

                String query = "SELECT * FROM mail ORDER BY sentTime DESC;";
                resultSet = statement.executeQuery(query);

                while (resultSet.next()) {
                    out.println("<tr>");
                    out.println("<td style='width:15%'>" + resultSet.getDate("sentTime") + "</td>");
                    out.println("<td style='width:15%'>" + resultSet.getString("to") + "</td>");
                    out.println("<td style='width:15%'>" + resultSet.getString("subject") + "</td>");
                    out.println("<td style='width:55%'>" + resultSet.getString("content") + "</td>");
                    out.println("</tr>");
                }
            } catch (SQLException ex) {
                System.out.println("SQLException: " + ex.getMessage());
            } finally {
                Database.close(connection, statement, resultSet);
            }

        %>
    </tbody>
</table>