package com.fourall.aat.repositories

import com.fourall.aat.contract.UserDataContract
import com.fourall.aat.contract.repositories.UserRepository
import com.fourall.aat.models.User
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test

class UserRepositoryTest {

    private lateinit var userLocalDataSourceMock: UserDataContract.Local

    private lateinit var userRepository: UserRepository

    @Before
    fun setUp() {

        userLocalDataSourceMock = mock()

        userRepository = UserDataRepository(userLocalDataSourceMock)
    }

    @Test fun `Get users, when it is requested to obtain all users, then returns users`() {

        // ARRANGE

        val EXPECTED_USER_ID_1 = 1L
        val EXPECTED_USER_NAME_1 = "Zé Renato"
        val EXPECTED_USER_AGE_1 = "45"

        val EXPECTED_USER_ID_2 = 2L
        val EXPECTED_USER_NAME_2 = "Ricardo Galho"
        val EXPECTED_USER_AGE_2 = "46"

        val EXPECTED_USER_ID_3 = 3L
        val EXPECTED_USER_NAME_3 = "Evelise Vincensi"
        val EXPECTED_USER_AGE_3 = "25"

        val expectedUser1 = User(EXPECTED_USER_ID_1, EXPECTED_USER_NAME_1, EXPECTED_USER_AGE_1)
        val expectedUser2 = User(EXPECTED_USER_ID_2, EXPECTED_USER_NAME_2, EXPECTED_USER_AGE_2)
        val expectedUser3 = User(EXPECTED_USER_ID_3, EXPECTED_USER_NAME_3, EXPECTED_USER_AGE_3)

        val expectedUsers = listOf(expectedUser1, expectedUser2, expectedUser3)

        whenever(userLocalDataSourceMock.getUsers()).thenReturn(expectedUsers)

        // ACT

        val users = userLocalDataSourceMock.getUsers()

        // ASSERT

        assertEquals(expectedUsers, users)
    }

    @Test fun `Get user by id, when it is passed an id from a non existent user, then returns null`() {

        // ARRANGE

        val EXPECTED_USER_ID = 0L

        whenever(userLocalDataSourceMock.getUserById(EXPECTED_USER_ID)).thenReturn(null)

        // ACT

        val user = userRepository.getUserById(EXPECTED_USER_ID)

        // ASSERT

        assertNull(user)
    }

    @Test fun `Get user by id, when it is passed an id from an existing user, then returns user successfully`() {

        // ARRANGE

        val EXPECTED_USER_ID = 1L
        val EXPECTED_USER_NAME = "Zé Renato"
        val EXPECTED_USER_AGE = "45"

        val expectedUser = User(EXPECTED_USER_ID, EXPECTED_USER_NAME, EXPECTED_USER_AGE)

        whenever(userLocalDataSourceMock.getUserById(EXPECTED_USER_ID)).thenReturn(expectedUser)

        // ACT

        val user = userRepository.getUserById(EXPECTED_USER_ID)

        // ASSERT

        assertEquals(expectedUser, user)
    }

    @Test fun `Save user, when it is passed user name and user age, then verify user is saved locally`() {

        // ARRANGE

        val USER_NAME = "Zé Renato"
        val USER_AGE = "45"

        // ACT

        userRepository.saveUser(USER_NAME, USER_AGE)

        // ASSERT

        verify(userLocalDataSourceMock, times(1)).saveUser(USER_NAME, USER_AGE)
    }
}