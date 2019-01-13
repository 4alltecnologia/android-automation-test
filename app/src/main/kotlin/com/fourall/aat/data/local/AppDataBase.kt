package com.fourall.aat.data.local

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.fourall.aat.models.User

@Database(version = 1, entities = [User::class])
abstract class AppDataBase : RoomDatabase() {
    abstract fun userDao(): UserDAO
}