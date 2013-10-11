package amu.action;

import amu.database.AddressDAO;
import amu.model.Address;
import amu.model.Customer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

class AddAddressAction implements Action {

    @Override
    public ActionResponse execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(true);
        Customer customer = (Customer) session.getAttribute("customer");

        // Handle referrals
        Map<String, String> values = new HashMap<String, String>();
        request.setAttribute("values", values);
        if (ActionFactory.hasKey(request.getParameter("from"))) {
            values.put("from", request.getParameter("from"));
        }

        if (customer == null) {
            ActionResponse actionResponse = new ActionResponse(ActionResponseType.REDIRECT, "loginCustomer");
            actionResponse.addParameter("from", "addAddress");
            return actionResponse;
        }

        // Non-idempotent add address request
        if (request.getMethod().equals("POST")) {
            List<String> messages = new ArrayList<String>();
            request.setAttribute("messages", messages);

            AddressDAO addressDAO = new AddressDAO();
            Address address = new Address(customer, request.getParameter("address"));

            if (addressDAO.add(address)) {
                if (ActionFactory.hasKey(request.getParameter("from"))) {
                    return new ActionResponse(ActionResponseType.REDIRECT, request.getParameter("from"));
                } else {
                    // Return to viewCustomer from addAddress by default
                    return new ActionResponse(ActionResponseType.REDIRECT, "viewCustomer");
                }
            }

            messages.add("An error occured.");
            request.setAttribute("address", address);
        }

        // (request.getMethod().equals("GET")) 
        return new ActionResponse(ActionResponseType.FORWARD, "addAddress");
    }
}
