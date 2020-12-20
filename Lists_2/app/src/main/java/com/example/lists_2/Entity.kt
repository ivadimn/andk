package com.example.lists_2

import android.os.Parcel
import android.os.Parcelable
import androidx.versionedparcelable.VersionedParcelize
import kotlinx.android.parcel.Parcelize

sealed class Entity(
    open val id : Long,
    open val name : String,
    open val imageLink : String,
    open val country : String,
)  : Parcelable {
    @Parcelize
    data class Actor (
        override val id: Long,
        override val name: String,
        override val imageLink: String,
        override val country : String,
        val birthday : String,
        val films : String
    ) : Entity(id, name, imageLink, country) {  }
    @Parcelize
    data class Car(
            override val id: Long,
            override val name: String,
            override val imageLink: String,
            override val country: String,
            val power: Int,
            val maxSpeed: Int
    ) : Entity(id, name, imageLink, country) { }

}