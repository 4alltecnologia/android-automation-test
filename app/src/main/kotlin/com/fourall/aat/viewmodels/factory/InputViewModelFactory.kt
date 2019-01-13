package com.fourall.aat.viewmodels.factory

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.fourall.aat.contract.repositories.UserRepository
import com.fourall.aat.data.di.CommandProvider
import com.fourall.aat.viewmodels.InputViewModel

class InputViewModelFactory(
    private val userRepository: UserRepository,
    private val commandProvider: CommandProvider
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
            InputViewModel(userRepository, commandProvider) as T
}