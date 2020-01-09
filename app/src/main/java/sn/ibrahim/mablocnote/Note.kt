package sn.ibrahim.mablocnote

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

data class Note(
    var titreNote: String? = "",
    var contenueNote: String? = "",
    var nomFichier: String? = ""): Parcelable, Serializable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(titreNote)
        parcel.writeString(contenueNote)
        parcel.writeString(nomFichier)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Note> {
        // Identifiant unique permettant à la JVM de de reconnaitre les version de la classe note
        private val serialVersionUID: Long = 14141414141414
        override fun createFromParcel(parcel: Parcel): Note {
            return Note(parcel)
        }

        override fun newArray(size: Int): Array<Note?> {
            return arrayOfNulls(size)
        }
    }
}