package com.example.db.database.model.user

import androidx.room.*
import com.example.db.database.converters.InstantConverter
import org.threeten.bp.Instant

@Entity(tableName = UsersContract.TABLE_NAME)
data class User(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = UsersContract.Columns.ID)
    val id : Long,
    @ColumnInfo(name = UsersContract.Columns.NAME)
    val name : String,
    @ColumnInfo(name = UsersContract.Columns.FAMILY)
    val family : String,
    @ColumnInfo(name = UsersContract.Columns.PHONE)
    val phone : String,
    @ColumnInfo(name = UsersContract.Columns.EMAIL)
    val email : String,
    @ColumnInfo(name = UsersContract.Columns.PHOTO)
    val photo : String,
    @ColumnInfo(name = UsersContract.Columns.CREATED_AT)
    val createdAt : Instant,
    @ColumnInfo(name = UsersContract.Columns.UPDATED_AT)
    val updatedAt : Instant
) {
}