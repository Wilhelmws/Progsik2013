package amu.action;

import amu.database.OrderDAO;
import amu.model.Cart;
import amu.model.Customer;
import amu.model.Order;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

class PlaceOrderAction implements Action {

    public PlaceOrderAction() {
    }

    @Override
    public ActionResponse execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
            session.setAttribute("cart", cart);
            return new ActionResponse(ActionResponseType.REDIRECT, "viewCart");
        }
        
        Customer customer = (Customer) session.getAttribute("customer");
        if (customer == null) {
            ActionResponse actionResponse = new ActionResponse(ActionResponseType.REDIRECT, "loginCustomer");
            actionResponse.addParameter("from", "placeOrder");
            return actionResponse;
        }

        OrderDAO orderDAO = new OrderDAO();
        Order order = new Order(customer, cart.getShippingAddress(), cart.getSubtotal().toString());
        
        if (orderDAO.add(order))
        {
            cart = new Cart();
            session.setAttribute("cart", cart);
            return new ActionResponse(ActionResponseType.REDIRECT, "placeOrderSuccessful");
        } else {
            return new ActionResponse(ActionResponseType.REDIRECT, "placeOrderError");
        }
    }
}
