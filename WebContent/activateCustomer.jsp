
<div class="container">
<h1>Activate Customer</h1>
<div>Your account has been created, but before we activate it, we need to verify that the email address given belongs to you. We have sent you an email with instructions on how to activate your account. In order to activate your account you can either click the link in the email or fill in this form:</div>
<div>
    <form method="post" action="activateCustomer.do">
        <c:choose>
            <c:when test="${empty email}">
                <div>
                    Email:
                    <input type="text" name="email" value="" />
                </div>
            </c:when>
            <c:otherwise>
                <input type="hidden" name="email" value="${email}" />
            </c:otherwise>
        </c:choose>
        <div>Activation token:  <input type="text" name="activationToken" /></div>
        <div><input type="submit" value="Activate account" /></div>
    </form>
</div>
</div>