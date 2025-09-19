package com.mohd.dev.ui

import NotesAdapter
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.mohd.dev.R
import com.mohd.dev.appUtils.AppUtils
import com.mohd.dev.base.BaseActivity
import com.mohd.dev.databinding.ActivityDashboardBinding
import com.mohd.dev.intertnetChecks.NetworkListener
import com.mohd.dev.intertnetChecks.NetworkReceiver
import com.mohd.dev.mvvm.NoteViewModel
import kotlin.getValue

class DashboardActivity : AppCompatActivity(), View.OnClickListener, NetworkListener {
    private lateinit var binding: ActivityDashboardBinding
    private lateinit var adapter: NotesAdapter
    private val notesViewModel: NoteViewModel by viewModels<NoteViewModel>()
    private val receiver = NetworkReceiver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.addNoteBtnIb.setOnClickListener(this)
        setRv()

        notesViewModel.getAll(this).observe(this, Observer {
            if (!it.isNullOrEmpty()) {
                adapter.updateList(it)
                Log.d("onCreate: ", it.toString())
            }
        })
        receiver.networkListener = this
    }

    private fun setRv() {
        adapter = NotesAdapter(this)
        { note, position ->
            AlertDialog.Builder(this)
                .setTitle("Delete Note")
                .setMessage("Are you sure you want to delete this note?")
                .setPositiveButton("Yes") { _, _ ->
                    adapter.removeItem(position)
                    notesViewModel.deleteNote(this, note)

                }
                .setNegativeButton("No", null)
                .show()
        }
        binding.recyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true)
        binding.recyclerView.adapter = adapter
    }

    override fun onNetworkChanged(isConnected: Boolean) {
        Log.d( "onNetworkChanged: ", isConnected.toString())
        if (isConnected) {
            notesViewModel.syncUnuploadedNotes(this)
            AppUtils.showMessage(this,"Syncing...")
        }
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            binding.addNoteBtnIb.id -> {
                val intent = Intent(this, AddNoteActivity::class.java)
                startActivity(intent)
            }
        }
    }
}