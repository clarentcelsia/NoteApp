package com.example.noteapp.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbNote")
class Note() : Parcelable {

    @PrimaryKey(autoGenerate = true)
    var id: Int? = null

    @ColumnInfo(name = "Title")
    var title: String? = ""

    @ColumnInfo(name = "Content")
    var content: String? = ""

    @ColumnInfo(name = "TimeStamps")
    var timestamp: String? = ""

    //Bundle into parcel
    //Read parcel
    constructor(parcel: Parcel) : this() {
        id = parcel.readInt()
        title = parcel.readString()
        content = parcel.readString()
        timestamp = parcel.readString()
    }

    override fun writeToParcel(p0: Parcel?, p1: Int) {
        //Ambil dari constructor
        p0!!.writeInt(this.id!!)
        p0.writeString(this.title)
        p0.writeString(this.content)
        p0.writeString(this.timestamp)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<Note> {
        override fun createFromParcel(parcel: Parcel): Note {
            return Note(parcel)
        }

        override fun newArray(size: Int): Array<Note?> {
            return newArray(size)
        }
    }

}