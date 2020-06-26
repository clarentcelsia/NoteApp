package com.example.noteapp.roompersistence

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.noteapp.model.Note

//Data Access Object
//CRUD

@Dao
interface DAO {

    // Room generates an implementation
    // inserts all parameters into the database in a single transaction.

    @Insert
    // it can return a long, which is the new rowId for the inserted item.
    // If the parameter is an array or a collection, it should return long[] or List<Long>
    fun insertNotes(vararg notes: Note?): LongArray?

    @Query("SELECT * FROM tbNote")
    fun getNotes() : LiveData<List<Note>>

    @Delete
    // return an int value instead, indicating the number of rows updated in the database.
    fun deleteNotes(vararg notes: Note?): Int

    @Update
    fun updateNotes(vararg notes: Note?): Int
}

// vararg = variable number of arguments : arrayOf()