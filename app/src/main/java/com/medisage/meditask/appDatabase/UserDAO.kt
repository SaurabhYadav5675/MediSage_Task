package com.medisage.meditask.appDatabase

import androidx.lifecycle.LiveData
import androidx.room.*
import com.medisage.meditask.model.User

@Dao
interface UserDAO {

    @Insert
    suspend fun insertUser(user: User)

    @Update
    suspend fun updateUser(user: User)

    @Delete
    suspend fun deleteUser(user: User)

    @Query("SELECT * FROM user")
    fun getAllUser(): LiveData<List<User>>

    @Query("SELECT * FROM user WHERE email like :userEmail AND password =:userPass")
    fun getUser(userEmail: String, userPass: String): LiveData<List<User>>
}