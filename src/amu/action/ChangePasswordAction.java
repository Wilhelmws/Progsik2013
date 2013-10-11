package amu.action;

import amu.database.CustomerDAO;
import amu.model.Customer;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

class ChangePasswordAction implements Action {

    @Override
    public ActionResponse execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(true);
        Customer customer = (Customer) session.getAttribute("customer");

        if (customer == null) {
            ActionResponse actionResponse = new ActionResponse(ActionResponseType.REDIRECT, "loginCustomer");
            actionResponse.addParameter("from", "changePassword");
            return actionResponse;
        }

        if (request.getMethod().equals("POST")) {
            List<String> messages = new ArrayList<String>();
            request.setAttribute("messages", messages);

            String[] password = request.getParameterValues("password");

            // Validate that new email is typed in the same both times
            if (password[0].equals(password[1]) == false) {
                messages.add("Password and repeated password did not match. Please try again.");
                return new ActionResponse(ActionResponseType.FORWARD, "changePassword");
            }

            // Validation OK, do business logic
            CustomerDAO customerDAO = new CustomerDAO();
            customer.setPassword(CustomerDAO.hashPassword(password[0]));
            if (customerDAO.edit(customer) == false) {
                messages.add("An error occured.");
                return new ActionResponse(ActionResponseType.FORWARD, "changePassword");
            }
            
            // Email change successful, return to viewCustomer
            return new ActionResponse(ActionResponseType.REDIRECT, "viewCustomer");

        } 
        
        // (request.getMethod().equals("GET")) 
        return new ActionResponse(ActionResponseType.FORWARD, "changePassword");
    }
}
