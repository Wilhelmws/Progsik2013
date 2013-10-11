<div class="container">
    <h1>Shopping Cart</h1>
    <c:choose>
        <c:when test="${empty cart.items}">
            <div>No items in shopping cart.</div>
        </c:when>
        <c:otherwise>
            <form action="updateCart.do" method="post">
                <c:forEach items="${cart.items}" var="item">
                    <h3>${item.value.book.title.name}</h3>
                    <div>Price: ${item.value.book.price}</div>
                    <input type="hidden" name="isbn" value="${item.value.book.isbn13}" />
                    <div> Quantity:
                        <input type="text" name="quantity" value="${item.value.quantity}" />
                    </div>
                </c:forEach>
                <br />
                <input type="submit" value="Update cart" />
            </form>
            <c:choose>
                <c:when test="${cart.numberOfItems == 1}">
                    <div>Subtotal: ${cart.subtotal}</div>
                </c:when>
                <c:otherwise>
                    <div>Subtotal (${cart.numberOfItems} items): ${cart.subtotal}</div>
                </c:otherwise>
            </c:choose>
            <br />
            <div><a href="debug/list_books.jsp">Continue shopping</a></div>
            <div><a href="selectShippingAddress.do">Go to checkout</a></div>
        </c:otherwise>
    </c:choose>
</div>