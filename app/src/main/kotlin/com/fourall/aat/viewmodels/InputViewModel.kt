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

class InputViewModel(
    private val userRepository: UserRepository,
    commandProvider: CommandProvider
) : ViewModel() {

    val command: SingleLiveEvent<GenericCommand> = commandProvider.getCommand()

    sealed class Command : GenericCommand() {
        class ShowUserInfo(val user: User?) : Command()
        class UserSaved(val id: Long) : Command()
    }

    fun loadUserById(id: Long) {

        GlobalScope.launch {

            val savedUser = withContext(Dispatchers.Default) {

                userRepository.getUserById(id)
            }
            command.value = Command.ShowUserInfo(savedUser)
        }
    }

    fun saveUser(name: String, age: String) {

        GlobalScope.launch {

            val createdId = withContext(Dispatchers.Default) {
                userRepository.saveUser(name, age)
            }

            command.value = Command.UserSaved(createdId)
        }
    }
}