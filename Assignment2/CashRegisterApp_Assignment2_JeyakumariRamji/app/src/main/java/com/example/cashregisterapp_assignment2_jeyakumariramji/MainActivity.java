package com.example.cashregisterapp_assignment2_jeyakumariramji;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    Button one, two, three, four, five, six, seven, eight, nine, zero, clear, buy, managerBtn;
    TextView typeTV, quantityTV, totalTV;
    ListDataAdapter adapter;
    Double total = 0.0;
    ListView itemsListView;
    int index = -1;
    ArrayList<Product> stockList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        setClickListeners();
        setListData();

        itemsListView.setOnItemClickListener(this);
    }

    private void initViews() {
        typeTV = findViewById(R.id.product_textview);
        quantityTV = findViewById(R.id.quantity_tv);
        totalTV = findViewById(R.id.total_tv);
        one = findViewById(R.id.one);
        two = findViewById(R.id.two);
        three = findViewById(R.id.three);
        four = findViewById(R.id.four);
        five = findViewById(R.id.five);
        six = findViewById(R.id.six);
        seven = findViewById(R.id.seven);
        eight = findViewById(R.id.eight);
        nine = findViewById(R.id.nine);
        zero = findViewById(R.id.zero);
        clear = findViewById(R.id.clear_but);
        buy = findViewById(R.id.buyButton);
        managerBtn = findViewById(R.id.buttonManager);
        itemsListView = findViewById(R.id.listview_products);
    }

    private void setClickListeners() {
        one.setOnClickListener(this);
        two.setOnClickListener(this);
        three.setOnClickListener(this);
        four.setOnClickListener(this);
        five.setOnClickListener(this);
        six.setOnClickListener(this);
        seven.setOnClickListener(this);
        eight.setOnClickListener(this);
        nine.setOnClickListener(this);
        zero.setOnClickListener(this);
        buy.setOnClickListener(this);
        clear.setOnClickListener(this);
        managerBtn.setOnClickListener(this);
    }

    private void setListData() {
        ((MyApp) getApplication()).setProductlistData();
        stockList = ((MyApp) getApplication()).productlist;
        adapter = new ListDataAdapter(MainActivity.this, stockList);
        itemsListView.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        if (id == R.id.buyButton) {
            if (!totalTV.getText().toString().isEmpty()) {
                showAlert();
            } else {
                Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
            }
        } else if (id == R.id.clear_but) {
            quantityTV.setText("");
            totalTV.setText("");
            typeTV.setText("");
        } else if (id == R.id.buttonManager) {
            Intent intentManager = new Intent(MainActivity.this, ManagerActivity.class);
            startActivity(intentManager);
        } else {
            Button b = (Button) view;
            String num = quantityTV.getText().toString() + b.getText().toString();
            quantityTV.setText(num);
            if (index != -1) {
                total = stockList.get(index).getPrice() * Double.parseDouble(num);
                totalTV.setText(String.format(Locale.getDefault(), "%.2f", total));
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        typeTV.setText(stockList.get(i).getType());
        index = i;
        if (!quantityTV.getText().toString().isEmpty()) {
            total = Double.parseDouble(quantityTV.getText().toString()) * stockList.get(i).getPrice();
            totalTV.setText(String.format(Locale.getDefault(), "%.2f", total));
        }
    }

    private void showAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your purchase is " + quantityTV.getText().toString() + " " + stockList.get(index).getType() + " for " + totalTV.getText().toString())
                .setTitle("Thank You for your purchase")
                .setPositiveButton("OK", (dialog, id) -> {
                    quantityTV.setText("");
                    totalTV.setText("");
                    typeTV.setText("");
                });
        builder.create().show();

        Product product = ((MyApp) getApplication()).productlist.get(index);
        product.setQuantity(product.getQuantity() - Integer.parseInt(quantityTV.getText().toString()));
        ((MyApp) getApplication()).productlist.set(index, product);
        adapter.notifyDataSetChanged();

        Date date = new Date();
        History history = new History(product.getType(), total, Integer.parseInt(quantityTV.getText().toString()), date);
        ((MyApp) getApplication()).historyList.add(history);
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }
}