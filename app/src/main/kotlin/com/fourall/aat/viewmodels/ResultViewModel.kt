package com.fourall.aat.viewmodels

import android.arch.lifecycle.ViewModel
import com.fourall.aat.contract.repositories.UserRepository
import com.fourall.aat.data.di.CommandProvider
import com.fourall.aat.models.GenericCommand
import com.fourall.aat.models.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ResultViewModel(
    private val userRepository: UserRepository,
    private val commandProvider: CommandProvider
) : ViewModel() {

    val command: SingleLiveEvent<GenericCommand> = commandProvider.getCommand()

    sealed class Command : GenericCommand() {
        class ShowUser(val user: User?) : Command()
    }

    fun loadUser(id: Long) {

        GlobalScope.launch {

            val user = withContext(Dispatchers.IO) {
                userRepository.getUserById(id)
            }

            command.postValue(Command.ShowUser(user))
        }
    }
}