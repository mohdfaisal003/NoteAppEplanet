package com.mohd.dev.ui.rv

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mohd.dev.databinding.RvNoteCardLayoutBinding
import com.mohd.dev.room.entities.Note
import com.mohd.dev.ui.UpdateNoteActivity

class NotesAdapter(val context: Context): RecyclerView.Adapter<NotesAdapter.ViewHolder>() {

    private lateinit var binding: RvNoteCardLayoutBinding
    private var notesList = mutableListOf<Note>()

    fun updateList(list: MutableList<Note>, position: Int) {
        this.notesList = list
        notifyItemChanged(position)
        notifyItemRangeChanged(position,notesList.size -1)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        binding = RvNoteCardLayoutBinding.inflate(LayoutInflater.from(context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        try {
            val note = notesList.get(position)
            holder.bind(note)
        } catch (exception: Exception) {
            exception.printStackTrace()
            Log.d( "onBindViewHolder: ",exception.message.toString())
        }

    }

    override fun getItemCount(): Int {
        return notesList.size
    }

    class ViewHolder(private val binding: RvNoteCardLayoutBinding): RecyclerView.ViewHolder(binding.root), View.OnLongClickListener, View.OnClickListener {
        fun bind(note: Note) {
            binding.titleTv.text = note.title
            binding.descTv.text = note.desc
            binding.timeTv.text = note.time

            binding.mainCard.setOnLongClickListener(this)
            binding.mainCard.setOnClickListener(this)
        }

        override fun onLongClick(v: View?): Boolean {
            /* TO DO */
            return false
        }

        override fun onClick(v: View?) {
            val intent = Intent(binding.root.context, UpdateNoteActivity::class.java)
            binding.root.context.startActivity(intent)
        }
    }
}