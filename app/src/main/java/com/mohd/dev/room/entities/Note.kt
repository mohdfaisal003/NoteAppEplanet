package com.mohd.dev.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class Note(
    val userId: String,
    val title: String,
    val desc: String,
    val time: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
) {
    constructor() : this("", "", "", "", 0)
}