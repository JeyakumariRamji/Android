package com.example.a3activity_quicknotes_assignment3_jeyakumari

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent


class EditNoteActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val note = intent.getParcelableExtra<Note>("note")

        setContent {
            note?.let {
                CreateOrEditNoteScreen(
                    note = it,
                    onSave = { updatedNote ->
                        val resultIntent = Intent().apply {
                            putExtra("note", updatedNote)
                        }
                        setResult(RESULT_OK, resultIntent)
                        finish()
                    },
                    onDelete = { deletedNote ->
                        val resultIntent = Intent().apply {
                            putExtra("noteId", deletedNote.id)
                        }
                        setResult(RESULT_FIRST_USER, resultIntent)
                        finish()
                    },
                    onCancel = { finish() }
                )
            }
        }
    }
}