package com.fourall.aat.contract

import com.fourall.aat.models.User

interface UserDataContract {

    interface ResultCallback<in T> {

        fun onLoaded(result: T)

        fun onDataNotAvailable()
    }

    interface Local {

        fun getUser(): User

        fun saveUser(
            name: String,
            age: String
        )

        fun getUserById(id: Int): User
    }
}