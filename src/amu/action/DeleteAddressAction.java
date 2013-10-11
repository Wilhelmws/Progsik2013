package amu.action;

import amu.database.AddressDAO;
import amu.model.Address;
import amu.model.Customer;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

class DeleteAddressAction implements Action {

    @Override
    public ActionResponse execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(true);
        Customer customer = (Customer) session.getAttribute("customer");

        if (customer == null) {
            ActionResponse actionResponse = new ActionResponse(ActionResponseType.REDIRECT, "loginCustomer");
            actionResponse.addParameter("from", "viewCustomer"); 
            return actionResponse;
        }

        AddressDAO addressDAO = new AddressDAO();
        Address address;

        if (request.getMethod().equals("POST")) {
            List<String> messages = new ArrayList<String>();
            request.setAttribute("messages", messages);

            if (addressDAO.delete(Integer.parseInt(request.getParameter("id")))) {
                return new ActionResponse(ActionResponseType.REDIRECT, "viewCustomer");
            }

            messages.add("An error occured."); 
        }

        // (request.getMethod().equals("GET")) 
        address = addressDAO.read(Integer.parseInt(request.getParameter("id"))); 
        request.setAttribute("address", address);
        return new ActionResponse(ActionResponseType.FORWARD, "deleteAddress");
    }
}
