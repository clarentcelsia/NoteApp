package com.example.noteapp

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.noteapp.model.Note
import com.example.noteapp.roompersistence.NotesRepos

//EXTENDS : CLASS(); IMPLEMENTS : INTERFACE
class MainActivity : AppCompatActivity(),
    View.OnTouchListener,
    View.OnClickListener,
    GestureDetector.OnGestureListener,
    GestureDetector.OnDoubleTapListener,
    TextWatcher {

    //UI COMPO'S
    private var mBack: ImageButton? = null
    private var mDone: ImageButton? = null
    private var mNotesEditText: NotesEditText? = null
    private var mTVTitle: TextView? = null
    private var mTitle: EditText? = null

    //repository & implementation
    private var mNotesRepository: NotesRepos? = null
    private var mNoteModels_Init: Note = Note()
    private var mNoteModels_Final: Note = Note()
    private var mGestureDetector: GestureDetector? = null

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.elevation = 4f

        mNotesRepository = NotesRepos(this)
        mGestureDetector = GestureDetector(this, this)

        mTVTitle = findViewById(R.id.tvtitle)
        this.mTVTitle?.setOnClickListener(this)
        mTitle = findViewById(R.id.title)
        this.mTitle?.addTextChangedListener(this)
        mBack = findViewById(R.id.back)
        this.mBack?.setOnClickListener(this)
        mDone = findViewById(R.id.done)
        this.mDone?.setOnClickListener(this)
        mNotesEditText = findViewById(R.id.notes)
        this.mNotesEditText?.setOnTouchListener(this)

        if (getIncomingIntent()) {
            isNew = false
            mTVTitle?.text = mNoteModels_Init.title
            mTitle?.setText(mNoteModels_Init.title)
            mNotesEditText?.setText(mNoteModels_Init.content)
        } else {
            setNewNotes()
        }
    }

    private fun setNewNotes() {
        isNew
        mTVTitle?.text = "Notes"
        mTitle?.setText("Notes")
        mNoteModels_Init.title = "Notes"
        mNoteModels_Init.content = ""
        mNoteModels_Init.timestamp = ""
    }

    private fun getIncomingIntent(): Boolean {

        //class Note mengimplementasi Parcelable
        //get several intents in the same class in one by using getParcelableExtra()
        return if (intent.hasExtra("notes")) {
            mNoteModels_Init = intent.getParcelableExtra("notes")
            //parsing..
            //get data from init
            mNoteModels_Final = Note()
            mNoteModels_Final.id = mNoteModels_Init.id
            mNoteModels_Final.title = mNoteModels_Init.title
            mNoteModels_Final.content = mNoteModels_Init.content
            mNoteModels_Final.timestamp = mNoteModels_Init.timestamp

            EDIT_MODE = 1
            true
        } else
            false
    }

    private fun saveChanges() {
        disableEditMode()
    }

    private fun saveNotes(note: Note){
        mNotesRepository?.insertNote(note)
    }
    private fun updateNotes(note: Note) {
        note.let {
            mNotesRepository?.updateNote(it)
        }
    }

    private fun enableEditMode() {
        Log.d(MODE, "enableEditMode: ON")
        mDone?.visibility = View.VISIBLE
        mBack?.visibility = View.GONE
        mTVTitle?.visibility = View.GONE
        mTitle?.visibility = View.VISIBLE

        EDIT_MODE = 1
        activation()
    }

    private fun disableEditMode() {
        Log.d(MODE, "disableEditMode: ON")
        mTVTitle?.visibility = View.VISIBLE
        mTitle?.visibility = View.GONE
        mDone?.visibility = View.GONE
        mBack?.visibility = View.VISIBLE

        EDIT_MODE = 0
        deactivation()

        //saving..
        val newNote = mNotesEditText?.text.toString()
        newNote.replace("\n", "")
        newNote.replace(" ", "")
        if (newNote.isNotEmpty()) {
            mNoteModels_Final.title = mTitle?.text.toString()
            mNoteModels_Final.content = mNotesEditText?.text.toString()
            val timestamp = Utility.currentTimeStamps()
            mNoteModels_Final.timestamp = timestamp

            Log.d(MODE, "disableEditMode: initial: $mNoteModels_Init")
            Log.d(MODE, "disableEditMode: final: $mNoteModels_Final")

            if (!isNew and (!mNoteModels_Final.title.equals(mNoteModels_Init.title) or
                !mNoteModels_Final.content.equals(mNoteModels_Init.content))
            ) {
                Log.d(MODE, "updateNote: called")
                updateNotes(mNoteModels_Final)
            }else{
                saveNotes(mNoteModels_Final)
            }
        }
    }

    private fun activation() {
        // Interface for converting text key events into edit operations on an Editable class -> KEY LISTENER
        mNotesEditText?.keyListener = EditText(this).keyListener
        mNotesEditText?.isFocusable
        mNotesEditText?.isFocusableInTouchMode
        mNotesEditText?.isCursorVisible
        mNotesEditText?.requestFocus()

    }

    private fun deactivation() {
        mNotesEditText?.keyListener = null
        mNotesEditText?.isFocusable = false
        mNotesEditText?.isFocusableInTouchMode = false
        mNotesEditText?.isCursorVisible = false
        mNotesEditText?.clearFocus()
    }

    override fun onTouch(p0: View?, motionEvent: MotionEvent?): Boolean {
        return mGestureDetector!!.onTouchEvent(motionEvent)
    }

    override fun onShowPress(p0: MotionEvent?) {
        return
    }

    override fun onSingleTapUp(p0: MotionEvent?): Boolean {
        return false
    }

    override fun onDown(p0: MotionEvent?): Boolean {
        return false
    }

    override fun onFling(p0: MotionEvent?, p1: MotionEvent?, p2: Float, p3: Float): Boolean {
        return false
    }

    override fun onScroll(p0: MotionEvent?, p1: MotionEvent?, p2: Float, p3: Float): Boolean {
        return false
    }

    override fun onLongPress(p0: MotionEvent?) {
        return
    }

    override fun onDoubleTap(p0: MotionEvent?): Boolean {
        Log.d(MODE, "onDoubleTap: double tapped")
        enableEditMode()
        return false
    }

    override fun onDoubleTapEvent(p0: MotionEvent?): Boolean {
        return false
    }

    override fun onSingleTapConfirmed(p0: MotionEvent?): Boolean {
        enableEditMode()
        return true
    }

    override fun onClick(p0: View) {
        when (p0.id) {
            R.id.back -> {
                onBackPressed()
            }
            R.id.done -> {
                if (isNew) disableEditMode()
                else saveChanges()
            }

            R.id.tvtitle -> {
                enableEditMode()
                mTitle?.requestFocus()
                mTitle?.setSelection(mTitle!!.length())
            }
        }
    }

    override fun afterTextChanged(p0: Editable?) {
        return
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        return
    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        mTVTitle?.text = p0.toString()
    }

    companion object {
        //vars
        private var EDIT_MODE: Int = 0
        private const val MODE: String = "MODE"
        private var isNew: Boolean = true
    }

}

