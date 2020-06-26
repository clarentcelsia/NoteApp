package com.example.noteapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.RIGHT
import androidx.recyclerview.widget.ItemTouchHelper.SimpleCallback
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.noteapp.adapter.NotesAdapter
import com.example.noteapp.model.Note
import com.example.noteapp.roompersistence.DAO
import com.example.noteapp.roompersistence.NoteDb
import com.example.noteapp.roompersistence.NotesRepos
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton

class Notes : AppCompatActivity(), NotesAdapter.OnNoteListener {

    //UI COMPONENTS
    private var mAdd: ExtendedFloatingActionButton? = null
    private var mRecycler: RecyclerView? = null
    private var mRepos: NotesRepos? = null
    private var mRecyclerAdapter: NotesAdapter? = null

    //VARS
    private var mNotes = ArrayList<Note>()
    private val NOTES_TAG = "notes"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes)

        mAdd = findViewById(R.id.add)
        mRecycler = findViewById(R.id.recycler)

        //INIT RECYCLER
        val itemDecor = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        mRecycler.apply {
            this?.setHasFixedSize(true)
            this?.layoutManager = LinearLayoutManager(this@Notes)
            this?.addItemDecoration(itemDecor)
        }

        //BRING REPOSITORY
        mRepos = NotesRepos(this)

        //Read Data
        readData()

        //Delete list
        val itemTouchHelperCallback: SimpleCallback =
            object : SimpleCallback(0, RIGHT) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    deleteNotes(mNotes[viewHolder.adapterPosition])
                    mRecyclerAdapter?.notifyDataSetChanged()
                }
            }

        val itemTouch = ItemTouchHelper(itemTouchHelperCallback)
        itemTouch.attachToRecyclerView(mRecycler)

    }

    fun readData() {
        //Melakukan observasi/pembacaan data repository

        val observer = Observer<List<Note>> {
            if(mNotes.size > 0) mNotes.clear()
            if (it != null) {
                mNotes.addAll(it)
                mRecyclerAdapter = NotesAdapter(mNotes, this)
                mRecycler?.adapter = mRecyclerAdapter
            }
        }

        mRepos?.readNote()?.observe(this, observer)


    }

    fun deleteNotes(note: Note) {
        mNotes.remove(note)
        mRecyclerAdapter?.notifyDataSetChanged()
        //delete repository
        mRepos?.deleteNote(note)
    }

    fun addNotes(view: View) {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
        startActivity(intent)
    }

    override fun onNoteClick(position: Int) {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra(NOTES_TAG, mNotes[position])
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }
}

/* Implements Object in SimpleCallBack

     (JAVA)
     Item touch helper = new Itemtouch(...){
          override void onChange(){..}
     }
*/
