package com.example.noteapp.asyncTask

import android.os.AsyncTask
import com.example.noteapp.model.Note
import com.example.noteapp.roompersistence.DAO

class InsertTask(noteDAO: DAO): AsyncTask<Note, Void, Void>() {

    var mNoteDAO : DAO = noteDAO

    override fun doInBackground(vararg notes: Note?): Void? {
        mNoteDAO.insertNotes(*notes)
        return null
    }


}