package com.ritmointerno.app.data.db

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Entidad CRUD principal de la app: una rutina de práctica que la usuaria
 * crea, edita, marca como favorita o elimina. Cada rutina pertenece a un
 * usuario (userId); si el usuario se elimina, sus rutinas se eliminan en
 * cascada.
 */
@Entity(
    tableName = "rutinas",
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("userId")]
)
data class RutinaEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val userId: Long,
    val titulo: String,
    val descripcion: String,
    val duracionMinutos: Int,
    val fecha: String,
    val favorito: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)