package amu.model;

public class Address {
    
    private Integer id;
    private Customer customer;
    private String address;
    
    public Address(int id, Customer customer, String address) {
        this.id = id;
        this.customer = customer;
        this.address = address;
    }

    public Address(Customer customer, String address) {
        this.id = null;
        this.customer = customer;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
