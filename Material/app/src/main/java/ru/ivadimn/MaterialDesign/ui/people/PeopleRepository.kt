package ru.ivadimn.MaterialDesign.ui.people

import android.database.Cursor
import android.net.Uri
import android.provider.ContactsContract
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.ivadimn.MaterialDesign.App
import ru.ivadimn.material.model.*
import ru.ivadimn.material.ui.MainFragment

class PeopleRepository  {

    suspend fun getContacts() : List<Contact> = withContext(Dispatchers.IO) {
        Log.d(MainFragment.TAG, "getContacts")
        ru.ivadimn.MaterialDesign.App.context.contentResolver.query(
            ContactsContract.Contacts.CONTENT_URI,
            null,
            null,
            null,
            null
        )?.use {
            val people = getRawContacts(it)
            people.forEach {
                getContactDetail(it)
            }
            people
        }.orEmpty()
    }

    private fun getRawContacts(cursor: Cursor) : List<Contact> {
        if (!cursor.moveToFirst())
            return emptyList()
        val list = mutableListOf<Contact>()
        val indexId = cursor.getColumnIndex(ContactsContract.Contacts._ID)
        val indexName = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)
        val indexPhotoUri = cursor.getColumnIndex(ContactsContract.Contacts.PHOTO_THUMBNAIL_URI)
        do {
            val id = cursor.getLong(indexId)
            val name = cursor.getString(indexName) ?: "No name"
            val p = cursor.getString(indexPhotoUri)
            val photoUri = if(p != null) Uri.parse(p) else null
            val uri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_URI, id.toString())
            list.add(Contact(id, uri.toString(), name, photoUri, ContactInfo(null)))
        } while (cursor.moveToNext())
        return list
    }

    suspend fun getContactDetail(contact: Contact) {
        withContext(Dispatchers.IO) {
            val contactInfoUri = Uri.withAppendedPath(
                Uri.parse(contact.uri),
                ContactsContract.Contacts.Entity.CONTENT_DIRECTORY
            )

            ru.ivadimn.MaterialDesign.App.context.contentResolver.query(
                contactInfoUri,
                null,
                null,
                null,
                null
            )?.use {
                contact.info = getDetail(it)
            }
        }
    }

    private fun getDetail(cursor: Cursor) : ContactInfo {
        if (!cursor.moveToFirst()) {
            return ContactInfo(null)
        }
        val mimetypeIndex = cursor.getColumnIndex(MIME_TYPE_INDEX)
        val phoneIndex = cursor.getColumnIndex(PHONE_INDEX)
        val phoneTypeIndex = cursor.getColumnIndex(PHONE_TYPE_INDEX)
        val emailIndex = cursor.getColumnIndex(EMAIL_INDEX)
        val emailTypeIndex = cursor.getColumnIndex(EMAIL_TYPE_INDEX)
        val photoIndex = cursor.getColumnIndex(PHOTO_INDEX)
        val phones = mutableListOf<ItemDetail>()
        val emails = mutableListOf<ItemDetail>()
        var photoUri : Uri? = null

        do {
            when(cursor.getString(mimetypeIndex)) {
                PHONE_TYPE -> {
                    val phone = cursor.getString(phoneIndex)
                    val type = PhoneTypes.getType(cursor.getInt(phoneTypeIndex))
                    phones.add(ItemDetail.Phone(phone, type))
                    Log.d("ContactDetail", "Phone = $phone - ${type.getTypeLabel(ru.ivadimn.MaterialDesign.App.context)}")
                }
                EMAIL_TYPE -> {
                    val email = cursor.getString(emailIndex)
                    val type = EmailTypes.getType(cursor.getInt(emailTypeIndex))
                    emails.add(ItemDetail.Email(email, type))
                    Log.d("ContactDetail", "Email = $email")
                }
                PHOTO_TYPE -> {
                    photoUri = Uri.parse(cursor.getString(photoIndex))
                    Log.d("ContactDetail", "Photo = $photoUri")
                }
            }

        } while (cursor.moveToNext())
        val items = mutableListOf<ItemDetail>()
        items.addAll(phones)
        items.addAll(emails)
        return ContactInfo(photoUri, items)
    }

    companion object {
        private const val MIME_TYPE_INDEX = ContactsContract.Data.MIMETYPE
        private const val PHONE_INDEX =  ContactsContract.CommonDataKinds.Phone.NUMBER
        private const val PHONE_TYPE_INDEX =  ContactsContract.CommonDataKinds.Phone.TYPE
        private const val EMAIL_TYPE_INDEX =  ContactsContract.CommonDataKinds.Email.TYPE
        private const val EMAIL_INDEX =  ContactsContract.CommonDataKinds.Email.ADDRESS
        private const val PHOTO_INDEX =  ContactsContract.CommonDataKinds.Photo.PHOTO_URI
        private const val PHONE_TYPE = ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE
        private const val EMAIL_TYPE = ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE
        private const val PHOTO_TYPE = ContactsContract.CommonDataKinds.Photo.CONTENT_ITEM_TYPE

    }

}