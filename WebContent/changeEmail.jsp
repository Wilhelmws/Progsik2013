<div class="container">
<h1>Change Email</h1>
    <div>Current email ${customer.email}</div>
    <c:if test="${not empty messages}">
        <c:forEach var="message" items="${messages}">
            <div><span class="error">${message}</span></div>
            </c:forEach>
        </c:if>
    <div class="general-form">
    <form action="changeEmail.do" method="post">
    	<table class="general-table">
        <tr>
            <td><label for="email">New email</label></td> 
            <td><input id="email" name="email" type="text" value="${values.email[0]}" /></td>
        </tr>
        <tr>
            <td><label for="email">Repeat email</label></td> 
            <td><input id="email" name="email" type="text" value="${values.email[1]}" /></td>
        </tr>
        </table>
        <div><input type="submit" value="Submit" /></div>
    </form>
    </div>
</div>