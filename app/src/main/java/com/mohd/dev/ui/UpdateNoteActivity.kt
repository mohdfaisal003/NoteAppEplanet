package com.mohd.dev.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.mohd.dev.appUtils.AppUtils
import com.mohd.dev.base.BaseActivity
import com.mohd.dev.databinding.ActivityUpdateBinding
import com.mohd.dev.mvvm.NoteViewModel
import com.mohd.dev.room.entities.Note
import kotlin.getValue

class UpdateNoteActivity : BaseActivity() {

    private lateinit var binding: ActivityUpdateBinding
    private val notesViewModel: NoteViewModel by viewModels<NoteViewModel>()
    private var noteId: Int = 0
    private lateinit var currentNote: Note
    private var isNetworkAvailable = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        noteId = intent.getIntExtra("id", 0)
        Log.d( "onCreate: ", noteId.toString())

        notesViewModel.getAll(this).observe(this) { list ->
            currentNote = list.find { it.id == noteId } ?: return@observe
            binding.descEt.setText(currentNote.desc)
        }

        binding.saveBtn.setOnClickListener {
            saveAndExit()
        }

        binding.backBtn.setOnClickListener {
            saveAndExit()
        }
    }

    override fun onNetworkChanged(isConnected: Boolean) {
        Log.d( "onNetworkChanged: ", isConnected.toString())
        isNetworkAvailable = isConnected
    }

    fun saveAndExit() {
        val updatedNote = currentNote.copy(
            AppUtils.getDeviceId(this),
            binding.descEt.text.toString(),
            binding.descEt.text.toString(),
            AppUtils.currentTime(),
            isNetworkAvailable
        )
        notesViewModel.updateNote(this, updatedNote)
        finish()
    }
}
