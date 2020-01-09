package sn.ibrahim.mablocnote.utils

import android.content.Context
import android.text.TextUtils
import android.util.Log
import sn.ibrahim.mablocnote.Note
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.util.*

private val TAG = "storage"

fun persistNote(context: Context, note: Note) : Boolean {
    var enregistrer = true

    if (TextUtils.isEmpty(note.nomFichier)) {
        note.nomFichier = UUID.randomUUID().toString() + ".note"
    }
    Log.i(TAG, "Enregistrement de la note $note")
    val fileOutput = context.openFileOutput(note.nomFichier, Context.MODE_PRIVATE)
    val outputStream = ObjectOutputStream(fileOutput)
    outputStream.writeObject(note)

    return enregistrer
}

fun chargerNotes(context: Context) : MutableList<Note> {
    val notes = mutableListOf<Note>()
    Log.i(TAG, "Chargement des notes...")

    val dossierNotes = context.filesDir
    for(filename in dossierNotes.list()) {
        val note = chargerNote(context, filename)
        Log.i(TAG, "La note chargée est : $note")
        notes.add(note)
    }
    return notes
}

fun supprimerNote(context: Context, note: Note): Boolean {
    return context.deleteFile(note.nomFichier)
}

private fun chargerNote(context: Context, filename: String) : Note {
    val fileInput = context.openFileInput(filename)
    val inputStream = ObjectInputStream(fileInput)
    val note = inputStream.readObject() as Note
    return note
}

