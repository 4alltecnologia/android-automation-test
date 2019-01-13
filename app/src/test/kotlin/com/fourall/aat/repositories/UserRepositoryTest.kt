package com.fourall.aat.repositories

import com.fourall.aat.contract.UserDataContract
import com.fourall.aat.contract.repositories.UserRepository
import com.fourall.aat.models.User
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import org.junit.Before
import org.junit.Test

class UserRepositoryTest {

    private lateinit var userLocalDataSourceMock: UserDataContract.Local

    private lateinit var userRepository: UserRepository

    @Before fun setUp() {

        userLocalDataSourceMock = mock()

        userRepository = UserDataRepository(userLocalDataSourceMock)
    }

    @Test fun `Save user, when it is requested to save user, then verify user is saved locally`() {

        // ARRANGE

        val USER_NAME = "Zé Renato"
        val USER_AGE = "45"

        val user = User(USER_NAME, USER_AGE)

        // ACT

        userRepository.saveUser(USER_NAME, USER_AGE)

        // ASSERT

        verify(userLocalDataSourceMock, times(1)).saveUser(USER_NAME, USER_AGE)
    }
}