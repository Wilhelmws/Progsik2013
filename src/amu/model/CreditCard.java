package amu.model;

import java.util.Calendar;

public class CreditCard {

    private Integer id;
    private Customer customer;
    private String creditCardNumber;
    private Calendar expiryDate;
    private String cardholderName;

    public CreditCard(Integer id, Customer customer, String creditCardNumber, Calendar expiryDate, String cardholderName) {
        this.id = id;
        this.customer = customer;
        this.creditCardNumber = creditCardNumber;
        this.expiryDate = expiryDate;
        this.cardholderName = cardholderName;
    }

    public CreditCard(Customer customer, String creditCardNumber, Calendar expiryDate, String cardholderName) {
        this.id = null;
        this.customer = customer;
        this.creditCardNumber = creditCardNumber;
        this.expiryDate = expiryDate;
        this.cardholderName = cardholderName;
    }

    public Integer getId() {
        return id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    public String getMaskedCreditCardNumber() {
        StringBuilder maskedCreditCardNumber = new StringBuilder(creditCardNumber.length());
        for (int i = 0; i < creditCardNumber.length(); i++)
        {
            if (i >= creditCardNumber.length() - 4) {
                maskedCreditCardNumber.append(creditCardNumber.charAt(i));
            } else {
                maskedCreditCardNumber.append('*');
            }
        }
        return maskedCreditCardNumber.toString();
    }

    public Calendar getExpiryDate() {
        return expiryDate;
    }

    public String getCardholderName() {
        return cardholderName;
    }
}
