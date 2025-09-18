package com.mohd.dev.ui

import android.os.Bundle
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.saveBtn.setOnClickListener {
            notesViewModel.updateNote(this,
                Note(
                    AppUtils.getDeviceId(this),
                    binding.descEt.text.toString(),
                    binding.descEt.text.toString(),
                            AppUtils.currentTime()
                )
            )
        }
    }
}