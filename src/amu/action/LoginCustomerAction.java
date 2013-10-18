package amu.action;

import amu.database.CustomerDAO;
import amu.model.Customer;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

class LoginCustomerAction implements Action {
	private String password;
	private String email;
	
    @Override
    public ActionResponse execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        Map<String, String> values = new HashMap<String, String>();
        request.setAttribute("values", values);
        if (ActionFactory.hasKey(request.getParameter("from"))) {
            values.put("from", request.getParameter("from"));
        }

        if (request.getMethod().equals("POST")) {
            Map<String, String> messages = new HashMap<String, String>();
            request.setAttribute("messages", messages);

        	String tempEmail = request.getParameter("email");
        	if(security.InputControl.ValidateInput(tempEmail)){
        		messages.put("email", "Invalid Syntax used.");
        		return new ActionResponse(ActionResponseType.FORWARD, "loginCustomer");
        	}else{
        		email = tempEmail;
        	}
            CustomerDAO customerDAO = new CustomerDAO();
            Customer customer = customerDAO.findByEmail(request.getParameter("email"));
            
            values.put("email", request.getParameter("email"));
            if (customer != null) {
            	String tempPassword = request.getParameter("password");
            	if(security.InputControl.ValidateInput(tempPassword)){
            		messages.put("password", "Invalid Syntax used.");
            		return new ActionResponse(ActionResponseType.FORWARD, "loginCustomer");
            	}else{
            		password = tempPassword;
            	}
                values.put("email", email);
                if (customer.getActivationToken() == null) {
                	Thread.sleep(2000);
                    if (customer.getPassword().equals(CustomerDAO.hashPassword(password))) {

                        HttpSession session = request.getSession(true);
                        session.setAttribute("customer", customer);
                        if (ActionFactory.hasKey(request.getParameter("from"))) {
                            return new ActionResponse(ActionResponseType.REDIRECT, request.getParameter("from"));
                        }
                    } else { // Wrong password
                    	Thread.sleep(2000);
                        messages.put("emailorpassword", "Password or email was incorrect.");
                    }
                } else { // customer.getActivationToken() != null
                    return new ActionResponse(ActionResponseType.REDIRECT, "activateCustomer");
                }
            } else { // findByEmail returned null -> no customer with that email exists
            	Thread.sleep(2000);
                messages.put("emailorpassword", "Password or email was incorrect.");
            }

            // Forward to login form with error messages
            return new ActionResponse(ActionResponseType.FORWARD, "loginCustomer");
        }

        return new ActionResponse(ActionResponseType.FORWARD, "loginCustomer");
    }
}
