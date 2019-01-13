package com.fourall.aat.data.di

import com.fourall.aat.models.GenericCommand
import com.fourall.aat.viewmodels.SingleLiveEvent

object CommandInjector : CommandProvider {

    override fun getCommand(): SingleLiveEvent<GenericCommand> = SingleLiveEvent()
}