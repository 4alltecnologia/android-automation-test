package com.fourall.aat.data.local

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.fourall.aat.models.User

@Dao
interface UserDAO {

    @Insert
    fun saveUser(user: User): Long

    @Query("SELECT * FROM user WHERE user.id = :id LIMIT 1")
    fun getUserById(id: Long): User?

}