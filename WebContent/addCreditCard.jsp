<div class="container">
<h1>Add Credit Card</h1>
	<div class="general-form">
    <form action="addCreditCard.do" method="post">
    	<table class="general-table">
        <tr>
            <td><label for="creditCardNumber">Credit Card Number: </label></td>
            <td><input id="creditCardNumber" name="creditCardNumber" type="text" value="${values.creditCardNumber}" /></td>
            <c:if test="${not empty messages.creditCardNumber}">
                <span class="error">${messages.creditCardNumber}</span>
            </c:if>
        </tr>
        <tr>
            <td><label for="cardholderName">Cardholder's name: </label></td>
            <td><input id="cardholderName" name="cardholderName" type="text" value="${values.cardholderName}" /></td>
            <c:if test="${not empty messages.cardholderName}">
                <span class="error">${messages.cardholderName}</span>
            </c:if>
        </tr>
        <tr>
            <td><label for="expiryDate">Expiry date: </label></td>
            <td><select id="expiryDate" name="expiryMonth">
                <option value="0">1</option>
                <option value="1">2</option>
                <option value="2">3</option>
                <option value="3">4</option>
                <option value="4">5</option>
                <option value="5">6</option>
                <option value="6">7</option>
                <option value="7">8</option>
                <option value="8">9</option>
                <option value="9">10</option>
                <option value="10">11</option>
                <option value="11">12</option>
            </select>
            /
            <select id="expiryDate" name="expiryYear">
                <c:forEach var="year" items="${years}">
                    <option value="${year}">${year}</option>
                </c:forEach>
            </select>
            </td>
            <c:if test="${not empty messages.expiryDate}">
                <span class="error">${messages.expiryDate}</span>
            </c:if>
        </tr>
        </table>
        <div><input type="submit" value="Submit" /></div>
    </form>
    </div>
    <c:if test="${not empty messages.error}">
        <span class="error">${messages.error}</span>
    </c:if>
</div>