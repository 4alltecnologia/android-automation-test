package com.fourall.aat.models

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "user")
data class User(
    @PrimaryKey(autoGenerate = true) var id: Long = 0,
    var name: String = "",
    var age: String = ""
) : Parcelable