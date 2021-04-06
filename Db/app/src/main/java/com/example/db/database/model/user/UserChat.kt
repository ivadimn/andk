package com.example.db.database.model.user

import androidx.room.Embedded
import androidx.room.Relation
import com.example.db.database.model.message.Message
import com.example.db.database.model.message.MessagesContract

data class UserChat(
     @Embedded
     val fromUser : User,
     @Relation(
             parentColumn = UsersContract.Columns.ID,
             entityColumn = MessagesContract.Columns.FROM_USER_ID
     )
     val chat : List<Message>
) {
}