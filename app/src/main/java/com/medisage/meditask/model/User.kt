package com.medisage.meditask.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val contact: String,
    val email: String,
    val password: String
)
