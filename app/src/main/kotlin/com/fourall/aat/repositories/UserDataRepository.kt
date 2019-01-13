package com.fourall.aat.repositories

import com.fourall.aat.contract.UserDataContract
import com.fourall.aat.contract.repositories.UserRepository

class UserDataRepository(private val userLocalDataSource: UserDataContract.Local) : UserRepository {

    override fun saveUser(name: String, age: String) {

        userLocalDataSource.saveUser(name, age)
    }
}