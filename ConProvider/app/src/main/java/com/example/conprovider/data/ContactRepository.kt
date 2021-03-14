package com.example.conprovider.data

import android.content.ContentValues
import android.database.AbstractCursor
import android.database.Cursor
import android.provider.ContactsContract
import android.util.Log
import com.example.conprovider.App
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.RuntimeException
import java.util.regex.Pattern

class ContactRepository {

    private val phonePattern = Pattern.compile("^\\+?[0-9]{3}-?[0-9]{6,12}\$")

    suspend fun saveContact(name : String, phone : String) = withContext(Dispatchers.IO) {
        if (!phonePattern.matcher(phone).matches() || name.isBlank()) {
            throw IncorrectInformationException()
        }
        val rawId = saveRawContact()
        saveContactName(rawId, name)
        saveContactPhone(rawId, phone)
    }

    private fun saveRawContact() : Long {
        val uri = App.context.contentResolver.insert(
                ContactsContract.RawContacts.CONTENT_URI,
                ContentValues()
        )
        Log.d("ContactRepository", "Content uri $uri")
        return uri?.lastPathSegment?.toLong() ?: error("cannot save raw contact")
    }

    private fun saveContactName(id : Long, name: String) {
        val contentValues = ContentValues().apply {
            put(ContactsContract.Data.RAW_CONTACT_ID, id)
            put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
            put(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, name)
        }
        App.context.contentResolver.insert(
                ContactsContract.Data.CONTENT_URI, contentValues
        )
    }

    private fun saveContactPhone(id : Long, phone: String) {
        val contentValues = ContentValues().apply {
            put(ContactsContract.Data.RAW_CONTACT_ID, id)
            put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
            put(ContactsContract.CommonDataKinds.Phone.NUMBER, phone)
        }
        App.context.contentResolver.insert(
                ContactsContract.Data.CONTENT_URI, contentValues
        )
    }


    suspend fun getAllContacts() : List<Contact> {
        return withContext(Dispatchers.IO) {
            App.context.contentResolver.query(ContactsContract.Contacts.CONTENT_URI,
                    null,
                    null,
                    null,
                    null)
        }?.use {
            getContactsFromCursor(it)
        }.orEmpty()
    }

    private fun getContactsFromCursor(cursor : Cursor) : List<Contact> {
        if (!cursor.moveToFirst()) {
            return emptyList()
        }
        val list = mutableListOf<Contact>()
        val nameIndex = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)
        val idIndex = cursor.getColumnIndex(ContactsContract.Contacts._ID)
        do {
            val id = cursor.getLong(idIndex)
            val name = cursor.getString(nameIndex) ?: "Not name"
            list.add(Contact(id, name, getPhonesForContact(id)))
        } while (cursor.moveToNext())

        return list
    }

    private fun getPhonesForContact(id : Long) : List<String> {
        return App.context.contentResolver.query(
               ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
               null,
               "${ContactsContract.CommonDataKinds.Phone.CONTACT_ID} = ?",
               arrayOf(id.toString()),
               null
        )?.use {
            getPhonesFromCursor(it)
        }.orEmpty()
    }

    private fun getPhonesFromCursor(cursor : Cursor) : List<String> {
        if (!cursor.moveToFirst()) {
            return emptyList()
        }
        val list = mutableListOf<String>()
        do {
            val numberIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
            list.add(cursor.getString(numberIndex))
        } while (cursor.moveToNext())

        return list
    }



}