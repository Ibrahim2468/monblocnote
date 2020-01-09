package sn.ibrahim.mablocnote

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class AdapteurNote(val liste_note: List<Note>, val itemclickListener: View.OnClickListener): RecyclerView.Adapter<AdapteurNote.ViewHolder>(){

    class ViewHolder(viewItem: View): RecyclerView.ViewHolder(viewItem){
        val cardView = itemView.findViewById<CardView>(R.id.card_view)
        val titreNote = cardView.findViewById<TextView>(R.id.titre_note)
        val extraitNote = cardView.findViewById<TextView>(R.id.extrait_note)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false)

        return ViewHolder(itemView)

    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val note = liste_note[position]
        viewHolder.cardView.setOnClickListener(itemclickListener)
        viewHolder.cardView.tag = position
        viewHolder.titreNote.text = note.titreNote
        viewHolder.extraitNote.text = note.contenueNote
    }

    override fun getItemCount(): Int {
        return liste_note.size
    }
}