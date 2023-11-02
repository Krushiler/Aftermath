package com.krushiler.data.repository

import com.krushiler.data.storage.dao.UserDao
import com.krushiler.data.storage.dbo.UserDbo
import com.krushiler.util.generateUUID
import data.dto.AuthDto
import data.dto.UserDto
import util.generateUUID

class UserRepository(private val userDao: UserDao) {
    private suspend fun getUserByToken(token: String): UserDto {
        val dbo = userDao.getUserByToken(token) ?: throw IllegalArgumentException("User is not found")
        return UserDto(
            login = dbo.login, name = dbo.name, avatar = dbo.avatar
        )
    }

    suspend fun getUserByLogin(login: String): UserDto {
        val dbo = userDao.getUserByLogin(login) ?: throw IllegalArgumentException("User is not found")
        return UserDto(
            login = dbo.login, name = dbo.name, avatar = dbo.avatar
        )
    }

    suspend fun getUserByTokenOrNull(token: String): UserDto? = try {
        getUserByToken(token)
    } catch (_: Exception) {
        null
    }

    suspend fun updateUser(login: String, name: String, avatar: String?) {
        userDao.updateUser(login, name, avatar)
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
                login = login, password = password, token = token, name = login, avatar = null
            )
        )
        return AuthDto(token)
    }
}