package amu.action;

import amu.Mailer;
import amu.database.CustomerDAO;
import amu.model.Customer;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

class RegisterCustomerAction extends HttpServlet implements Action {
    
    @Override
    public ActionResponse execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        if (request.getMethod().equals("POST")) {
            CustomerDAO customerDAO = new CustomerDAO();
            Customer customer = customerDAO.findByEmail(request.getParameter("email"));

            if (customer == null) {
                customer = new Customer();
                customer.setEmail(request.getParameter("email"));
                customer.setName(request.getParameter("name"));
                customer.setPassword(CustomerDAO.hashPassword(request.getParameter("password")));
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
