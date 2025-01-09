package com.example.simplecalculatorappinjetpackcompose_jan8

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.statusBarsPadding
import android.os.Bundle
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.mutableStateOf
import com.example.simplecalculatorappinjetpackcompose_jan8.ui.theme.SimpleCalculatorAppinJetpackCompose_Jan8Theme
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SimpleCalculatorAppinJetpackCompose_Jan8Theme {
                var num1 by remember { mutableStateOf("0") }
                var num2 by remember { mutableStateOf("0") }
                var result by remember { mutableStateOf("") }
                val history = remember { mutableStateListOf<String>() } // List to hold history

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .statusBarsPadding()
                        .padding(16.dp)
                ) {
                    // Input fields
                    TextField(
                        value = num1,
                        onValueChange = { num1 = it },
                        label = { Text("Number 1") }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    TextField(
                        value = num2,
                        onValueChange = { num2 = it },
                        label = { Text("Number 2") }
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    // Display the result
                    Text(
                        text = "Result: $result",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    // Row for buttons
                    Row {
                        Button(onClick = {
                            val calculation = (num1.toDoubleOrNull() ?: 0.0) + (num2.toDoubleOrNull() ?: 0.0)
                            result = calculation.toString()
                            history.add("Add: $num1 + $num2 = $result")
                        }) {
                            Text(text = "Add")
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Button(onClick = {
                            val calculation = (num1.toDoubleOrNull() ?: 0.0) - (num2.toDoubleOrNull() ?: 0.0)
                            result = calculation.toString()
                            history.add("Sub: $num1 - $num2 = $result")
                        }) {
                            Text(text = "Sub")
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Button(onClick = {
                            val calculation = (num1.toDoubleOrNull() ?: 0.0) * (num2.toDoubleOrNull() ?: 0.0)
                            result = calculation.toString()
                            history.add("Mul: $num1 * $num2 = $result")
                        }) {
                            Text(text = "Mul")
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Button(onClick = {
                            val divisor = num2.toDoubleOrNull() ?: 1.0
                            if (divisor != 0.0) {
                                val calculation = (num1.toDoubleOrNull() ?: 0.0) / divisor
                                result = calculation.toString()
                                history.add("Div: $num1 / $num2 = $result")
                            } else {
                                result = "Cannot divide by zero"
                                history.add("Div: $num1 / $num2 = Error (divide by zero)")
                            }
                        }) {
                            Text(text = "Div")
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))

                    // History Header
                    Text(
                        text = "History",
                        style = MaterialTheme.typography.headlineSmall
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    // Display history using LazyColumn
                    LazyColumn {
                        items(history) { item ->
                            Text(
                                text = item,
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                        }
                    }
                }
            }
        }
    }
}

