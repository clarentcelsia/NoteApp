package com.example.noteapp.roompersistence

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.noteapp.asyncTask.DeleteTask
import com.example.noteapp.asyncTask.InsertTask
import com.example.noteapp.asyncTask.UpdateTask
import com.example.noteapp.model.Note

class NotesRepos(context: Context) {

    //Tempat repository/penyimpanan suatu instansi

    private var mNoteDB: NoteDb

    init {
        mNoteDB = NoteDb.DatabaseName.getInstance(context)!!
    }

    fun insertNote(note: Note) {
        InsertTask(mNoteDB.notesDAO()).execute(note)
    }

    fun deleteNote(note: Note) {
        DeleteTask(mNoteDB.notesDAO()).execute(note)
    }

    fun updateNote(note: Note){
        UpdateTask(mNoteDB.notesDAO()).execute(note)
    }

    fun readNote() : LiveData<List<Note>>{
        return mNoteDB.notesDAO().getNotes()
    }




}