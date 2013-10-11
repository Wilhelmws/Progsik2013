package amu.action;

import amu.database.BookDAO;
import amu.model.Book;
import amu.model.Cart;
import amu.model.CartItem;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

class AddToCartAction implements Action {

    @Override
    public ActionResponse execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        
        Cart cart = (Cart) session.getAttribute("cart");
        
        if (cart == null)
        {
            cart = new Cart();
            session.setAttribute("cart", cart);
        }
        
        if (request.getParameter("isbn") != null && request.getParameter("quantity") != null)
        {
            BookDAO bookDAO = new BookDAO();
            Book book = bookDAO.findByISBN(request.getParameter("isbn"));
            
            cart.addItem(new CartItem(book, Integer.parseInt(request.getParameter("quantity"))));
        }

        return new ActionResponse(ActionResponseType.REDIRECT, "viewCart");
    }
    
}
