package com.fourall.aat.data.local

import com.fourall.aat.contract.UserDataContract
import com.fourall.aat.models.User

class UserLocalDataSource(private val userDAO: UserDAO) : UserDataContract.Local {

    override fun saveUser(name: String, age: String) {
        return userDAO.saveUser(User(name = name, age = age))
    }

    override fun getUserById(id: Int): User {
        return userDAO.getUserById(id)
    }

    override fun getUser(): User {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}