package com.fourall.aat.viewmodels

import android.arch.lifecycle.MutableLiveData
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
    val viewState: MutableLiveData<ViewState> = MutableLiveData()

    data class ViewState(
        val isSavingUser: Boolean = false
    )

    sealed class Command : GenericCommand() {
        class ShowUserInfo(val user: User?) : Command()
        class ShowSavedUserMessage(val id: Long) : Command()
    }

    init {

        viewState.setValue(ViewState())
    }

    fun loadUserById(id: Long) {

        GlobalScope.launch {

            val savedUser = withContext(Dispatchers.IO) {

                userRepository.getUserById(id)
            }

            command.postValue(Command.ShowUserInfo(savedUser))
        }
    }

    fun saveUser(name: String, age: String) {

        viewState.setValue(currentViewState().copy(isSavingUser = true))

        GlobalScope.launch {

            val createdId = withContext(Dispatchers.IO) {
                userRepository.saveUser(name, age)
            }

            viewState.postValue(currentViewState().copy(isSavingUser = false))

            command.postValue(Command.ShowSavedUserMessage(createdId))
        }
    }

    fun saveUserById(
        id: Long,
        name: String,
        age: String
    ) {

        viewState.setValue(currentViewState().copy(isSavingUser = true))

        GlobalScope.launch {

            val updatedId = withContext(Dispatchers.IO) {
                userRepository.saveUserById(id, name, age)
            }

            viewState.postValue(currentViewState().copy(isSavingUser = false))

            command.postValue(Command.ShowSavedUserMessage(updatedId))
        }
    }

    private fun currentViewState(): ViewState = viewState.value!!
}