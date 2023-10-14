package com.krushiler.data.storage.dao

import com.krushiler.data.dao.Dao
import com.krushiler.data.storage.dbo.UserDbo
import com.krushiler.data.storage.dbo.Users
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.update

class UserDao(database: Database) : Dao(database) {
    suspend fun getUserByToken(token: String): UserDbo? = dbQuery {
        Users.select { Users.token eq token }.map { Users.resultRowToUser(it) }.singleOrNull()
    }

    suspend fun getUserByLogin(login: String): UserDbo? = dbQuery {
        Users.select { Users.login eq login }.map { Users.resultRowToUser(it) }.singleOrNull()
    }

    suspend fun updateName(login: String, name: String): Boolean = dbQuery {
        Users.update({ Users.login eq login }) {
            it[Users.name] = name
        } > 0
    }

    suspend fun createUser(userDbo: UserDbo): Boolean = dbQuery {
        Users.insert {
            it[login] = userDbo.login
            it[password] = userDbo.password
            it[token] = userDbo.token
            it[name] = userDbo.name
        }.insertedCount > 0
    }
}