package com.example.a3activity_quicknotes_assignment3_jeyakumari

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Note(
    val id: Int,
    var title: String,
    var content: String
) : Parcelable