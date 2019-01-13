package com.fourall.aat.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(val name: String = "",
                val age: String = "") : Parcelable