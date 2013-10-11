package amu.action;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpServletRequest;

public class ActionFactory implements ServletContextListener {

    private static final Map<String, Action> actions = fillActionMap();

    private static Map<String, Action> fillActionMap() {
        Map<String, Action> map = new HashMap<String, Action>();

        // Address actions
        map.put("addAddress", new AddAddressAction());
        map.put("editAddress", new EditAddressAction());
        map.put("deleteAddress", new DeleteAddressAction());
        
        // Book actions
        map.put("bookNotFound", new ForwardAction("bookNotFound"));
        map.put("viewBook", new ViewBookAction());
        
        // Cart actions
        map.put("addBookToCart", new AddToCartAction());
        map.put("updateCart", new UpdateCartAction());
        map.put("viewCart", new ViewCartAction());
        
        // Checkout process
        map.put("selectShippingAddress", new SelectShippingAddressAction());
        map.put("selectPaymentOption", new SelectPaymentOptionAction());
        map.put("reviewOrder", new ReviewOrderAction());
        map.put("placeOrder", new PlaceOrderAction());
        map.put("placeOrderError", new ForwardAction("placeOrderError"));
        map.put("placeOrderSuccessful", new ForwardAction("placeOrderSuccessful"));
        
        // Credit card actions
        map.put("addCreditCard", new AddCreditCardAction());
        map.put("deleteCreditCard", new DeleteCreditCardAction());

        // Customer actions
        map.put("loginCustomer", new LoginCustomerAction());
        map.put("loginError", new ForwardAction("loginError"));
        map.put("registerCustomer", new RegisterCustomerAction());
        map.put("registrationError", new ForwardAction("registrationError"));        
        map.put("activateCustomer", new ActivateCustomerAction());
        map.put("activationError", new ForwardAction("activationError"));
        map.put("activationSuccessful", new ForwardAction("activationSuccessful"));
        map.put("viewCustomer", new ViewCustomerAction());
        map.put("changePassword", new ChangePasswordAction());
        map.put("changeEmail", new ChangeEmailAction());
        map.put("changeName", new ChangeNameAction());
        
        // Customer support
        map.put("customerSupport", new CustomerSupportAction());
        map.put("customerSupportSuccessful", new ForwardAction("customerSupportSuccessful"));

        return Collections.unmodifiableMap(map);
    }

    public static Action getAction(HttpServletRequest request) {
        // Strip leading '/' and trailing '.do'
        String key = request.getServletPath().substring(1,request.getServletPath().length()-3);
        return actions.get(key);
    }
    
    public static boolean hasKey(String key) {
        return actions.containsKey(key);
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // This function is run by the web application container during startup
        // due to config in web.xml. While this function does nothing in itself,
        // the static initializer (fillActionMap) is executed immediately before this,
        // and that's what we want to happen here.
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // Empty
    }
}