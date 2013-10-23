
<div class="container">
    <h1>Login</h1>
    <c:choose>
        <c:when test="${empty customer}">
            <div class="general-form">
                <form action="loginCustomer.do" method="post">
                    <c:if test="${not empty values.from}">
                        <input type="hidden" name="from" value="${values.from}">
                        <input type="hidden" name="isbn" value="${values.isbn}">
                    </c:if>
                    <table class="general-table">
                        <tr>
                            <td>
                                <label for="email">Email</label>
                            </td>
                            <td>
                                <input id="email" name="email" type="text" value="${values.email}" />
                            </td>
                            <c:if test="${not empty messages.emailorpassword}">
                                <td><span class="error">${messages.emailorpassword}</span></td>
                                </c:if>
                        </tr>
                        <tr>
                            <td><label for="password">Password</label></td>
                            <td><input id="password" name="password" type="password" autocomplete="off" /></td>
                        </tr>
                    </table>
					<%@ page import="net.tanesha.recaptcha.ReCaptcha" %>
    				<%@ page import="net.tanesha.recaptcha.ReCaptchaFactory" %>
    				<%
    				ReCaptcha c = ReCaptchaFactory.newReCaptcha("6LeM_egSAAAAALCCPiJlsKQkG5rY6jHfSqtU03_J", "6LeM_egSAAAAAGaA2ePbaBwrUHET4x5YMdQJtbJh", false);
    				out.print(c.createRecaptchaHtml(null, null));
    				%>
                    <div>
                        <input type="submit" value="Submit">
                    </div>

                </form>
            </div>
        </c:when>
        <c:otherwise>
            <div>
                Login successful!
            </div>
        </c:otherwise>
    </c:choose>
</div>