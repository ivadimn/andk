package com.example.db.database

import androidx.room.*
import com.example.db.database.model.User
import com.example.db.database.model.UsersContract

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



}