package com.example.a3activity_quicknotes_assignment3_jeyakumari

import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    notes: List<Note>,
    onAddNote: () -> Unit,
    onViewNote: (Note) -> Unit
) {
    Scaffold(
        topBar = { CenterAlignedTopAppBar(title = { Text("QuickNotes") }) },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddNote) {
                Text("+")
            }
        }
    ) { paddingValues ->
        LazyColumn(modifier = Modifier.padding(paddingValues)) {
            items(notes.size) { index ->
                val note = notes[index]
                Card(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth(),
                    onClick = { onViewNote(note) }
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = note.title, style = MaterialTheme.typography.titleMedium)
                        Text(
                            text = note.content.take(100) + if (note.content.length > 100) "..." else "",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }
        }
    }
}