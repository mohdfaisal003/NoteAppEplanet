package com.mohd.dev.firestore

import com.google.firebase.firestore.FirebaseFirestore
import com.mohd.dev.room.entities.Note
import kotlinx.coroutines.tasks.await

class FirestoreHelper {

    private val firestore = FirebaseFirestore.getInstance()
    private val notesCollection = firestore.collection("notes")

    suspend fun insertNote(note: Note): String? {
        return try {
            val docRef = notesCollection.add(note).await()
            docRef.id
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    suspend fun updateNote(note: Note): Boolean {
        return try {
            note.userId?.let {
                notesCollection.document(it).set(note).await()
                true
            } ?: false
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    suspend fun deleteNote(note: Note): Boolean {
        return try {
            note.userId?.let {
                notesCollection.document(it).delete().await()
                true
            } ?: false
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    suspend fun getAllNotes(userId: String): MutableList<Note> {
        return try {
            notesCollection
                .whereEqualTo("userId", userId)
                .get()
                .await()
                .toObjects(Note::class.java)
                .toMutableList()
        } catch (e: Exception) {
            e.printStackTrace()
            mutableListOf()
        }
    }
}
