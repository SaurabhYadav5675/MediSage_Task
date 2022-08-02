package com.medisage.meditask.appDatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.medisage.meditask.model.Posts
import com.medisage.meditask.model.User

@Database(entities = [User::class, Posts::class], version = 1)
abstract class UserDatabase : RoomDatabase() {
    abstract fun userDao(): UserDAO
    abstract fun postsDao(): PostsDAO

    companion object {
        @Volatile
        private var Instance: UserDatabase? = null


        fun getDatabase(context: Context): UserDatabase {
            if (Instance == null) {
                synchronized(this)
                {
                    Instance =
                        Room.databaseBuilder(
                            context.applicationContext,
                            UserDatabase::class.java,
                            "userDB"
                        )
                            .build()
                }
            }
            return Instance!!
        }
    }
}