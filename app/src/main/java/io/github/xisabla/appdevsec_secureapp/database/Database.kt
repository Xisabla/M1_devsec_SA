package io.github.xisabla.appdevsec_secureapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import io.github.xisabla.appdevsec_secureapp.dao.AccountDao
import io.github.xisabla.appdevsec_secureapp.model.Account

@Database(entities = arrayOf(Account::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun accountDao() : AccountDao
}