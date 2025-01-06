package com.example.cashregisterapp_assignment2_jeyakumariramji;

import android.app.Application;
import java.util.ArrayList;
public class MyApp extends Application {
    //application data
    //2 array lists: 1 product type array list, 1 history type
    public ArrayList<Product> productlist = new ArrayList<>(0);
    public ArrayList<History> historyList = new ArrayList<>(0);

    //function that intializes the productList with hardcoded data
    public void setProductlistData() {
        if (productlist.isEmpty()) {
            productlist.add(new Product("Pants", 20.44, 10));
            productlist.add(new Product("Shoes", 10.44, 100));
            productlist.add(new Product("Hats", 5.99, 30));
        }
    }

}