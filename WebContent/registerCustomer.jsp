<div class="container">
	<h1>Register</h1>
	<c:choose>
		<c:when test="${empty sessionScope.customer}">
			<c:choose>
				<c:when test="${not empty register_success}">
					<div>
						You've successfully created a user. Maybe you should <a
							href="loginCustomer.do">sign in</a>?
					</div>
				</c:when>
				<c:otherwise>
					<c:if test="${not empty register_error}">
						<div>${register_error}</div>
					</c:if>
					<div class="general-form">
						<form action="registerCustomer.do" method="post">
							<table class="general-table">
								<tr>
									<td><label for="email">Email</label></td>
									<td><input id="email" name="email" type="text" /></td>
									<c:if test="${not empty messages.email}">
										<td><span class="error">${messages.email}</span></td>
									</c:if>
								</tr>
								<tr>
									<td><label for="name">Name</label></td>
									<td><input id="name" name="name" type="text" /></td>
									<c:if test="${not empty messages.name}">
										<td><span class="error">${messages.name}</span></td>
									</c:if>
								</tr>
								<tr>
									<td><label for="password">Password</label></td>
									<td><input id="password" name="password" type="password"
										autocomplete="off" /></td>

								</tr>
							</table>
							<%@ page import="net.tanesha.recaptcha.ReCaptcha"%>
							<%@ page import="net.tanesha.recaptcha.ReCaptchaFactory"%>
							<%
    						ReCaptcha c = ReCaptchaFactory.newReCaptcha("6LeM_egSAAAAALCCPiJlsKQkG5rY6jHfSqtU03_J", "6LeM_egSAAAAAGaA2ePbaBwrUHET4x5YMdQJtbJh", false);
    						out.print(c.createRecaptchaHtml(null, null));
    						%>
							<div>
								<input type="submit" value="Submit">
							</div>
						</form>
					</div>
				</c:otherwise>
			</c:choose>
		</c:when>
		<c:otherwise>
			<div>You're already logged in. Now, don't be greedy, one
				account should be enough.</div>
		</c:otherwise>
	</c:choose>
</div>