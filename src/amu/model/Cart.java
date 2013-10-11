package amu.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Cart implements Serializable {

    private Map<String, CartItem> items = new HashMap<String, CartItem>();
    private Address shippingAddress = null;
    private CreditCard creditCard = null;

    public Map<String, CartItem> getItems() {
        return items;
    }
    
    public boolean hasItemByISBN(String isbn) {
        if (items.get(isbn) == null) {
            return false;
        } else {
            return true;
        }
    }

    public CartItem getItemByISBN(String isbn) {
        return items.get(isbn);
    }
    
    public void updateItem(CartItem item) {
        String isbn = item.getBook().getIsbn13();
        if (hasItemByISBN(isbn)) {
            if (item.getQuantity() > 0) {
                items.put(isbn, item);
            } else {
                items.remove(isbn);
            }
        } else {
            items.put(isbn, item);
        }
    }

    public void addItem(CartItem item) {
        String isbn = item.getBook().getIsbn13();
        if (hasItemByISBN(isbn)) {
            if (items.get(isbn).getQuantity() + item.getQuantity() > 0) {
                items.get(isbn).addQuantity(item.getQuantity());
            } else {
                items.remove(isbn);
            }
        } else {
            items.put(isbn, item);
        }
    }

    public int getNumberOfItems() {
        int numberOfItems = 0;
        for (CartItem item : items.values()) {
            numberOfItems += item.getQuantity();
        }
        return numberOfItems;
    }

    public Float getSubtotal() {
        Float subtotal = new Float(0);
        for (CartItem item : items.values()) {
            subtotal += item.getQuantity() * item.getBook().getPrice();
        }
        return subtotal;
    }

    public Address getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(Address shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public CreditCard getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(CreditCard creditCard) {
        this.creditCard = creditCard;
    }
}
