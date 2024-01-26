package com.elsysCup.db

import com.elsysCup.DatabaseConfig
import com.elsysCup.models.LoginTable
import com.elsysCup.models.Users
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction


class MyDatabase(config: DatabaseConfig) {
    val loginDB = LoginDB(this)
    val userDB = UserDB(this)
    init {
        Database.connect(config.jdbc, config.driver, config.user, config.password)
        transaction{
            SchemaUtils.create(Users)
            SchemaUtils.create(LoginTable)

            // Add any missing columns or tables
            SchemaUtils.createMissingTablesAndColumns(Users)
            SchemaUtils.createMissingTablesAndColumns(LoginTable)
        }
    }

    suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }
}