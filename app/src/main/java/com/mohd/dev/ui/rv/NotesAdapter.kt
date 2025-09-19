import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.mohd.dev.R
import com.mohd.dev.databinding.RvNoteCardLayoutBinding
import com.mohd.dev.room.entities.Note
import com.mohd.dev.ui.UpdateNoteActivity
import okhttp3.internal.notify

class NotesAdapter(
    val context: Context,
    private val onLongClick: (Note, Int) -> Unit
) : RecyclerView.Adapter<NotesAdapter.ViewHolder>() {

    private var notesList = mutableListOf<Note>()

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(newList: List<Note>) {
        notesList.clear()
        notesList.addAll(newList)
        notifyDataSetChanged()
    }

    fun removeItem(position: Int) {
        if (position in notesList.indices) {
            notesList.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, notesList.size)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RvNoteCardLayoutBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val note = notesList[position]
        holder.bind(note, position, onLongClick)
    }

    override fun getItemCount(): Int {
        return notesList.size
    }

    class ViewHolder(private val binding: RvNoteCardLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(note: Note, position: Int, onLongClick: (Note, Int) -> Unit) {
            if (!note.isUploaded) {
                binding.mainCard.setBackgroundColor(ContextCompat.getColor(binding.root.context,android.R.color.darker_gray))
            } else {
                binding.mainCard.setBackgroundColor(ContextCompat.getColor(binding.root.context, R.color.white))
            }

            binding.titleTv.text = note.title
            binding.descTv.text = note.desc
            binding.timeTv.text = note.time

            binding.mainCard.setOnLongClickListener {
                onLongClick(note, position)
                true
            }

            binding.mainCard.setOnClickListener {
                val intent = Intent(binding.root.context, UpdateNoteActivity::class.java)
                intent.putExtra("id", note.id)
                binding.root.context.startActivity(intent)
            }
        }
    }
}

