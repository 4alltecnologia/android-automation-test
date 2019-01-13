package com.fourall.aat.data.di

import com.fourall.aat.models.GenericCommand
import com.fourall.aat.viewmodels.SingleLiveEvent

interface CommandProvider {

    fun getCommand(): SingleLiveEvent<GenericCommand>
}