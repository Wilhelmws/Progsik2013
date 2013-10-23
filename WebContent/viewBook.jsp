<style type="text/css">
    #main
    {
    position:relative;
        width:200px;
    }

    .floatdiv
    {
    float:left;
        width=80px
    }
</style>

<div class="container">
    <h1>Book</h1>
    <c:choose>
        <c:when test="${empty book}">
            <h2>Book not found!</h2>
            <div class = "index-item"><a href="debug/list_books.jsp">List books</a></div>
        </c:when>
        <c:otherwise>
            <h2>${book.title.name}</h2>
            <div>
                <ul>
                    <li>
                        <b>Authors:</b> 
                        <c:forEach items="${book.author}" var="author" varStatus="it">
                            ${author.name}<c:if test="${!it.last}">, </c:if>
                        </c:forEach>
                    </li>
                    <li><b>Publisher:</b> ${book.publisher.name}</li>
                    <li><b>Published:</b> ${book.published}</li>
                    <li><b>Edition:</b> ${book.edition} (${book.binding})</li>
                    <li><b>ISBN:</b> ${book.isbn13}</li>
                    <li><b>Price:</b> ${book.price}</li>
                </ul>
            </div>
            <div>
                ${book.description}
            </div>
            <div>
                <form action="addBookToCart.do" method="post">
                    <input type="hidden" name="isbn" value="${book.isbn13}" />
                    <input type="text" name="quantity" value="1" />
                    <input type="submit" value="Add to cart" />
                </form>
            </div>
            
            <!-- Review Books -->
            <div>
            	<ul>
            	<c:choose>
            		<c:when test="${empty reviews}">
            			<div>
    	        			<form action="addReview.do" method="post">
    	        				Message:<br>
    	        				<c:choose>
    	        					<c:when test="${empty content}">
    	        						<textarea name="content" rows="10" cols="40"></textarea><br>
    	        					</c:when>
    	        					<c:otherwise>
    	        						<textarea name="content" rows="10" cols="40">${content}</textarea><br>
    	        					</c:otherwise>
    	        				</c:choose>
        	    				<input type="hidden" name="bookid" value="${book.id}"/><br>
        	    				<input type="submit" value="Post review"/><br>
	            			</form>
            			</div>
            		
		        		<c:forEach items="${rews}" var="rev">
			        		<b>User: </b> ${rev.name} <br>
			        		<b>Votes: </b> ${rev.votes} <br>
			        		<b>Message: </b> ${rev.message} <br>
			        		
			        		<form action="vote.do" method="post">
		            			<input type="submit" name="down" value="-"/>
		            			<input type="submit" name="up" value="+"/>
		            			<input type="hidden" name="review" value="${rev.id}"/>
	            			</form>
	            			
			        		<br><br>
		        		</c:forEach>
		        	</c:when>
		        	<c:otherwise>
		        	
		        		<b>No reviews found.</b>
		        		
		        	</c:otherwise>
		        </c:choose>
            	</ul>
            </div>
            
            
        </c:otherwise>
    </c:choose>
</div>