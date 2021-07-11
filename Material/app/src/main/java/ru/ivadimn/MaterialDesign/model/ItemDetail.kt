package ru.ivadimn.MaterialDesign.model

import android.content.Context
import android.os.Parcelable

sealed class ItemDetail(
    open var value: String
) {

    data class Phone(
        override var value: String,
        var type: PhoneTypes,
    ) : ItemDetail(value) {
        override fun getTypeLabel(context: Context) : String {
            return type.getTypeLabel(context)
        }
    }


    data class Email(
        override var value: String,
        var type: EmailTypes,
    ) : ItemDetail(value) {
        override fun getTypeLabel(context: Context) : String {
            return type.getTypeLabel(context)
        }
    }

    abstract fun getTypeLabel(context: Context) : String
}
