<div class="container">
    <h1>Select Shipping Address</h1>
    <div>
        Select a shipping address amongst the following, or <a href="addAddress.do?from=selectShippingAddress">enter a new address</a>.
    </div>
    <c:forEach var="address" items="${addresses}" varStatus="counter">
        <div>
            <h3>Address #${counter.count}</h3>
            <pre>${address.address}</pre>
            <div><a href="selectShippingAddress.do?id=${address.id}">Select shipping address</a></div><br />
            <div>
                <span><a href="editAddress.do?id=${address.id}">Edit</a></span>
                <span><a href="deleteAddress.do?id=${address.id}">Delete</a></span>                
            </div>
        </div>
    </c:forEach>
</div>
