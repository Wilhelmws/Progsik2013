package amu.action;

import amu.database.BookDAO;
import amu.model.Book;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

class ViewBookAction implements Action {

    @Override
    public ActionResponse execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        BookDAO bookDAO = new BookDAO();
        Book book = bookDAO.findByISBN(request.getParameter("isbn"));
        
        if (book != null) {
            request.setAttribute("book", book);
        }
        
        return new ActionResponse(ActionResponseType.FORWARD, "viewBook");
    }
}
