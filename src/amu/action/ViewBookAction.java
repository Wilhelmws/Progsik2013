package amu.action;

import java.util.ArrayList;

import amu.database.BookDAO;
import amu.database.ReviewDAO;
import amu.model.Book;
import amu.model.Review;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

class ViewBookAction implements Action {

    @Override
    public ActionResponse execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        BookDAO bookDAO = new BookDAO();
        Book book = bookDAO.findByISBN(request.getParameter("isbn"));
        
        if (book != null) {
            request.setAttribute("book", book);
            
    		ReviewDAO reviewDAO = new ReviewDAO();
			ArrayList<Review> reviews = reviewDAO.findByBookID(book.getId());
			System.out.println("Number of reviews: " + reviews.size());
			
			if(reviews != null){
				request.setAttribute("rews", reviews);
			}
        }
        
        
        return new ActionResponse(ActionResponseType.FORWARD, "viewBook");
    }
}
