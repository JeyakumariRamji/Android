package com.example.cashregisterapp_assignment2_jeyakumariramji;

import java.util.Date;

public class History {
    private final String type;
    private final double price;
    private int quantity;
    private final Date date;

    public History(String type, double price, int quantity, Date date) {
        this.type = type;
        this.price = price;
        this.quantity = quantity;
        this.date = date;
    }

    // Add getters and setters as needed
    public String getType() {
        return type;
    }

    public double getPrice() {
        return price;
    }

    public Date getDate() {
        return date;
    }
    public  int getQuantity() {
        return quantity;
    }
}
