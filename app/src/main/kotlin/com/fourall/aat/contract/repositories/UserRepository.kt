package com.fourall.aat.contract.repositories

interface UserRepository {

    fun saveUser(name: String,
                 age: String)
}