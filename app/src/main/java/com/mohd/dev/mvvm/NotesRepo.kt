package com.mohd.dev.mvvm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mohd.dev.room.dao.NoteDao
import com.mohd.dev.room.entities.Note

class NotesRepo(private val noteDao: NoteDao) {

    suspend fun insert(note: Note) = noteDao.insert(note)
    suspend fun update(note: Note) = noteDao.update(note)
    suspend fun delete(note: Note) = noteDao.delete(note)
    suspend fun getSingleNote(userId: String) = noteDao.getSingleNote(userId)
    fun getAllNotes(): LiveData<List<Note>> = noteDao.getAllNotes()
    suspend fun getUnuploadedNotes(): List<Note> {
        return noteDao.getAllNotes().value?.filter { !it.isUploaded } ?: emptyList()
    }
}