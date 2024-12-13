package com.example.dec13_calculatoractivity_jeyakumari;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class HistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        TextView historyTextView = findViewById(R.id.historyTextView);

        // Retrieve the history from the intent
        String history = getIntent().getStringExtra("history");

        // Set the history text
        historyTextView.setText(history != null ? history : "No history available.");
    }
}
