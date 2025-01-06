package com.example.cashregisterapp_assignment2_jeyakumariramji;

public class Product {
    private String type;
    private double price;
    private int quantity;
    private int id;
    public Product(String type, double price, int quantity) {
        this.type = type;
        this.price = price;
        this.quantity = quantity;
    }
    public String getType() {
        return type;
    }
    public double getPrice() {
        return price;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}