package com.fourall.aat.contract

import com.fourall.aat.models.User

interface UserDataContract {

    interface Local {

        fun getUsers(): List<User>

        fun getUserById(id: Long): User?

        fun saveUser(
            name: String,
            age: String
        ): Long

        fun saveUserById(
                id: Long,
                name: String,
                age: String
        ): Long
    }
}