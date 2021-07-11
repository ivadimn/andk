package ru.ivadimn.MaterialDesign.model

import android.content.Context
import android.provider.ContactsContract

enum class PhoneTypes(val typeValue : Int) {

    TYPE_CUSTOM(0),
    TYPE_HOME(ContactsContract.CommonDataKinds.Phone.TYPE_HOME),
    TYPE_MOBILE(ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE),
    TYPE_WORK(ContactsContract.CommonDataKinds.Phone.TYPE_WORK),
    TYPE_FAX_WORK(ContactsContract.CommonDataKinds.Phone.TYPE_FAX_WORK),
    TYPE_FAX_HOME(ContactsContract.CommonDataKinds.Phone.TYPE_FAX_HOME),
    TYPE_PAGER(ContactsContract.CommonDataKinds.Phone.TYPE_PAGER),
    TYPE_OTHER(ContactsContract.CommonDataKinds.Phone.TYPE_OTHER),
    TYPE_CALLBACK(ContactsContract.CommonDataKinds.Phone.TYPE_CALLBACK),
    TYPE_CAR(ContactsContract.CommonDataKinds.Phone.TYPE_CAR),
    TYPE_COMPANY_MAIN(ContactsContract.CommonDataKinds.Phone.TYPE_COMPANY_MAIN),
    TYPE_ISDN(ContactsContract.CommonDataKinds.Phone.TYPE_ISDN),
    TYPE_MAIN(ContactsContract.CommonDataKinds.Phone.TYPE_MAIN),
    TYPE_OTHER_FAX(ContactsContract.CommonDataKinds.Phone.TYPE_OTHER_FAX),
    TYPE_RADIO(ContactsContract.CommonDataKinds.Phone.TYPE_RADIO),
    TYPE_TELEX(ContactsContract.CommonDataKinds.Phone.TYPE_TELEX),
    TYPE_TTY_TDD(ContactsContract.CommonDataKinds.Phone.TYPE_TTY_TDD),
    TYPE_WORK_MOBILE(ContactsContract.CommonDataKinds.Phone.TYPE_WORK_MOBILE),
    TYPE_WORK_PAGER(ContactsContract.CommonDataKinds.Phone.TYPE_WORK_PAGER),
    TYPE_ASSISTANT(ContactsContract.CommonDataKinds.Phone.TYPE_ASSISTANT),
    TYPE_MMS(ContactsContract.CommonDataKinds.Phone.TYPE_MMS);


    fun getTypeLabel(context : Context) : String {
        return context.getString(ContactsContract.CommonDataKinds.Phone.getTypeLabelResource(typeValue))
    }

    companion object {
        fun getType(typePhone : Int) : PhoneTypes {
            return when(typePhone) {
                ContactsContract.CommonDataKinds.Phone.TYPE_HOME -> TYPE_HOME
                ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE -> TYPE_MOBILE
                ContactsContract.CommonDataKinds.Phone.TYPE_WORK -> TYPE_WORK
                ContactsContract.CommonDataKinds.Phone.TYPE_FAX_WORK -> TYPE_FAX_WORK
                ContactsContract.CommonDataKinds.Phone.TYPE_FAX_HOME -> TYPE_FAX_HOME
                ContactsContract.CommonDataKinds.Phone.TYPE_PAGER -> TYPE_PAGER
                ContactsContract.CommonDataKinds.Phone.TYPE_OTHER -> TYPE_OTHER
                ContactsContract.CommonDataKinds.Phone.TYPE_CALLBACK -> TYPE_CALLBACK
                ContactsContract.CommonDataKinds.Phone.TYPE_CAR -> TYPE_CAR
                ContactsContract.CommonDataKinds.Phone.TYPE_COMPANY_MAIN -> TYPE_COMPANY_MAIN
                ContactsContract.CommonDataKinds.Phone.TYPE_ISDN -> TYPE_ISDN
                ContactsContract.CommonDataKinds.Phone.TYPE_MAIN -> TYPE_MAIN
                ContactsContract.CommonDataKinds.Phone.TYPE_OTHER_FAX -> TYPE_OTHER_FAX
                ContactsContract.CommonDataKinds.Phone.TYPE_RADIO -> TYPE_RADIO
                ContactsContract.CommonDataKinds.Phone.TYPE_TELEX -> TYPE_TELEX
                ContactsContract.CommonDataKinds.Phone.TYPE_TTY_TDD -> TYPE_TTY_TDD
                ContactsContract.CommonDataKinds.Phone.TYPE_WORK_MOBILE -> TYPE_WORK_MOBILE
                ContactsContract.CommonDataKinds.Phone.TYPE_WORK_PAGER -> TYPE_WORK_PAGER
                ContactsContract.CommonDataKinds.Phone.TYPE_ASSISTANT -> TYPE_ASSISTANT
                ContactsContract.CommonDataKinds.Phone.TYPE_MMS -> TYPE_MMS
                else -> TYPE_CUSTOM
            }
        }
    }
}