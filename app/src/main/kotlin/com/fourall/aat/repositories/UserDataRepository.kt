package com.fourall.aat.repositories

import com.fourall.aat.contract.UserDataContract
import com.fourall.aat.contract.repositories.UserRepository
import com.fourall.aat.models.User

class UserDataRepository(private val userLocalDataSource: UserDataContract.Local) : UserRepository {

    override fun getUsers(): List<User>  = userLocalDataSource.getUsers()

    override fun getUserById(id: Long): User? = userLocalDataSource.getUserById(id)

    override fun saveUser(name: String, age: String): Long = userLocalDataSource.saveUser(name, age)

    override fun saveUserById(id: Long, name: String, age: String): Long =
            userLocalDataSource.saveUserById(id, name, age)
}