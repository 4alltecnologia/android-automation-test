package com.fourall.aat.viewmodels

import android.arch.lifecycle.ViewModel
import com.fourall.aat.contract.repositories.UserRepository
import com.fourall.aat.data.di.CommandProvider
import com.fourall.aat.models.GenericCommand
import com.fourall.aat.models.User

class InputViewModel(
    private val userRepository: UserRepository,
    private val commandProvider: CommandProvider
) : ViewModel() {

    val command: SingleLiveEvent<GenericCommand> = commandProvider.getCommand()

    sealed class Command : GenericCommand() {
        class ShowUserInfo(val user: User) : Command()
    }

    fun loadUser() {

        val savedUser = userRepository.getUser()

        command.setValue(Command.ShowUserInfo(savedUser))
    }
}