<div class="container">

	<c:choose>
        <c:when test="${empty order}">
            <div>Items not found.</div>
        </c:when>
        <c:otherwise>
			ID: ${order.id} <br>
        	Order: ${order.value}
        </c:otherwise>
	</c:choose>
	
</div>