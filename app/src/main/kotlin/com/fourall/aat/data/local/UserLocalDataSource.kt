package com.fourall.aat.data.local

import com.fourall.aat.contract.UserDataContract
import com.fourall.aat.models.User

class UserLocalDataSource(private val userDAO: UserDAO) : UserDataContract.Local {

    override fun getUsers(): List<User> = userDAO.getUsers()

    override fun getUserById(id: Long): User? = userDAO.getUserById(id)

    override fun saveUser(name: String, age: String): Long =
            userDAO.saveUser(User(name = name, age = age))
}