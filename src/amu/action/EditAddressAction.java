package amu.action;

import amu.database.AddressDAO;
import amu.model.Address;
import amu.model.Customer;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

class EditAddressAction implements Action {
	private String stringAddress;
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
        Address address = addressDAO.read(Integer.parseInt(request.getParameter("id")));

        if (request.getMethod().equals("POST")) {
        	stringAddress = null;
            List<String> messages = new ArrayList<String>();
            request.setAttribute("messages", messages);
            String tempAddress = request.getParameter("address");
            if(security.InputControl.ValidateInput(tempAddress)){
            	messages.add("Invalid Syntax Used.");
            }else{
            	stringAddress = tempAddress;
            	address.setAddress(stringAddress);
            }
            if(stringAddress != null){
	            if (addressDAO.edit(address)) {
	                return new ActionResponse(ActionResponseType.REDIRECT, "viewCustomer");
	            }
            }
            if(messages.size() == 0) messages.add("An error occured.");
        }
        List<Address> customerAddresses = addressDAO.browse(customer);
        for (int i = 0; i < customerAddresses.size(); i++) {
        	if (customerAddresses.get(i).getId() == Integer.parseInt(request.getParameter("id"))){
            	// (request.getMethod().equals("GET")) 
                request.setAttribute("address", address);
                return new ActionResponse(ActionResponseType.FORWARD, "editAddress");
        	}
        }
        return new ActionResponse(ActionResponseType.REDIRECT, "viewCustomer");
    }

}
