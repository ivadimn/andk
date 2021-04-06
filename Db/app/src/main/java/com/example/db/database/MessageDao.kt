package com.example.db.database

import androidx.room.*
import com.example.db.database.model.message.Message
import com.example.db.database.model.message.MessagesContract
import com.example.db.database.model.user.UserChat
import com.example.db.database.model.user.UsersContract

@Dao
interface MessageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @Transaction
    suspend fun insertMessages(messages : List<Message>)


}