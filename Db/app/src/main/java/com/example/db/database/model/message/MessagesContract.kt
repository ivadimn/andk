package com.example.db.database.model.message

object MessagesContract {

    const val TABLE_NAME = "messages"

    object Columns {
        const val ID = "id"
        const val FROM_USER = "from_user_id"
        const val TO_USER = "to_user_id"
        const val BODY = "body"
        const val IS_IMPORTANT = "is_important"
        const val IS_DELIVERED = "is_delivered"
        const val CREATED_AT = "created_at"
    }
}