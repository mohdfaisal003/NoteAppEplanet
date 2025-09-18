package com.mohd.dev.mvvm

import com.mohd.dev.intertnetChecks.NetworkListener
import com.mohd.dev.room.dao.NoteDao
import com.mohd.dev.room.entities.Note

class NotesRepo(private val noteDao: NoteDao) {

    val allNotes: List<Note> = noteDao.getAllNotes()

    suspend fun insert(note: Note) {
        noteDao.insert(note)
    }

    suspend fun update(note: Note) {
        noteDao.update(note)
    }

    suspend fun delete(note: Note) {
        noteDao.delete(note)
    }
}