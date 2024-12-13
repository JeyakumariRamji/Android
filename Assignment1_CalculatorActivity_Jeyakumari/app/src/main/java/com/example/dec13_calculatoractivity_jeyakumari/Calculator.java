package com.example.dec13_calculatoractivity_jeyakumari;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Calculator {
    private List<String> inputList = new ArrayList<>();
    private List<String> historyList = new ArrayList<>();

    public void push(String value) {
        // Validate input
        if (isOperator(value) && !inputList.isEmpty() && isOperator(inputList.get(inputList.size() - 1))) {
            throw new IllegalArgumentException(" ");
        }
        inputList.add(value);
    }

    public double calculate() {
        // Convert the input list to Reverse Polish Notation (RPN)
        List<String> rpn = toRPN(inputList);

        // Evaluate the RPN expression
        double result = evaluateRPN(rpn);

        // Save the expression and its result in history
        StringBuilder expression = new StringBuilder();
        for (String item : inputList) {
            expression.append(item).append(" ");
        }
        expression.append("= ").append(result);

        historyList.add(expression.toString());
        return result;
    }

    public void clear() {
        inputList.clear();
    }

    public List<String> getHistory() {
        return historyList;
    }

    public List<String> getInputList() {
        return new ArrayList<>(inputList);
    }

    // Convert infix expression to Reverse Polish Notation (RPN)
    private List<String> toRPN(List<String> input) {
        List<String> output = new ArrayList<>();
        Stack<String> operators = new Stack<>();

        for (String token : input) {
            if (isNumber(token)) {
                output.add(token);
            } else if (isOperator(token)) {
                while (!operators.isEmpty() && !operators.peek().equals("(") &&
                        precedence(operators.peek()) >= precedence(token)) {
                    output.add(operators.pop());
                }
                operators.push(token);
            } else if (token.equals("(")) {
                operators.push(token);
            } else if (token.equals(")")) {
                while (!operators.isEmpty() && !operators.peek().equals("(")) {
                    output.add(operators.pop());
                }
                if (!operators.isEmpty() && operators.peek().equals("(")) {
                    operators.pop(); // Remove the '(' from stack
                }
            }
        }

        while (!operators.isEmpty()) {
            output.add(operators.pop());
        }

        return output;
    }

    // Evaluate  Reverse Polish Notation (RPN) expression
    private double evaluateRPN(List<String> rpn) {
        Stack<Double> stack = new Stack<>();

        for (String token : rpn) {
            if (isNumber(token)) {
                stack.push(Double.parseDouble(token));
            } else if (isOperator(token)) {
                double b = stack.pop();
                double a = stack.pop();
                switch (token) {
                    case "+":
                        stack.push(a + b);
                        break;
                    case "-":
                        stack.push(a - b);
                        break;
                    case "*":
                        stack.push(a * b);
                        break;
                    case "/":
                        if (b != 0) stack.push(a / b);
                        else throw new ArithmeticException("Division by zero");
                        break;
                }
            }
        }

        return stack.pop();
    }

    // Check if a string is a number (supports decimals)
    private boolean isNumber(String token) {
        return token.matches("-?\\d+(\\.\\d+)?");
    }

    // Check if a string is an operator
    private boolean isOperator(String token) {
        return "+-*/".contains(token);
    }

    // Get precedence of operators
    private int precedence(String operator) {
        switch (operator) {
            case "+":
            case "-":
                return 1;
            case "*":
            case "/":
                return 2;
            default:
                return 0;
        }
    }
}
