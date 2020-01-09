package sn.ibrahim.mablocnote

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.widget.Toolbar

class DetailsNoteActivity : AppCompatActivity() {

    companion object{
        val CODE_REQUEST = 1
        val EXTRA_NOTE = "note"
        val EXTRA_INDEX_NOTE = "indexNote"

        val ACTION_SAVE_NOTE = "sn.ibrahim.mablocnote.actions.ACTION_ENREGISTRER"
        val ACTION_SUPPRIMER_NOTE = "sn.ibrahim.mablocnote.actions.ACTION_SUPPRIMER"
    }

    lateinit var note: Note
    // On ne peut pas définir noteIndex comme un lateInit parceque c'est un type primitif
    var indexNote: Int = -1

    lateinit var viewTitreNote: TextView
    lateinit var viewContenuNote: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details_note)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        note = intent.getParcelableExtra<Note>(EXTRA_NOTE)
        indexNote = intent.getIntExtra(EXTRA_INDEX_NOTE, -1)

        viewTitreNote = findViewById<TextView>(R.id.titre_note)
        viewContenuNote = findViewById<TextView>(R.id.contenue_note)

        viewTitreNote.text = note.titreNote
        viewContenuNote.text = note.contenueNote
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.activity_details_note, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_enregistrer -> {
                enregistrerNote()
                return true
            }
            R.id.action_supprimer -> {
                afficherDialogConfirmerSuppression()
                return true
            }
            else -> {
                return super.onOptionsItemSelected(item)
            }
        }
    }

    fun afficherDialogConfirmerSuppression(){
        val confirmerSuppressionNoteDialogFragment = ConfirmerSuppressionNoteDialogFragment(note.titreNote.toString())
        confirmerSuppressionNoteDialogFragment.listener = object : ConfirmerSuppressionNoteDialogFragment.ConfirmerSuppressionDialogListener{
            override fun onDialogClickPositive() {
                supprimerNote()
            }

            override fun onDialogClickNegative() {
            }
        }
        confirmerSuppressionNoteDialogFragment.show(supportFragmentManager, "Confirmer Suppression")
    }

    fun enregistrerNote(){
        note.titreNote = viewTitreNote.text.toString()
        note.contenueNote = viewContenuNote.text.toString()

        intent = Intent(ACTION_SAVE_NOTE)
        intent.putExtra(EXTRA_NOTE, note as Parcelable)
        intent.putExtra(EXTRA_INDEX_NOTE, indexNote)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    fun supprimerNote(){
        intent = Intent(ACTION_SUPPRIMER_NOTE)
        intent.putExtra(EXTRA_INDEX_NOTE, indexNote)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }
}
