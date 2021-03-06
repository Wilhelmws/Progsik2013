package amu.action;

import amu.Mailer;
import amu.model.Customer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

class CustomerSupportAction implements Action {
	private String subject;
	private String content;
	
    @Override
    public ActionResponse execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession(true);
        Customer customer = (Customer) session.getAttribute("customer");

        if (customer == null) {
            ActionResponse actionResponse = new ActionResponse(ActionResponseType.REDIRECT, "loginCustomer");
            actionResponse.addParameter("from", "customerSupport");
            return actionResponse;
        }

        String tempSub = request.getParameter("subject");
        String tempCont = request.getParameter("content");
        if(security.InputControl.ValidateInput(tempSub)){
        	return new ActionResponse(ActionResponseType.FORWARD, "errorPage");
        }else{
        	subject = tempSub;
        }
        if(security.InputControl.ValidateInput(tempCont)){
        	return new ActionResponse(ActionResponseType.FORWARD, "errorPage");
        }else{
        	content = tempCont;
        }
        if (request.getMethod().equals("POST")) {
            Mailer.send(request.getParameter("department"), 
            		subject, 
            		content, 
                    request.getParameter("fromAddr"), 
                    request.getParameter("fromName"));
            // TODO: Send receipt to customer
            return new ActionResponse(ActionResponseType.REDIRECT, "customerSupportSuccessful");
        } 

        return new ActionResponse(ActionResponseType.FORWARD, "customerSupport");
    }
}
