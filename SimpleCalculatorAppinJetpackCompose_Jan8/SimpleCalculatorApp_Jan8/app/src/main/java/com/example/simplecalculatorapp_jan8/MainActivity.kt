package com.example.simplecalculatorapp_jan8

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.simplecalculatorapp_jan8.ui.theme.SimpleCalculatorApp_Jan8Theme
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SimpleCalculatorApp_Jan8Theme {
                val history = remember { mutableStateListOf<String>() }
                var currentScreen by remember { mutableStateOf("calculator") }

                // Main layout with screen-based state
                Column(modifier = Modifier.fillMaxSize()) {
                    when (currentScreen) {
                        "calculator" -> CalculatorScreen(
                            history = history,
                            onNavigateToHistory = { currentScreen = "history" }
                        )
                        "history" -> HistoryScreen(
                            history = history,
                            onNavigateBack = { currentScreen = "calculator" }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CalculatorScreen(history: MutableList<String>, onNavigateToHistory: () -> Unit) {
    var input by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(16.dp)
    ) {
        // Display Input and Result
        Text(
            text = "Input: $input",
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Result: $result",
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Numeric Keypad with "9", "Clear", and "Delete" in the same row
        LazyColumn {
            items((0..8).chunked(3)) { row ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    row.forEach { num ->
                        Button(
                            onClick = { input += num.toString() },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(text = num.toString())
                        }
                    }
                }
            }
        }

        // Row with "9", "Clear", and "Delete"
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = { input += "9" },
                modifier = Modifier.weight(1f)
            ) {
                Text(text = "9")
            }
            Button(
                onClick = { input = "" },
                modifier = Modifier.weight(1f)
            ) {
                Text(text = "Clear")
            }
            Button(
                onClick = {
                    input = input.dropLast(1)
                    if (input.isEmpty()) input = ""
                },
                modifier = Modifier.weight(1f)
            ) {
                Text(text = "Delete")
            }
        }

        // Row for Operators (+, -, *, /)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = { input += "+" },
                modifier = Modifier.weight(1f)
            ) {
                Text(text = "+")
            }
            Button(
                onClick = { input += "-" },
                modifier = Modifier.weight(1f)
            ) {
                Text(text = "-")
            }
            Button(
                onClick = { input += "*" },
                modifier = Modifier.weight(1f)
            ) {
                Text(text = "*")
            }
            Button(
                onClick = { input += "/" },
                modifier = Modifier.weight(1f)
            ) {
                Text(text = "/")
            }
        }

        // Decimal Point Button (.) and Equals (=)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = {
                    if (!input.contains(".")) {
                        input += "."
                    }
                },
                modifier = Modifier.weight(1f)
            ) {
                Text(text = ".")
            }
            Button(
                onClick = {
                    // Handle '=' button press
                    if (input.isNotEmpty()) {
                        result = try {
                            evaluateExpression(input).toString()
                        } catch (e: Exception) {
                            "Error"
                        }
                        history.add("Input: $input, Result: $result")
                    }
                },
                modifier = Modifier.weight(1f)
            ) {
                Text(text = "=")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Navigate to History Page
        Button(onClick = { onNavigateToHistory() }) {
            Text(text = "View History")
        }
    }
}

@Composable
fun HistoryScreen(history: List<String>, onNavigateBack: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(16.dp)
    ) {
        Text(
            text = "History",
            style = MaterialTheme.typography.headlineSmall
        )
        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn {
            items(history) { item ->
                Text(
                    text = item,
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(4.dp))
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        // Navigate back to Calculator
        Button(onClick = { onNavigateBack() }) {
            Text(text = "Back to Calculator")
        }
    }
}

// Function to evaluate the mathematical expression
fun evaluateExpression(expression: String): Double {
    val operators = arrayOf("+", "-", "*", "/")
    var result = expression

    for (operator in operators) {
        if (result.contains(operator)) {
            val parts = result.split(operator)
            if (parts.size == 2) {
                val num1 = parts[0].toDoubleOrNull() ?: 0.0
                val num2 = parts[1].toDoubleOrNull() ?: 0.0
                return when (operator) {
                    "+" -> num1 + num2
                    "-" -> num1 - num2
                    "*" -> num1 * num2
                    "/" -> if (num2 != 0.0) num1 / num2 else Double.NaN
                    else -> 0.0
                }
            }
        }
    }
    return 0.0
}