package io.github.xisabla.appdevsec_secureapp.dao

import androidx.room.*
import io.github.xisabla.appdevsec_secureapp.model.Account

@Dao
interface AccountDao {
    @Query("SELECT * FROM account")
    fun getAll(): List<Account>

    @Query("SELECT * FROM account WHERE name == :name LIMIT 1")
    fun getByName(name: String): Account

    @Query("SELECT EXISTS(SELECT * FROM account WHERE name = :name)")
    fun exists(name: String): Boolean

    @Insert
    fun insertAll(vararg accounts: Account)

    @Insert
    fun insert(account: Account)

    @Update
    fun updateAll(vararg accounts: Account)

    @Update
    fun update(account: Account)

    @Delete
    fun delete(account: Account)
}