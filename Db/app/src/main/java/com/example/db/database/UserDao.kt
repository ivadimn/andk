package com.example.db.database

import androidx.room.*
import com.example.db.database.model.message.MessagesContract
import com.example.db.database.model.user.User
import com.example.db.database.model.user.UserChat
import com.example.db.database.model.user.UsersContract

@Dao
interface UserDao  {
    @Query("Select * from ${UsersContract.TABLE_NAME}")
    suspend fun getAllUsers() : List<User>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsers(users : List<User>)

    @Delete
    suspend fun removeUser(user : User)

    @Query("Delete from ${UsersContract.TABLE_NAME} Where ${UsersContract.Columns.ID} = :userId")
    suspend fun removeUserById(userId : Long)

    @Query("Select * from ${UsersContract.TABLE_NAME} Where ${UsersContract.Columns.ID} = :userId")
    suspend fun getUserById(userId: Long) : User?

    @Update
    suspend fun updateUser(user: User)

    @Query("Select * From ${UsersContract.TABLE_NAME} Where id = :userId" )
    suspend fun getUserChat(userId : Long) : UserChat



}