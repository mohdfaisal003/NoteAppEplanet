package com.mohd.dev.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.mohd.dev.R
import com.mohd.dev.base.BaseActivity
import com.mohd.dev.databinding.ActivityDashboardBinding
import com.mohd.dev.mvvm.NoteViewModel
import com.mohd.dev.room.entities.Note
import com.mohd.dev.ui.rv.NotesAdapter
import kotlinx.coroutines.launch
import kotlin.getValue

class DashboardActivity : BaseActivity() {
    private lateinit var binding: ActivityDashboardBinding
    private lateinit var adapter: NotesAdapter

    private val notesViewModel: NoteViewModel by viewModels<NoteViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = NotesAdapter(this)
        binding.addNoteBtnIb.setOnClickListener(this)
        setRv()
    }

    private fun setRv() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
    }

    override fun onClick(view: View?) {
        when(view?.id) {
            binding.addNoteBtnIb.id -> {
                val intent = Intent(this, AddNoteActivity::class.java)
                startActivity(intent)
            }
        }
    }
}