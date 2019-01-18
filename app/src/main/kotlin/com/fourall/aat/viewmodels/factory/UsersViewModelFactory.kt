package com.fourall.aat.viewmodels.factory

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.fourall.aat.contract.repositories.UserRepository
import com.fourall.aat.data.di.CommandProvider
import com.fourall.aat.viewmodels.UsersViewModel

class UsersViewModelFactory(
    private val userRepository: UserRepository,
    private val commandProvider: CommandProvider
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
            UsersViewModel(userRepository, commandProvider) as T
}