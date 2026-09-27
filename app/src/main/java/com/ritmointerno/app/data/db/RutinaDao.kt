package com.ritmointerno.app.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface RutinaDao {

    @Insert
    suspend fun insertar(rutina: RutinaEntity): Long

    @Update
    suspend fun actualizar(rutina: RutinaEntity)

    @Delete
    suspend fun eliminar(rutina: RutinaEntity)

    // LiveData: Room notifica solo automáticamente y la lista se refresca
    // sin reiniciar la app cada vez que se crea/edita/elimina una rutina.
    @Query("SELECT * FROM rutinas WHERE userId = :userId ORDER BY createdAt DESC")
    fun observarPorUsuario(userId: Long): LiveData<List<RutinaEntity>>

    @Query("SELECT * FROM rutinas WHERE id = :id LIMIT 1")
    suspend fun buscarPorId(id: Long): RutinaEntity?
}