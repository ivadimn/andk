package ru.ivadimn.di.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import org.threeten.bp.Instant
import ru.ivadimn.di.data.contracts.UserContract

@Entity(tableName = UserContract.TABLE_NAME,
    indices = [
        Index(UserContract.Columns.NAME, name = UserContract.Indexes.NAME_INDEX, unique = false),
        Index(UserContract.Columns.FAMILY, name = UserContract.Indexes.FAMILY_INDEX, unique = false)
    ]
)
data class User(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = UserContract.Columns.ID)
    val id : Long,
    @ColumnInfo(name = UserContract.Columns.NAME)
    val name : String,
    @ColumnInfo(name = UserContract.Columns.FAMILY)
    val family : String,
    @ColumnInfo(name = UserContract.Columns.PHONE)
    val phone: String,
    @ColumnInfo(name = UserContract.Columns.EMAIL)
    val email : String,
    @ColumnInfo(name = UserContract.Columns.PHOTO)
    var photo : String,
    @ColumnInfo(name = UserContract.Columns.CREATED_AT)
    val createdAt : Instant,
    @ColumnInfo(name = UserContract.Columns.UPDATED_AT)
    val updatedAt : Instant,
)
