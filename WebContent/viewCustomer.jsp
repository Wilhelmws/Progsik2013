<div class="container">
    <h1>Customer options</h1>
    <div>Hello, ${customer.name}</div>
    <div>
        <div><a href="changeName.do">Change name</a></div>
        <div><a href="changeEmail.do">Change email</a></div>
        <div><a href="changePassword.do">Change password</a></div>
    </div>
    <div>
        <h2>Payment settings</h2>
        <div>
            <a href="addCreditCard.do?from=viewCustomer">Add a credit card</a>
        </div>
        <br>
        <c:forEach var="creditCard" items="${creditCards}" varStatus="counter">
            <div>
                <div>Credit card #${counter.count}</div>
                <div>Credit card number: ${creditCard.maskedCreditCardNumber}</div>
                <div>Expiry date: <fmt:formatDate value="${creditCard.expiryDate.time}" type="date" dateStyle="short" /></div>
                <div>Cardholder's name: ${creditCard.cardholderName}</div>
                <div><a href="deleteCreditCard.do?id=${creditCard.id}">Delete</a></div>
            </div>
            <br>
        </c:forEach>
    </div>
    <div>
        <h2>Address book</h2>
        <div>
            <a href="addAddress.do?from=viewCustomer">Enter a new address</a>
        </div>
        <c:forEach var="address" items="${addresses}" varStatus="counter">
            <div>
                <span>Address #${counter.count}</span>
                <pre>${address.address}</pre>
                <span><a href="editAddress.do?id=${address.id}">Edit</a></span>
                <span><a href="deleteAddress.do?id=${address.id}">Delete</a></span>
            </div>
        </c:forEach>
    </div>
    <div>
        <h2>Your orders</h2>
        <c:forEach var="order" items="${orders}" varStatus="counter">
            <div>
                <div>Order #${counter.count}</div>
                <pre>${order.address.address}</pre>
                <div>Date: <fmt:formatDate value="${order.createdDate.time}" type="date" dateStyle="short" /></div>
                <div>Value: ${order.value}</div>
                <div>Status: ${order.statusText}</div>
            </div>
        </c:forEach>
    </div>
</div>
