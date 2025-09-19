package com.mohd.dev.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import com.mohd.dev.appUtils.AppUtils
import com.mohd.dev.base.BaseActivity
import com.mohd.dev.databinding.AddNoteActivityBinding
import com.mohd.dev.mvvm.NoteViewModel
import com.mohd.dev.room.entities.Note

class AddNoteActivity : BaseActivity() {
    private lateinit var binding: AddNoteActivityBinding
    private val notesViewModel: NoteViewModel by viewModels<NoteViewModel>()
    private var isNetworkAvailable = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = AddNoteActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.saveBtn.setOnClickListener(this)
        binding.backBtn.setOnClickListener(this)
    }

    override fun onNetworkChanged(isConnected: Boolean) {
        Log.d( "onNetworkChanged: ", isConnected.toString())
        isNetworkAvailable = isConnected
    }

    override fun onClick(view: View?) {
        when(view?.id) {
            binding.backBtn.id -> {
                finish()
            }
            binding.saveBtn.id -> {
                notesViewModel.insertNote(this,
                    Note(
                        AppUtils.getDeviceId(this),
                        binding.descEt.text.toString(),
                        binding.descEt.text.toString(),
                        AppUtils.currentTime(),
                        isNetworkAvailable
                    )
                )
                finish()
            }
        }
    }
}