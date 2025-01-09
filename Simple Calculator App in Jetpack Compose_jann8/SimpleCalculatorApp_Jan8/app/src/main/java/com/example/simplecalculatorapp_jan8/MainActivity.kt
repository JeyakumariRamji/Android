package com.example.simplecalculatorapp_jan8

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.simplecalculatorapp_jan8.ui.theme.SimpleCalculatorApp_Jan8Theme
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.mutableStateOf
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.*
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material3.Button
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SimpleCalculatorApp_Jan8Theme {
                val history = remember { mutableStateListOf<String>() }
                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = "calculator") {
                    composable("calculator") {
                        CalculatorScreen(navController = navController, history = history)
                    }
                    composable("history") {
                        HistoryScreen(history = history, navController = navController)
                    }
                }
            }
        }
    }
}

@Composable
fun CalculatorScreen(navController: NavController, history: MutableList<String>) {
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

        // Numeric Keypad
        LazyColumn {
            items((0..9).chunked(3)) { row ->
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

        // Decimal Point Button (.)
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

        // Row for Clear, Delete Buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
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

        Spacer(modifier = Modifier.height(16.dp))
        // Navigate to History Page
        Button(onClick = { navController.navigate("history") }) {
            Text(text = "View History")
        }
    }
}

@Composable
fun HistoryScreen(history: List<String>, navController: NavController) {
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
        Button(onClick = { navController.popBackStack() }) {
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