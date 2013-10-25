package amu.action;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import amu.database.BookDAO;
import amu.database.ListDAO;
import amu.database.OrderDAO;
import amu.model.Book;
import amu.model.Cart;
import amu.model.Customer;
import amu.model.List;
import amu.model.Order;

public class AddListAction implements Action{
	private ArrayList<Book> bookList;
	
	@Override
	public ActionResponse execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		ListDAO listDao = new ListDAO();
		
		HttpSession session = request.getSession();
		Customer customer = (Customer) session.getAttribute("customer");
		if (customer == null) {
            ActionResponse actionResponse = new ActionResponse(ActionResponseType.REDIRECT, "loginCustomer");
            actionResponse.addParameter("from", "placeOrder");
            return actionResponse;
        }
		
		String listTitle = request.getParameter("title");
		String listDescritpion = request.getParameter("description");
		
		BookDAO bookDAO = new BookDAO();
        Book book = bookDAO.findByISBN(request.getParameter("isbn"));
		bookList.add(book);
		
		
		return new ActionResponse(ActionResponseType.FORWARD, "addList");
	}
}
/*
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
        
		return new ActionResponse(ActionResponseType.REDIRECT, "viewCart"); */
