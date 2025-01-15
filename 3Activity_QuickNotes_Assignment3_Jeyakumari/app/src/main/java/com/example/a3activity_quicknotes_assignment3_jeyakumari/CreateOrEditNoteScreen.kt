package com.example.a3activity_quicknotes_assignment3_jeyakumari

import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateOrEditNoteScreen(
    note: Note?,
    onSave: (Note) -> Unit,
    onDelete: ((Note) -> Unit)? = null, // Optional for delete, not needed in Create
    onCancel: () -> Unit
) {
    // State variables for input fields
    var title by remember { mutableStateOf(note?.title ?: "") }
    var content by remember { mutableStateOf(note?.content ?: "") }

    // State for showing Toast message
    val context = LocalContext.current
    val showToast = remember { mutableStateOf(false) }

    // To handle Toast message
    LaunchedEffect(showToast.value) {
        if (showToast.value) {
            Toast.makeText(context, "Please enter both title and content.", Toast.LENGTH_SHORT)
                .show()
            showToast.value = false
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(if (note == null) "Create Note" else "Edit Note") }
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(16.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Title") },
                    modifier = Modifier.fillMaxWidth()
                )

                TextField(
                    value = content,
                    onValueChange = { content = it },
                    label = { Text("Content") },
                    modifier = Modifier.fillMaxWidth()
                )

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(onClick = onCancel) {
                        Text("Cancel")
                    }

                    // Save Button Logic
                    Button(
                        onClick = {
                            // Check if both title and content are filled
                            if (title.isBlank() || content.isBlank()) {
                                showToast.value = true // Trigger the toast message
                            } else {
                                // Create or edit the note
                                val newNote = Note(
                                    id = note?.id ?: -1, // Use existing ID or -1 for new notes
                                    title = title,
                                    content = content
                                )
                                onSave(newNote)
                            }
                        }
                    ) {
                        Text("Save")
                    }
                }

                if (note != null && onDelete != null) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = { onDelete(note) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.error
                        ),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Delete", color = MaterialTheme.colorScheme.onError)
                    }
                }
            }
        }
    )
}