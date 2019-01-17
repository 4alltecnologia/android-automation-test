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

class UsersViewModel(
        private val userRepository: UserRepository,
        private val commandProvider: CommandProvider
) : ViewModel() {

    val command: SingleLiveEvent<GenericCommand> = commandProvider.getCommand()
    val viewState: MutableLiveData<ViewState> = MutableLiveData()

    data class ViewState(
            val isLoadingUsers: Boolean = false
    )

    sealed class Command : GenericCommand() {
        object AddNewUser : Command()
        class ShowUsers(val users: List<User>) : Command()
    }

    init {

        viewState.setValue(ViewState())
    }

    fun addNewUser() {

        command.setValue(Command.AddNewUser)
    }

    fun loadUsers() {

        viewState.setValue(currentViewState().copy(isLoadingUsers = true))

        GlobalScope.launch {

            val users = withContext(Dispatchers.Default) {

                userRepository.getUsers()
            }

            viewState.postValue(currentViewState().copy(isLoadingUsers = false))

            command.postValue(Command.ShowUsers(users))
        }
    }

    private fun currentViewState(): ViewState = viewState.value!!
}