package com.example.noteapp.roompersistence

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.noteapp.model.Note

// u can create view with implementing views = arryaof(NotesDetail.class), version..
@Database(entities = arrayOf(Note::class), version = 1)
abstract class NoteDb : RoomDatabase() {

    // Kita membangun sebuah instansi ROOM DATABASE BUILDER

    abstract fun notesDAO(): DAO

    object DatabaseName {

        val DATABASE_NAME = "NoteDB"

        var instance: NoteDb?=null //Instansi

        fun getInstance(context: Context): NoteDb? {
            if(instance == null) {
                //Prepopulate Database
                instance = Room.databaseBuilder(context, NoteDb::class.java, DATABASE_NAME)
                    .build()
            }
            return instance
        }

        fun destroy(){
            instance = null
        }
    }
}

// NOTES :
// Abstract class akan selalu menjadi superclass / hirarki tertinggi dari subclass-subclass-nya.
// Abstract Class adalah sebuah class yang tidak bisa di-instansiasi
// (tidak bisa dibuat menjadi objek) dan berperan sebagai 'kerangka dasar' bagi class turunannya.

// Umumnya memiliki abstract method
// Sedangkan interface: - Adalah sebuah blok signature kumpulan method tanpa tubuh (konstan).


// Lateinit digunakan ketika kita ingin membuat non-null type
// tapi kita menginisiasikannya melalui dependency injection atau disebuah method
// In Java => private String x; (<- without initialiation)