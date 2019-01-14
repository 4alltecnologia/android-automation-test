package com.fourall.aat.contract.repositories

import com.fourall.aat.models.User

interface UserRepository {

    fun getUserById(id: Long): User?

    fun saveUser(
        name: String,
        age: String
    ): Long
}