package ru.ivadimn.material.model

import android.content.Context
import android.provider.ContactsContract

enum class EmailTypes(
    private val typeValue: Int
) {
    TYPE_CUSTOM(0),
    TYPE_HOME(ContactsContract.CommonDataKinds.Email.TYPE_HOME),
    TYPE_WORK(ContactsContract.CommonDataKinds.Email.TYPE_WORK),
    TYPE_OTHER(ContactsContract.CommonDataKinds.Email.TYPE_OTHER),
    TYPE_MOBILE(ContactsContract.CommonDataKinds.Email.TYPE_MOBILE);


    fun getTypeLabel(context : Context) : String {
        return context.getString(ContactsContract.CommonDataKinds.Email.getTypeLabelResource(typeValue))
    }

    companion object {
        fun getType(typePhone : Int) : EmailTypes {
            return when(typePhone) {
                ContactsContract.CommonDataKinds.Email.TYPE_HOME -> TYPE_HOME
                ContactsContract.CommonDataKinds.Email.TYPE_WORK -> TYPE_WORK
                ContactsContract.CommonDataKinds.Email.TYPE_OTHER -> TYPE_OTHER
                ContactsContract.CommonDataKinds.Email.TYPE_MOBILE -> TYPE_MOBILE
                else -> TYPE_CUSTOM
            }
        }
    }

}