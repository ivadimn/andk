package ru.ivadimn.di.data.contracts

object UserContract {
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
    object Indexes {
        const val NAME_INDEX = "${TABLE_NAME}_name_idx"
        const val FAMILY_INDEX = "${TABLE_NAME}_family_idx"
    }
}