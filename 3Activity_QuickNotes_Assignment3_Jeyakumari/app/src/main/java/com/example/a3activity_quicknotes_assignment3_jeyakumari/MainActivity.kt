package com.example.a3activity_quicknotes_assignment3_jeyakumari

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.*
import com.example.a3activity_quicknotes_assignment3_jeyakumari.ui.theme._3Activity_QuickNotes_Assignment3_JeyakumariTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val notesState = mutableStateListOf<Note>()

        // Launcher for Create Note
        val createNoteLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == RESULT_OK) {
                    val note = result.data?.getParcelableExtra<Note>("note")
                    note?.let { notesState.add(it.copy(id = notesState.size + 1)) }
                }
            }

        // Launcher for Edit Note
        val editNoteLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == RESULT_OK) {
                    val note = result.data?.getParcelableExtra<Note>("note")
                    note?.let {
                        val index = notesState.indexOfFirst { n -> n.id == it.id }
                        if (index != -1) notesState[index] = it
                    }
                } else if (result.resultCode == RESULT_FIRST_USER) {
                    val noteId = result.data?.getIntExtra("noteId", -1)
                    notesState.removeIf { it.id == noteId }
                }
            }

        setContent {
            _3Activity_QuickNotes_Assignment3_JeyakumariTheme {
                HomeScreen(
                    notes = notesState,
                    onAddNote = {
                        val intent = Intent(this, CreateNoteActivity::class.java)
                        createNoteLauncher.launch(intent)
                    },
                    onViewNote = { note ->
                        val intent = Intent(this, EditNoteActivity::class.java)
                        intent.putExtra("note", note)
                        editNoteLauncher.launch(intent)
                    }
                )
            }
        }
    }
}