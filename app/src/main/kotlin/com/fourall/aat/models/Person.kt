package com.fourall.aat.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Person(val age: Int = 0, val name: String = "") : Parcelable