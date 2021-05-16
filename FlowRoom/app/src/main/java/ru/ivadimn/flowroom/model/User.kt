package ru.ivadimn.flowroom.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import ru.ivadimn.flowroom.contracts.UsersContract
import ru.ivadimn.flowroom.converters.SexConverter

@Entity(tableName = UsersContract.TABLE_NAME)
@TypeConverters(SexConverter::class)
data class User(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = UsersContract.Columns.ID)
    val id : Long,
    @ColumnInfo(name = UsersContract.Columns.NAME)
    val name : String,
    @ColumnInfo(name = UsersContract.Columns.AGE)
    val age : Int,
    @ColumnInfo(name = UsersContract.Columns.SEX)
    val sex : Sex
) {
}