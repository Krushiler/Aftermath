package com.krushiler.data.storage.dbo

import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table

data class UserDbo(
    val login: String,
    val token: String,
    val password: String,
    val name: String,
    val avatar: String?,
)

object Users : Table() {
    val login = varchar("login", 64)
    val password = varchar("password", 64)
    val token = varchar("token", 128)
    val name = varchar("username", 128)
    val avatar = varchar("avatar", 128).nullable()

    override val primaryKey = PrimaryKey(login)

    fun resultRowToUser(row: ResultRow) = UserDbo(
        login = row[login],
        token = row[token],
        password = row[password],
        name = row[name],
        avatar = row[avatar]
    )
}