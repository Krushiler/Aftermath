package com.krushiler.data.storage.dbo

import org.jetbrains.exposed.sql.*

data class UserDbo(
    val login: String,
    val token: String,
    val password: String,
)

object Users : Table() {
    val login = varchar("login", 64)
    val password = varchar("password", 64)
    val token = varchar("token", 128)

    override val primaryKey = PrimaryKey(login)

    fun resultRowToUser(row: ResultRow) = UserDbo(
        login = row[login],
        token = row[token],
        password = row[password],
    )
}