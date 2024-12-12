package com.example.dec11_calculator_jeyakumari;

import java.util.ArrayList;
import java.util.List;
public class Calculator {
    private List<String> inputList = new ArrayList<>();
    private List<String> historyList = new ArrayList<>();  // For saving history of expressions

    public void push(String value) {
        inputList.add(value);
    }

    public int calculate() {
        // Parse the list and calculate the result
        int result = Integer.parseInt(inputList.get(0));
        for (int i = 1; i < inputList.size(); i += 2) {
            String operator = inputList.get(i);
            int operand = Integer.parseInt(inputList.get(i + 1));
            switch (operator) {
                case "+":
                    result += operand;
                    break;
                case "-":
                    result -= operand;
                    break;
                case "*":
                    result *= operand;
                    break;
                case "/":
                    if (operand != 0) result /= operand;
                    else throw new ArithmeticException("Division by zero");
                    break;
            }
        }

        // Save the expression and its result in history
        StringBuilder expression = new StringBuilder();
        for (int i = 0; i < inputList.size(); i++) {
            expression.append(inputList.get(i)).append(" ");
        }
        expression.append("= ").append(result);

        historyList.add(expression.toString());  // Add the result to the history
        return result;
    }

    public void clear() {
        inputList.clear();
    }

    // Get the history list
    public List<String> getHistory() {
        return historyList;
    }

    // Add a getter for inputList (to access it safely)
    public List<String> getInputList() {
        return new ArrayList<>(inputList);  // Return a copy of the list
    }
}
