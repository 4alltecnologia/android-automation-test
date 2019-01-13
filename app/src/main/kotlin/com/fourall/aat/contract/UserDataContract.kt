package com.fourall.aat.contract

import com.fourall.aat.models.User

interface UserDataContract {

    interface Local {

        fun getUser(): User

        fun saveUser(
            name: String,
            age: String
        )
    }
}