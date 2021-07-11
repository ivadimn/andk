package ru.ivadimn.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.ivadimn.di.ui.user.UserDetailViewModel
import ru.ivadimn.di.ui.user.UserViewModel

class ViewModelFactory
    : ViewModelProvider.Factory  {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when(modelClass) {
            UserViewModel::class.java -> DiContainer.getUserViewModel() as T
            UserDetailViewModel::class.java -> DiContainer.getUserDetailViewModel() as T
            else -> error("Can not create view model ")
        }
    }

}