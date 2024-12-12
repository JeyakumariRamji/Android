package com.example.dec11_calculator_jeyakumari;

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

            if ("=".equals(value)) {
                int result = calculator.calculate();
                resultTextView.setText(String.valueOf(result));

                // Show result in history (if in advanced mode)
                if (isAdvancedMode) {
                    // Access the input list from the Calculator class
                    List<String> inputList = calculator.getInputList();  // Now this works because List is imported

                    // Build the string representing the calculation expression
                    StringBuilder expression = new StringBuilder();
                    for (String item : inputList) {
                        expression.append(item).append(" ");  // Add operands and operators
                    }

                    // Add the result to the expression
                    expression.append("= ").append(result);

                    // Append the expression with result to historyTextView
                    historyTextView.append(expression.toString() + "\n");
                }

                // Clear the calculator for next input
                calculator.clear();
            } else if ("C".equals(value)) {
                resultTextView.setText("0");
                calculator.clear();
            } else {
                calculator.push(value);
                resultTextView.setText(resultTextView.getText().toString() + value);
            }
        };

        // Attach listeners to buttons
        int[] buttonIds = {
                R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
                R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9,
                R.id.btnPlus, R.id.btnMinus, R.id.btnMultiply, R.id.btnDivide,
                R.id.btnClear, R.id.btnEquals
        };
        for (int id : buttonIds) {
            findViewById(id).setOnClickListener(buttonClickListener);
        }

        modeSwitchButton.setOnClickListener(view -> {
            isAdvancedMode = !isAdvancedMode;
            modeSwitchButton.setText(isAdvancedMode ? "Standard - No History" : "Advance - With History");
            historyTextView.setVisibility(isAdvancedMode ? View.VISIBLE : View.GONE);
        });
    }
}