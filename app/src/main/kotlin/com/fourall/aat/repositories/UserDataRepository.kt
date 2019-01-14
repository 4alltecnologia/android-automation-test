package com.fourall.aat.repositories

import com.fourall.aat.contract.UserDataContract
import com.fourall.aat.contract.repositories.UserRepository
import com.fourall.aat.models.User

class UserDataRepository(private val userLocalDataSource: UserDataContract.Local) : UserRepository {

    override fun getUserById(id: Long): User? = userLocalDataSource.getUserById(id)

    override fun saveUser(name: String, age: String): Long {

        return userLocalDataSource.saveUser(name, age)
    }
}