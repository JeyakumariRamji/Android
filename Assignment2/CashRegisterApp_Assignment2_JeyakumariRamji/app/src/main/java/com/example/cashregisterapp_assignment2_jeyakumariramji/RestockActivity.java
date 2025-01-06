package com.example.cashregisterapp_assignment2_jeyakumariramji;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

public class RestockActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener {
    EditText quantityET;
    Button okBtn, cancelBtn;
    ListView restockListView;
    ListDataAdapter adapter;
    int selectedIndex = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restock);
        initializeAllViews();
        setListData();
        okBtn.setOnClickListener(this);
        cancelBtn.setOnClickListener(this);
        restockListView.setOnItemClickListener(this);

        // Set up Back button functionality
        ImageButton backArrowButton = findViewById(R.id.backArrowButton);
        backArrowButton.setOnClickListener(v -> finish());
    }

    private void setListData() {
        adapter = new ListDataAdapter(RestockActivity.this, ((MyApp) getApplication()).productlist);
        restockListView.setAdapter(adapter);
    }

    private void initializeAllViews() {
        quantityET = findViewById(R.id.editTextQuantity);
        okBtn = findViewById(R.id.okButtonR);
        cancelBtn = findViewById(R.id.cancelButtonR);
        restockListView = findViewById(R.id.productListView);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.okButtonR) {
            if (quantityET.getText().toString().equals("") || selectedIndex == -1) {
                Toast.makeText(this, "All fields are required to be filled", Toast.LENGTH_SHORT).show();
            } else {
                Product p1 = ((MyApp) getApplication()).productlist.get(selectedIndex);
                p1.setQuantity(p1.getQuantity() + Integer.parseInt(quantityET.getText().toString()));
                ((MyApp) getApplication()).productlist.set(selectedIndex, p1);
                adapter.notifyDataSetChanged();
                selectedIndex = -1;
                quantityET.setText("");
            }
        } else {
            finish();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        selectedIndex = i;
        Toast.makeText(this, ((MyApp) getApplication()).productlist.get(i).getType(), Toast.LENGTH_SHORT).show();
    }
}
