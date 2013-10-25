package amu.action;

import amu.database.CustomerDAO;
import amu.model.Customer;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

//Imports for Captcha
import net.tanesha.recaptcha.ReCaptchaImpl;
import net.tanesha.recaptcha.ReCaptchaResponse;

class LoginCustomerAction implements Action {
	private String password;
	private String email;

	@Override
	public ActionResponse execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		Map<String, String> values = new HashMap<String, String>();
		request.setAttribute("values", values);
		if (ActionFactory.hasKey(request.getParameter("from"))) {
			values.put("from", request.getParameter("from"));
			values.put("isbn", request.getParameter("isbn"));
			values.put("content", request.getParameter("content"));
		}

		if (request.getMethod().equals("POST")) {
			Map<String, String> messages = new HashMap<String, String>();
			request.setAttribute("messages", messages);

			String tempEmail = request.getParameter("email");
			if(security.InputControl.ValidateInput(tempEmail)){
				messages.put("email", "Invalid Syntax used.");
				return new ActionResponse(ActionResponseType.FORWARD, "loginCustomer");
			}else{
				email = tempEmail;
			}
			CustomerDAO customerDAO = new CustomerDAO();
			Customer customer = customerDAO.findByEmail(request.getParameter("email"));

			String remoteAddr = request.getRemoteAddr();

			ReCaptchaImpl reCaptcha = new ReCaptchaImpl();
			reCaptcha.setPrivateKey("6LeM_egSAAAAAGaA2ePbaBwrUHET4x5YMdQJtbJh");

			String challenge = request.getParameter("recaptcha_challenge_field");
			String uresponse = request.getParameter("recaptcha_response_field");
			ReCaptchaResponse reCaptchaResponse = reCaptcha.checkAnswer(remoteAddr, challenge, uresponse);

			if (customer != null) {
				String tempPassword = request.getParameter("password");
				if(security.InputControl.ValidateInput(tempPassword)){
					messages.put("password", "Invalid Syntax used.");
					return new ActionResponse(ActionResponseType.FORWARD, "loginCustomer");
				} 

				else {
					password = tempPassword;
				}

				values.put("email", email);
				if (customer.getActivationToken() == null) {
					Thread.sleep(1000);
					if (customer.getPassword().equals(CustomerDAO.hashPassword(password))) {

						//Check if Captcha is entered correctly
						if (reCaptchaResponse.isValid()) {
							HttpSession session = request.getSession(true);
							session.setAttribute("customer", customer);

							if (ActionFactory.hasKey(request.getParameter("from"))) {
								ActionResponse ar = new ActionResponse(ActionResponseType.REDIRECT, request.getParameter("from"));
								
								//If it was redirected from viewBook
								if(request.getParameter("from").equals("addReview")){
									System.out.println("LoginCustomerAction: " + request.getParameter("from"));
									System.out.println("LoginCustomerAction: " + request.getParameter("isbn"));
									System.out.println("LoginCustomerAction: " + request.getParameter("content"));
									ar.addParameter("isbn", request.getParameter("isbn"));
									ar.addParameter("from", "loginCustomer");
									ar.addParameter("content", request.getParameter("content"));
								}
								
								System.out.println("LOGIN REDIRECT: " + request.getParameter("from"));
								
								//execute the redirect
								return ar;
							}
						} 
						else {
							messages.put("emailorpassword", "Captcha was incorrect. Are you a bot?");
						}

					}

					else { // Wrong password
						Thread.sleep(1000);
						messages.put("emailorpassword", "Password or email was incorrect.");
					}

				} else { // customer.getActivationToken() != null
					return new ActionResponse(ActionResponseType.REDIRECT, "activateCustomer");
				}
			} else { // findByEmail returned null -> no customer with that email exists
				Thread.sleep(1000);
				messages.put("emailorpassword", "Password or email was incorrect.");
			}


			values.put("email", request.getParameter("email"));


			// Forward to login form with error messages
			return new ActionResponse(ActionResponseType.FORWARD, "loginCustomer");
		}

		return new ActionResponse(ActionResponseType.FORWARD, "loginCustomer");
	}
}
