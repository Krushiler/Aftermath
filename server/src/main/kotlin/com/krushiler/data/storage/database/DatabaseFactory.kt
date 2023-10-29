package com.krushiler.data.storage.database

import com.krushiler.data.storage.dbo.Dictionaries
import com.krushiler.data.storage.dbo.Terms
import com.krushiler.data.storage.dbo.Users
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseFactory {
    fun create(): Database {
        val driverClassName = "org.h2.Driver"
        val jdbcURL = "jdbc:h2:file:./build/db"
        val database = Database.connect(jdbcURL, driverClassName)
        transaction(database) {
            SchemaUtils.create(Users)
            SchemaUtils.create(Dictionaries)
            SchemaUtils.create(Terms)
        }
        return database
    }
}