package com.fourall.aat.viewmodels

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.fourall.aat.contract.repositories.UserRepository
import com.fourall.aat.data.di.CommandProvider
import com.fourall.aat.models.GenericCommand
import com.fourall.aat.models.User
import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class InputViewModelTest {

    private lateinit var userRepositoryMock: UserRepository
    private lateinit var commandProviderMock: CommandProvider

    private lateinit var commandMock: SingleLiveEvent<GenericCommand>

    @JvmField
    @Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var inputViewModel: InputViewModel

    @Before
    fun setUp() {

        userRepositoryMock = mock()
        commandProviderMock = mock()

        commandMock = mock()

        whenever(commandProviderMock.getCommand()).thenReturn(commandMock)

        inputViewModel = InputViewModel(userRepositoryMock, commandProviderMock)
    }

    @Test
    fun `Load user, when it is requested to load user, then shows user info`() {

        // ARRANGE

        val EXPECTED_USER_ID = 1L
        val EXPECTED_USER_NAME = "Zé Renato"
        val EXPECTED_USER_AGE = "45"

        val expectedUser = User(EXPECTED_USER_ID, EXPECTED_USER_NAME, EXPECTED_USER_AGE)

        val EXPECTED_COMMAND = InputViewModel.Command.ShowUserInfo(expectedUser)

        val commandCaptor = argumentCaptor<InputViewModel.Command.ShowUserInfo>()

        // ACT

        runBlocking {

            whenever(userRepositoryMock.getUserById(EXPECTED_USER_ID)).thenReturn(expectedUser)

            inputViewModel.loadUserById(EXPECTED_USER_ID)
        }

        // ASSERT

        verify(commandMock, times(1)).postValue(commandCaptor.capture())

        assertEquals(EXPECTED_COMMAND.user, commandCaptor.firstValue.user)
    }
}