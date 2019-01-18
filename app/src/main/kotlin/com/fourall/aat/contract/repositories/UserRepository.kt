package com.fourall.aat.contract.repositories

import com.fourall.aat.models.User

interface UserRepository {

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