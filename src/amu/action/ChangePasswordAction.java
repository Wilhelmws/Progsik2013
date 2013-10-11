    String[] password = request.getParameterValues("password");
            if (!customer.getPassword().equals(CustomerDAO.hashPassword(password[0]))) {
            	messages.add("Old password is wrong");
            	System.out.println("customer pw: " + customer.getPassword());
            	System.out.println("customer hashed pw: " + CustomerDAO.hashPassword(password[0]));
            	System.out.println("custoemr pw from gui " + password[0]);
            	System.out.println(customer.getPassword().equals(CustomerDAO.hashPassword(password[0])));
            	return new ActionResponse(ActionResponseType.FORWARD, "changePassword");
            }

            // Validate that new email is typed in the same both times
            if (password[1].equals(password[2]) == false) {
                messages.add("Password and repeated password did not match. Please try again.");
                return new ActionResponse(ActionResponseType.FORWARD, "changePassword");
            }
            
            if (password[1].equals(password[0])) {
            	messages.add("Old and new password can not be the same");
            	return new ActionResponse(ActionResponseType.FORWARD, "changePassword");
            }