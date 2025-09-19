package com.mohd.dev.firestore

import com.google.firebase.firestore.FirebaseFirestore
import com.mohd.dev.room.entities.Note
import kotlinx.coroutines.tasks.await

class FirestoreHelper {

    private val firestore = FirebaseFirestore.getInstance()

    private fun userNotesCollection(deviceId: String) =
        firestore.collection("notes")
            .document(deviceId)
            .collection("user_notes")

    // Insert or update note
    suspend fun insertOrUpdateNote(note: Note): Boolean {
        return try {
            if (note.id == null) {
                userNotesCollection(note.userId)
                    .add(note)
                    .await()
            } else {
                userNotesCollection(note.userId)
                    .document(note.id.toString())
                    .set(note)
                    .await()
            }
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    suspend fun deleteNote(note: Note): Boolean {
        return try {
            userNotesCollection(note.userId)
                .document(note.id.toString())
                .delete()
                .await()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    suspend fun getAllNotes(deviceId: String): MutableList<Note> {
        return try {
            userNotesCollection(deviceId)
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