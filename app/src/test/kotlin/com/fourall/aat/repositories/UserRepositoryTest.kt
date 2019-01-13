package com.fourall.aat.repositories

import com.fourall.aat.contract.UserDataContract
import com.fourall.aat.contract.repositories.UserRepository
import com.fourall.aat.models.User
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class UserRepositoryTest {

    private lateinit var userLocalDataSourceMock: UserDataContract.Local

    private lateinit var userRepository: UserRepository

    @Before fun setUp() {

        userLocalDataSourceMock = mock()

        userRepository = UserDataRepository(userLocalDataSourceMock)
    }

    @Test fun `Get user, when it is requested to get user, then returns user successfully`() {

        // ARRANGE

        val EXPECTED_USER_NAME = "Zé Renato"
        val EXPECTED_USER_AGE = "45"

        val expectedUser = User(EXPECTED_USER_NAME, EXPECTED_USER_AGE)

        whenever(userLocalDataSourceMock.getUser()).thenReturn(expectedUser)

        // ACT

        val user = userRepository.getUser()

        // ASSERT

        assertEquals(expectedUser, user)
    }

    @Test fun `Save user, when it is passed user name and user age, then verify user is saved locally`() {

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