package com.ritmointerno.app.data.db

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Usuario registrado localmente. El email es único (índice UNIQUE) y la
 * contraseña nunca se guarda en texto plano: solo su hash (PasswordHasher).
 */
@Entity(
    tableName = "usuarios",
    indices = [Index(value = ["email"], unique = true)]
)
data class UserEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val nombre: String,
    val email: String,
    val passwordHash: String,
    val createdAt: Long = System.currentTimeMillis()
)