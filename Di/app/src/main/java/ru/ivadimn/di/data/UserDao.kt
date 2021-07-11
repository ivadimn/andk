package ru.ivadimn.di.data

import androidx.room.*
import ru.ivadimn.di.data.contracts.UserContract
import ru.ivadimn.di.data.entities.User

@Dao
interface UserDao {
    @Query("Select * From ${UserContract.TABLE_NAME}")
    suspend fun selectAll() : List<User>

    @Insert
    suspend fun insert(users : List<User>)

    @Delete
    suspend fun delete(user: User)

    @Query("Delete from ${UserContract.TABLE_NAME} Where ${UserContract.Columns.ID} = :userId")
    suspend fun deleteById(userId : Long)

    @Update
    suspend fun update(user : User)

    @Query("Select count(${UserContract.Columns.ID}) From ${UserContract.TABLE_NAME}")
    suspend fun getCountUsers() : Int?

    @Query("Select * From ${UserContract.TABLE_NAME} Where ${UserContract.Columns.ID} = :userId" )
    suspend fun selectUser(userId: Long) : User
}