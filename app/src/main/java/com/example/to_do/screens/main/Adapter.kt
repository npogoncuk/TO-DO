package com.example.to_do.screens.main

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SortedList
import com.example.to_do.App
import com.example.to_do.R
import com.example.to_do.model.Note

class Adapter(private val liveDataToObserve: LiveData<Drawable>, private val lifecycleOwner: LifecycleOwner) : RecyclerView.Adapter<Adapter.NoteViewHolder>() {

    inner class NoteViewHolder(itemView: View, liveData: LiveData<Drawable>, lifecycle: LifecycleOwner)
        : RecyclerView.ViewHolder(itemView) {
        val row: LinearLayout = itemView.findViewById(R.id.linear_layout_row)
        private val textView = itemView.findViewById<TextView>(R.id.note_text)
        private val completed = itemView.findViewById<CheckBox>(R.id.completed)
        private val delete = itemView.findViewById<View>(R.id.delete)
        private lateinit var note: Note

        init {
            completed.setOnCheckedChangeListener { _, checked ->
                if (!silentUpdate) {
                    note.done = checked
                    App.instance.noteDao.update(note)
                }
                updateStrokeOut()
            }
            delete.setOnClickListener {
                App.instance.noteDao.delete(note)
            }
            itemView.setOnClickListener {
                (itemView.context as Activity).findNavController(R.id.nav_host_fragment_content_main)
                    .navigate(R.id.action_FirstFragment_to_SecondFragment, Bundle().apply { putParcelable("note", note) })
            }

            liveData.observe(lifecycle) {
                row.background = it
            }
        }
        private var silentUpdate = true

        fun bind(note: Note) {
            this.note = note
            textView.text = note.text
            updateStrokeOut()
            silentUpdate = true
            completed.isChecked = note.done
            silentUpdate = false
        }

        private fun updateStrokeOut() {
            if (note.done)
                textView.paintFlags = textView.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            else textView.paintFlags = textView.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
        }
    }

    private val sortedList: SortedList<Note> = SortedList(Note::class.java, object : SortedList.Callback<Note>() {
        override fun compare(o1: Note?, o2: Note?): Int {
            if (o1!!.done && !o2!!.done) return 1
            if (o2!!.done && !o1.done) return -1
            return (o2.timeStamp - o1.timeStamp).toInt()
        }

        override fun onInserted(position: Int, count: Int) {
            notifyItemRangeInserted(position, count)
        }

        override fun onRemoved(position: Int, count: Int) = notifyItemRangeRemoved(position, count)
        override fun onMoved(fromPosition: Int, toPosition: Int) = notifyItemMoved(fromPosition, toPosition)

        override fun onChanged(position: Int, count: Int) = notifyItemRangeChanged(position, count)

        override fun areContentsTheSame(oldItem: Note?, newItem: Note?): Boolean =
            oldItem == newItem

        override fun areItemsTheSame(item1: Note?, item2: Note?): Boolean = item1!!.uid == item2!!.uid

    })

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Adapter.NoteViewHolder {
        val holder = NoteViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_note_list, parent, false), liveDataToObserve, lifecycleOwner)
        return holder
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(sortedList[position])
    }

    override fun getItemCount(): Int = sortedList.size()

    fun setItems(items: List<Note>) {
        sortedList.replaceAll(items)
    }
}