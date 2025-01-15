package com.example.a3activity_quicknotes_assignment3_jeyakumari

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

class CreateNoteActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CreateOrEditNoteScreen(
                note = null,
                onSave = { note ->
                    val resultIntent = Intent().apply {
                        putExtra("note", note)
                    }
                    setResult(RESULT_OK, resultIntent)
                    finish()
                },
                onCancel = { finish() }
            )
        }
    }
}