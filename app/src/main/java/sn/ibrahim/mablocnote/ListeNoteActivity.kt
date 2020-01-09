package sn.ibrahim.mablocnote

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import sn.ibrahim.mablocnote.utils.chargerNotes
import sn.ibrahim.mablocnote.utils.persistNote


class ListeNoteActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var listeNote: MutableList<Note>
    lateinit var adapteurNote: AdapteurNote

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_liste_note)

        val toolbar: Toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        findViewById<FloatingActionButton>(R.id.creer_note_button).setOnClickListener(this)

        listeNote = chargerNotes(this)

        adapteurNote = AdapteurNote(listeNote, this)

        val recyclerView = findViewById<RecyclerView>(R.id.liste_note_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapteurNote
    }

    @SuppressLint("MissingSuperCall")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(resultCode != Activity.RESULT_OK || data == null){
            return
        }

        when(requestCode){
            DetailsNoteActivity.CODE_REQUEST -> resultatEditionNote(data)
        }
    }

    override fun onClick(view: View){
        if(view.tag != null){
            afficherDetailsNote(view.tag as Int)
        } else{
            when(view.id){
                R.id.creer_note_button -> creerNouvelleNote()
            }
        }
    }

    fun afficherDetailsNote(indexNote: Int){
        val note = if(indexNote < 0) Note() else listeNote[indexNote]

        val intent = Intent(this, DetailsNoteActivity::class.java)
        intent.putExtra(DetailsNoteActivity.EXTRA_NOTE, note as Parcelable)
        intent.putExtra(DetailsNoteActivity.EXTRA_INDEX_NOTE, indexNote)
        startActivityForResult(intent, DetailsNoteActivity.CODE_REQUEST)
    }

    fun resultatEditionNote(data: Intent){

        val indexNote = data.getIntExtra(DetailsNoteActivity.EXTRA_INDEX_NOTE, -1)

        when(data.action){
            DetailsNoteActivity.ACTION_SAVE_NOTE -> {
                val note = data.getParcelableExtra<Note>(DetailsNoteActivity.EXTRA_NOTE)
                enregistrerNote(note, indexNote)
            }

            DetailsNoteActivity.ACTION_SUPPRIMER_NOTE -> {
                supprimerNote(indexNote)
            }
        }

    }

    fun creerNouvelleNote(){

        afficherDetailsNote(-1)
    }

    fun enregistrerNote(note: Note?, indexNote: Int){

        if (note != null) {
            persistNote(this, note)
        }
        if(indexNote < 0){
            if (note != null) {
                listeNote.add(0, note)
            }
        } else {
            if (note != null) {
                listeNote[indexNote] = note
            }
        }
        adapteurNote.notifyDataSetChanged()
    }

    fun supprimerNote(indexNote: Int){
        if(indexNote == 0){
            return
        }
        val note = listeNote.removeAt(indexNote)
        sn.ibrahim.mablocnote.utils.supprimerNote(this, note)
        adapteurNote.notifyDataSetChanged()
    }


}
