package com.ritmointerno.app.data

import androidx.lifecycle.LiveData
import com.ritmointerno.app.data.db.RutinaDao
import com.ritmointerno.app.data.db.RutinaEntity

/**
 * Repositorio del CRUD de rutinas. Intermediario entre los ViewModels y
 * Room: ningún ViewModel llama directamente al DAO.
 */
class RutinaRepository(private val rutinaDao: RutinaDao) {

    fun observarRutinas(userId: Long): LiveData<List<RutinaEntity>> =
            rutinaDao.observarPorUsuario(userId)

    suspend fun obtenerPorId(id: Long): RutinaEntity? = rutinaDao.buscarPorId(id)

    suspend fun guardar(rutina: RutinaEntity): Long = rutinaDao.insertar(rutina)

    suspend fun actualizar(rutina: RutinaEntity) = rutinaDao.actualizar(rutina)

    suspend fun eliminar(rutina: RutinaEntity) = rutinaDao.eliminar(rutina)

    suspend fun alternarFavorito(rutina: RutinaEntity) =
            rutinaDao.actualizar(rutina.copy(favorito = !rutina.favorito))
}