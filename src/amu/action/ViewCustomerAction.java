package amu.action;

import amu.database.AddressDAO;
import amu.database.CreditCardDAO;
import amu.database.OrderDAO;
import amu.model.Address;
import amu.model.CreditCard;
import amu.model.Customer;
import amu.model.Order;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

class ViewCustomerAction implements Action {

    @Override
    public ActionResponse execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        HttpSession session = request.getSession(true);
        Customer customer = (Customer) session.getAttribute("customer");

        if (customer == null) {
            ActionResponse actionResponse = new ActionResponse(ActionResponseType.REDIRECT, "loginCustomer");
            actionResponse.addParameter("from", "viewCustomer");
            return actionResponse;
        } else {
            CreditCardDAO creditCardDAO = new CreditCardDAO();
            List<CreditCard> creditCards = creditCardDAO.browse(customer);
            request.setAttribute("creditCards", creditCards);
            
            AddressDAO addressDAO = new AddressDAO();
            List<Address> addresses = addressDAO.browse(customer);
            request.setAttribute("addresses", addresses);
            
            OrderDAO orderDAO = new OrderDAO();
            List<Order> orders = orderDAO.browse(customer);
            request.setAttribute("orders", orders);

            return new ActionResponse(ActionResponseType.FORWARD, "viewCustomer");
        }
    }
}