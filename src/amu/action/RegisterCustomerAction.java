package amu.action;

import java.util.HashMap;
import java.util.Map;

import amu.Mailer;
import amu.database.CustomerDAO;
import amu.model.Customer;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

class RegisterCustomerAction extends HttpServlet implements Action {
    private String email;
    private String name;
    private String password;
    
    @Override
    public ActionResponse execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        if (request.getMethod().equals("POST")) {
        	Map<String, String> messages = new HashMap<String, String>();
            request.setAttribute("messages", messages);
            
        	String tempEmail = request.getParameter("email");
        	if(security.InputControl.ValidateInput(tempEmail)){
        		messages.put("email", "Invalid Syntax Used.");
        		return new ActionResponse(ActionResponseType.FORWARD, "registerCustomer");
        	}else{
        		email = tempEmail;
        	}
        	
            CustomerDAO customerDAO = new CustomerDAO();
            Customer customer = customerDAO.findByEmail(email);

            if (customer == null) {
                customer = new Customer();
                customer.setEmail(email);
                
            	String tempName = request.getParameter("name");
            	if(security.InputControl.ValidateInput(tempName)){
            		messages.put("name", "Invalid Syntax Used.");
            		return new ActionResponse(ActionResponseType.FORWARD, "registerCustomer");
            	}else{
            		name = tempName;
            	}
                customer.setName(name);
                String tempPassword = request.getParameter("password");
                if(security.InputControl.ValidateInput(tempPassword)){
            		messages.put("password", "Invalid Syntax Used.");
            		return new ActionResponse(ActionResponseType.FORWARD, "registerCustomer");	
                }else{
                	password = tempPassword;
                }
                customer.setPassword(CustomerDAO.hashPassword(password));
                customer.setActivationToken(CustomerDAO.generateActivationCode());
                customer = customerDAO.register(customer);
                
                ActionResponse actionResponse = new ActionResponse(ActionResponseType.REDIRECT, "activateCustomer");
                actionResponse.addParameter("email", customer.getEmail());
                
                StringBuilder sb = new StringBuilder();
                sb.append("Welcome to Amu-Darya, the really insecure bookstore!\n\n");
                sb.append("To activate your account, click <a href='http://");
                sb.append(request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/");
                sb.append(actionResponse.getURL() + actionResponse.getParameterString());
                sb.append("&activationToken=" + customer.getActivationToken());
                sb.append("'>here</a>, or use this activation code: " + customer.getActivationToken());
               
                Mailer.send(customer.getEmail(), "Activation required", sb.toString());
 
                return actionResponse;
            } else {
                return new ActionResponse(ActionResponseType.REDIRECT, "registrationError");
            }
        }
        
        // Else we show the register form
        return new ActionResponse(ActionResponseType.FORWARD, "registerCustomer");
    }
}
