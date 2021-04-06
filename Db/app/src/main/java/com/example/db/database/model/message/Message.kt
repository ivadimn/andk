package com.example.db.database.model.message

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.db.database.model.user.User
import com.example.db.database.model.user.UsersContract
import org.threeten.bp.Instant

@Entity(
        tableName = MessagesContract.TABLE_NAME,
        foreignKeys = [
                ForeignKey(
                        entity = User::class,
                        parentColumns = [UsersContract.Columns.ID],
                        childColumns = [MessagesContract.Columns.FROM_USER_ID]
                ),
                ForeignKey(
                        entity = User::class,
                        parentColumns = [UsersContract.Columns.ID],
                        childColumns = [MessagesContract.Columns.TO_USER_ID]
                )
        ]
)
data class Message(
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = MessagesContract.Columns.ID)
        val id : Long,
        @ColumnInfo(name = MessagesContract.Columns.FROM_USER_ID)
        val fromUserId : Long,
        @ColumnInfo(name = MessagesContract.Columns.TO_USER_ID)
        val toUserId : Long,
        @ColumnInfo(name = MessagesContract.Columns.BODY)
        val body : String,
        @ColumnInfo(name = MessagesContract.Columns.IS_IMPORTANT)
        val isImportant : Boolean,
        @ColumnInfo(name = MessagesContract.Columns.IS_DELIVERED)
        val isDelivered : Boolean,
        @ColumnInfo(name = MessagesContract.Columns.CREATED_AT)
        val createdAt : Instant
) {
}