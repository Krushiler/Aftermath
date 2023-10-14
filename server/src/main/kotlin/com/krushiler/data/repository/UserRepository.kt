package com.krushiler.data.repository

import com.krushiler.data.storage.dao.UserDao
import com.krushiler.data.storage.dbo.UserDbo
import com.krushiler.util.generateUUID
import data.dto.AuthDto

class UserRepository(private val userDao: UserDao) {
    suspend fun getUserByToken(token: String): UserDbo? = try {
        userDao.getUserByToken(token)
    } catch (_: Exception) {
        null
    }

    suspend fun login(login: String, password: String): AuthDto {
        val user = userDao.getUserByLogin(login) ?: throw IllegalArgumentException("Username or password is invalid")
        if (user.password == password) return AuthDto(user.token)
        throw IllegalArgumentException("Username or password is invalid")
    }

    suspend fun register(login: String, password: String): AuthDto {
        val user = userDao.getUserByLogin(login)
        if (user != null) throw IllegalArgumentException("User already exists")
        val token = generateUUID()
        userDao.createUser(
            UserDbo(
                login = login,
                password = password,
                token = token,
                name = login
            )
        )
        return AuthDto(token)
    }
}