package com.example.cashregisterapp_assignment2_jeyakumariramji;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class ManagerActivity extends AppCompatActivity implements View.OnClickListener {
    Button historyBtn, restockBtn;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);
        historyBtn=findViewById(R.id.buttonHistory);
        restockBtn=findViewById(R.id.buttonRestock);
        historyBtn.setOnClickListener(this);
        restockBtn.setOnClickListener(this);

        // Set up Back button functionality
        ImageButton backArrowButton = findViewById(R.id.backArrowButton);
        backArrowButton.setOnClickListener(v -> finish());

    }


    @Override
    public void onClick(View view) {
        if(view.getId()== R.id.buttonHistory){
            Intent intent1 = new Intent (ManagerActivity.this, ShowHistoryActivity.class);
            startActivity(intent1);
        } else {
            Intent intent2 = new Intent(ManagerActivity.this, RestockActivity.class);
            startActivity(intent2);
        }
    }
}