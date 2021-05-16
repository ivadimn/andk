package ru.ivadimn.flowroom.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import ru.ivadimn.flowroom.contracts.UsersContract
import ru.ivadimn.flowroom.model.User

@Dao
interface UserDao {

    @Insert
    suspend fun insert(users : List<User>)

    @Query("Select * From ${UsersContract.TABLE_NAME}")
    suspend fun selectAll() : List<User>

    @Query("Select * From ${UsersContract.TABLE_NAME}")
    abstract fun selectAllFlow() : Flow<List<User>>

    fun selectAllFlowDistinctUntilChanged() =
        selectAllFlow().distinctUntilChanged()
}