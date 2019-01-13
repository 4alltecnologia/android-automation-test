package com.fourall.aat.contract

interface UserDataContract {

    interface Local {

        fun saveUser(
            name: String,
            age: String
        )
    }
}