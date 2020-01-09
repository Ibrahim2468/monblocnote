package sn.ibrahim.mablocnote

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class ConfirmerSuppressionNoteDialogFragment(var titreNote: String = ""): DialogFragment() {

    interface ConfirmerSuppressionDialogListener{
        fun onDialogClickPositive()
        fun onDialogClickNegative()
    }

    var listener: ConfirmerSuppressionDialogListener? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val builder = AlertDialog.Builder(activity)

        builder.setMessage("Voulez-vous vraiment supprimer la note ${titreNote}")
            .setPositiveButton("Supprimer", DialogInterface.OnClickListener{
                dialog, id -> listener?.onDialogClickPositive()
            })
            .setNegativeButton("Annuler", DialogInterface.OnClickListener{
                dialog, id -> listener?.onDialogClickNegative()
            })

        return builder.create()
    }
}