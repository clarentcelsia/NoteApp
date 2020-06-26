package com.example.noteapp.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.noteapp.model.Note
import com.example.noteapp.R
import com.example.noteapp.Utility
import java.lang.NullPointerException

//EXTENDS -> CLASS()
class NotesAdapter(notesList: List<Note>, onNoteListener: OnNoteListener) :
    RecyclerView.Adapter<NotesAdapter.notesViewHolder>() {

    private val TAG = "VIEW_BINDING_NULL"
    private var mNotesList = notesList
    private var mOnNoteListener = onNoteListener

    class notesViewHolder(itemView: View, onNoteListener: OnNoteListener) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        private val notesTitle = itemView.findViewById<TextView>(R.id.tvTitle)
        private val notesSubTitle = itemView.findViewById<TextView>(R.id.tvSubTitle)
        private val notesTimes = itemView.findViewById<TextView>(R.id.tvTimes)
        private val nOnNoteListener: OnNoteListener = onNoteListener

        fun binding(mNote: Note) {

                notesTitle.text = mNote.title
                notesSubTitle.text = mNote.content

                // dd/MM/yyyy
                val day = mNote.timestamp?.substring(0, 2)
                var month = mNote.timestamp?.substring(3, 5)
                month = month?.let { Utility.getMonth(it) }
                val year = mNote.timestamp?.substring(6)

                val timestamps = "$day $month $year"
                notesTimes.text = timestamps

        }

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            nOnNoteListener.onNoteClick(adapterPosition)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): notesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.notes_item, parent, false)
        return notesViewHolder(view, mOnNoteListener)
    }

    override fun getItemCount(): Int = mNotesList.size

    override fun onBindViewHolder(holder: notesViewHolder, position: Int) {

        try {

            val notes = mNotesList[position]
            holder.binding(notes)

        } catch (e: NullPointerException) {
            Log.e(TAG, "View Binding is NULL")
        }
    }

    interface OnNoteListener {
        fun onNoteClick(position: Int)
    }
}