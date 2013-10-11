<div class="container">
    <h1>Select Payment Option</h1>
    <div>
        Select a credit card amongst the following, or <a href="addCreditCard.do?from=selectPaymentOption">add a new card</a>.
    </div>
    <form method="post" action="selectPaymentOption.do">
        <c:forEach var="creditCard" items="${creditCards}" varStatus="counter">
            <div>
                <input type="radio" name="creditCardID" value="${creditCard.id}" />
                <span>Credit card #${counter.count}</span><br />
                <div>Credit card number: ${creditCard.maskedCreditCardNumber}</div>
                <div>Expiry date: <fmt:formatDate value="${creditCard.expiryDate.time}" type="date" dateStyle="short" /></div>
                <div>Cardholder's name: ${creditCard.cardholderName}</div>
            </div>
            <div>
                <label for="cardSecurityCode">Card security code: </label>
                <input id="cardSecurityCode" name="cardSecurityCode" type="text" />
            </div>
            <input type="submit" value="Select credit card" /> 
        </c:forEach>
    </form>
</div>
