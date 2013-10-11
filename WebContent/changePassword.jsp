<div class="container">
<h1>Change Password</h1>
    <c:if test="${not empty messages}">
        <c:forEach var="message" items="${messages}">
            <div>
                <span class="error">${message}</span>
            </div>
        </c:forEach>
    </c:if>
    <div class="general-form">
    <form action="changePassword.do" method="post">
    	<table class="general-table">
        <tr>
            <td><label for="password">New password</label></td> 
            <td><input id="password" name="password" type="password" value="${values.password[0]}" /></td>
        </tr>
        <tr>
            <td><label for="password">Repeat password</label></td> 
            <td><input id="password" name="password" type="password" value="${values.password[1]}" /></td>
        </tr>
        </table>
        <div><input type="submit" value="Submit" /></div>
    </form>
    </div>
</div>