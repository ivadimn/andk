package com.example.db.database.model

import android.provider.ContactsContract

object UsersContract {

    const val TABLE_NAME = "users"

    object Columns {
        const val ID = "id"
        const val NAME = "name"
        const val FAMILY = "family"
        const val PHONE = "phone"
        const val EMAIL = "email"
        const val PHOTO = "photo"
        const val CREATED_AT = "created_at"
        const val UPDATED_AT = "updated_at"
    }
}