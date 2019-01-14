package com.fourall.aat

import android.app.Application
import android.arch.persistence.room.Room
import com.fourall.aat.data.local.AppDataBase

class Application : Application() {

    companion object {
        lateinit var database: AppDataBase
    }

    override fun onCreate() {
        super.onCreate()

        database = Room.databaseBuilder(this, AppDataBase::class.java, "database.db").build()
    }
}