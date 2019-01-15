package com.fourall.aat.data.local

import com.fourall.aat.contract.UserDataContract
import com.fourall.aat.models.User
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class UserLocalDataSourceTest {

    private lateinit var userDAOMock: UserDAO

    private lateinit var userLocalDataSource: UserDataContract.Local

    @Before
    fun setUp() {

        userDAOMock = mock()

        userLocalDataSource = UserLocalDataSource(userDAOMock)
    }

    @Test
    fun `Get user, when it is requested to get user, then returns user successfully`() {

        // ARRANGE

        val EXPECTED_USER_ID = 1L
        val EXPECTED_USER_NAME = "Zé Renato"
        val EXPECTED_USER_AGE = "45"

        val expectedUser = User(
            id = EXPECTED_USER_ID,
            name = EXPECTED_USER_NAME,
            age = EXPECTED_USER_AGE
        )

        whenever(userDAOMock.getUserById(1)).thenReturn(expectedUser)

        // ACT

        val user = userLocalDataSource.getUserById(1L)

        // ASSERT

        assertEquals(expectedUser, user)
    }

    @Test
    fun `Save user, when it is passed user name and user age, then verify user is saved locally`() {

        // ARRANGE

        val EXPECTED_USER_NAME = "Zé Renato"
        val EXPECTED_USER_AGE = "45"

        val expectedUser = User(name = EXPECTED_USER_NAME, age = EXPECTED_USER_AGE)

        // ACT

        userLocalDataSource.saveUser(EXPECTED_USER_NAME, EXPECTED_USER_AGE)

        // ASSERT

        verify(userDAOMock, times(1)).saveUser(expectedUser)
    }
}