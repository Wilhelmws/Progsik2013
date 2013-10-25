package amu.action;

import amu.model.Cart;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

class ViewCartAction implements Action {

    public ViewCartAction() {
    }

    @Override
    public ActionResponse execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        
        if (cart == null)
        {
            cart = new Cart();
            session.setAttribute("cart", cart);
        }
        
        if(session.getAttribute("editChart") != null){
        	if(session.getAttribute("editChart").equals("cancelCart")){
        		return new ActionResponse(ActionResponseType.REDIRECT, "placeOrder");
        		
        	}
        }
        
        return new ActionResponse(ActionResponseType.FORWARD, "viewCart");
    }
    
}
