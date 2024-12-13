package com.example.dec13_calculatoractivity_jeyakumari;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;  // Add this import for List


public class MainActivity extends AppCompatActivity {

    private Calculator calculator = new Calculator();
    private TextView resultTextView, historyTextView;
    private boolean isAdvancedMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the header TextView
        TextView headerTextView = findViewById(R.id.headerTextView);

        // Set text and background color for the header
        headerTextView.setText("Assignment1");
        headerTextView.setBackgroundColor(Color.parseColor("#FF5722")); // Set background color
        headerTextView.setTextColor(Color.WHITE);

        resultTextView = findViewById(R.id.resultTextView);
        historyTextView = findViewById(R.id.historyTextView);
        Button modeSwitchButton = findViewById(R.id.modeSwitchButton);


        View.OnClickListener buttonClickListener = view -> {
            Button button = (Button) view;
            String value = button.getText().toString();

            try {
                if ("=".equals(value)) {
                    double result = calculator.calculate();
                    resultTextView.setText(String.valueOf(result));

                    if (isAdvancedMode) {
                        List<String> inputList = calculator.getInputList();
                        StringBuilder expression = new StringBuilder();
                        for (String item : inputList) {
                            expression.append(item).append(" ");
                        }
                        expression.append("= ").append(result);
                        historyTextView.append(expression.toString() + "\n");
                    }

                    calculator.clear();
                } else if ("C".equals(value)) {
                    resultTextView.setText("0");
                    calculator.clear();
                } else {
                    calculator.push(value);
                    resultTextView.setText(resultTextView.getText().toString() + value);
                }
            } catch (IllegalArgumentException e) {
                resultTextView.setText("Error: " + e.getMessage());
            }
        };

        modeSwitchButton.setOnClickListener(view -> {
            isAdvancedMode = !isAdvancedMode;
            modeSwitchButton.setText(isAdvancedMode ? "Standard - No History" : "Advance - With History");

            if (isAdvancedMode) {
                // Start the HistoryActivity with the history list
                Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
                StringBuilder historyBuilder = new StringBuilder();
                for (String entry : calculator.getHistory()) {
                    historyBuilder.append(entry).append("\n");
                }
                intent.putExtra("history", historyBuilder.toString());
                startActivity(intent);
            }
        });


        // Add new button IDs for parentheses and decimal point
        int[] buttonIds = {
                R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
                R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9,
                R.id.btnPlus, R.id.btnMinus, R.id.btnMultiply, R.id.btnDivide,
                R.id.btnClear, R.id.btnEquals, R.id.btnLeftParen, R.id.btnRightParen, R.id.btnDot
        };

        for (int id : buttonIds) {
            findViewById(id).setOnClickListener(buttonClickListener);
        }
    }
}