package amu.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import amu.database.CustomerDAO;
import amu.model.Cart;
import amu.model.Customer;

public class AddReviewAction implements Action {

	@Override
	public ActionResponse execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//Get the current session and the customer
		HttpSession session = request.getSession();
        Customer customer = (Customer) session.getAttribute("customer");
        
        //If the user is not logged in, redirect to the login page
        if (customer == null) {
            ActionResponse actionResponse = new ActionResponse(ActionResponseType.REDIRECT, "loginCustomer");
            
            //The page should be directed back here
            actionResponse.addParameter("from", "addReview");
            return actionResponse;
        }
        
        return new ActionResponse(ActionResponseType.FORWARD, "addReview");
	}

}
