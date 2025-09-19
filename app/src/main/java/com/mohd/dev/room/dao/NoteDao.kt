package com.mohd.dev.room.dao

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.mohd.dev.room.entities.Note

@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: Note)

    @Update
    suspend fun update(note: Note)

    @Delete
    suspend fun delete(note: Note)

    @Query("SELECT * FROM notes WHERE userId = :userId")
    suspend fun getSingleNote(userId: String): Note?

    @Query("SELECT * FROM notes")
    fun getAllNotes(): LiveData<List<Note>>

    @Query("SELECT * FROM notes WHERE isUploaded = 0")
    fun getUnuploadedNotesLive(): LiveData<List<Note>>
}
