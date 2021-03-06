package amu.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import amu.Mailer;
import amu.database.CustomerDAO;
import amu.database.ReviewDAO;
import amu.model.Cart;
import amu.model.Customer;
import amu.model.Review;

public class AddReviewAction implements Action {

	@Override
	public ActionResponse execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//Get the current session and the customer
		HttpSession session = request.getSession();
        Customer customer = (Customer) session.getAttribute("customer");
        
        //If the user is not logged in, redirect to the login page
        if (customer == null) {
            ActionResponse actionResponse = new ActionResponse(ActionResponseType.REDIRECT, "loginCustomer");
            actionResponse.addParameter("from", "addReview");
            actionResponse.addParameter("isbn", request.getParameter("bookid"));
            actionResponse.addParameter("content",  request.getParameter("content"));
            return actionResponse;
        }
        
        else if (request.getMethod().equals("POST")) {
        	String user = customer.getName();
            String tempContent = request.getParameter("content");
            String content = "";
            if(security.InputControl.ValidateInput(tempContent)){
            	return new ActionResponse(ActionResponseType.REDIRECT, "errorPage");
            }else{
            	content = tempContent;
            }
            int bookid = Integer.parseInt(request.getParameter("bookid"));
            
            ReviewDAO reviewDAO = new ReviewDAO();
            Review review = new Review(user, content, bookid);
            
            if(reviewDAO.add(review)){
            	System.out.println("added: " + user + " Message: " + content + " book ID: " + bookid);
            }
          
            //TODO: Create a new action response.
//            return new ActionResponse(ActionResponseType.REDIRECT, "customerSupportSuccessful");
        }
        else if(request.getParameter("from").equals("loginCustomer")){
        	ActionResponse ar = new ActionResponse(ActionResponseType.REDIRECT, "viewBook");
        	ar.addParameter("isbn", request.getParameter("isbn"));
        	ar.addParameter("content", request.getParameter("content"));
        	
        	return ar;
        }
        
        ActionResponse ar = new ActionResponse(ActionResponseType.REDIRECT, "viewBook");
        ar.addParameter("isbn", request.getParameter("bookid"));
        ar.addParameter("from", "addReview");
        
        return ar;
	}

}
