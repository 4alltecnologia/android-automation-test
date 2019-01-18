package com.fourall.aat.data.local

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.fourall.aat.models.User

@Dao
interface UserDAO {

    @Query("SELECT * FROM user")
    fun getUsers(): List<User>

    @Query("SELECT * FROM user WHERE user.id = :id LIMIT 1")
    fun getUserById(id: Long): User?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveUser(user: User): Long
}