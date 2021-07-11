package ru.ivadimn.di

import ru.ivadimn.di.data.database.Db
import ru.ivadimn.di.interfaces.UserDetailRepository
import ru.ivadimn.di.interfaces.UserRepository
import ru.ivadimn.di.ui.user.UserDetailRepositoryImpl
import ru.ivadimn.di.ui.user.UserDetailViewModel
import ru.ivadimn.di.ui.user.UserRepositoryImpl
import ru.ivadimn.di.ui.user.UserViewModel

object DiContainer {

    private fun getUserRepository() : UserRepository  {
        return UserRepositoryImpl(Db.instance.userDao())
    }

    private fun getUserDetailRepository() : UserDetailRepository  {
        return UserDetailRepositoryImpl(Db.instance.userDao())
    }

    fun getUserViewModel() : UserViewModel {
        return UserViewModel(getUserRepository())
    }

    fun getUserDetailViewModel() : UserDetailViewModel {
        return UserDetailViewModel(getUserDetailRepository())
    }
}