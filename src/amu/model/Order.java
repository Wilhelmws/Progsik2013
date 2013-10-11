package amu.model;

import java.util.Calendar;

public class Order {
    
    private Integer id;
    private Customer customer;
    private Address address;
    private Calendar createdDate;
    private String value;
    private int status;
    
    // TODO: Add OrderItems

    public Order(int id, Customer customer, Address address, Calendar createdDate, String value, int status) {
        this.id = id;
        this.customer = customer;
        this.address = address;
        this.createdDate = createdDate;
        this.value = value;
        this.status = status;
    }

    public Order(Customer customer, Address address, String subtotal) {
        this.id = null;
        this.customer = customer;
        this.address = address;
        this.createdDate = null;
        this.value = subtotal;
        this.status = 0;
    }

    public Integer getId() {
        return id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Address getAddress() {
        return address;
    }

    public Calendar getCreatedDate() {
        return createdDate;
    }

    public String getValue() {
        return value;
    }

    public int getStatus() {
        return status;
    }
    
    public String getStatusText() {
        switch (status)
        {
            case 2: 
                return "Delivered";
            case 1:
                return "Shipped";
            case 0:
            default:
                return "Pending";
            case -1:
                return "Canceled";
        }
    }
}
