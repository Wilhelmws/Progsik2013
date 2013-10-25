package amu.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import amu.database.OrderDAO;
import amu.model.Cart;
import amu.model.Customer;
import amu.model.Order;

public class EditOrderAction implements Action{

	@Override
	public ActionResponse execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		OrderDAO orderDAO = new OrderDAO();
		Order order = orderDAO.findByOrderID(Integer.parseInt(request.getParameter("orderID")));
		
		HttpSession session = request.getSession();
		Customer customer = (Customer) session.getAttribute("customer");
        Cart cart = (Cart) session.getAttribute("cart");
        
        if (cart == null)
        {
            cart = new Cart();
            session.setAttribute("cart", cart);
        }
        
        cart = orderDAO.getOrderItems(order.getId(), customer.getId());
        session.setAttribute("cart", cart);
        session.setAttribute("editChart", "editChart");
        session.setAttribute("order", order);
        
		return new ActionResponse(ActionResponseType.REDIRECT, "viewCart");
	}

}
