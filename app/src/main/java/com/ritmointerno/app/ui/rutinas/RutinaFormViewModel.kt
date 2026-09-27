package com.ritmointerno.app.ui.rutinas

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ritmointerno.app.data.RutinaRepository
import com.ritmointerno.app.data.db.RutinaEntity
import kotlinx.coroutines.launch

/** Resultado de intentar guardar (crear o editar) una rutina. */
sealed class GuardarResultado {
    object Exito : GuardarResultado()
    data class Error(val mensaje: String) : GuardarResultado()
}

/** ViewModel de la Vista 4 (formulario): valida y guarda, sirve para crear y editar. */
class RutinaFormViewModel(private val repository: RutinaRepository) : ViewModel() {

    private val _guardado = MutableLiveData<GuardarResultado>()
    val guardado: LiveData<GuardarResultado> = _guardado

    fun guardar(
        idExistente: Long?,
        userId: Long,
        titulo: String,
        descripcion: String,
        duracionTexto: String,
        fecha: String,
        favorito: Boolean
    ) {
        val tituloLimpio = titulo.trim()
        if (tituloLimpio.isBlank()) {
            _guardado.value = GuardarResultado.Error("El título es obligatorio.")
            return
        }
        val duracion = duracionTexto.trim().toIntOrNull()
        if (duracion == null || duracion <= 0) {
            _guardado.value = GuardarResultado.Error("La duración debe ser un número mayor a 0.")
            return
        }
        if (fecha.isBlank()) {
            _guardado.value = GuardarResultado.Error("Selecciona una fecha.")
            return
        }

        viewModelScope.launch {
            val rutina = RutinaEntity(
                id = idExistente ?: 0,
                userId = userId,
                titulo = tituloLimpio,
                descripcion = descripcion.trim(),
                duracionMinutos = duracion,
                fecha = fecha,
                favorito = favorito
            )
            if (idExistente == null) {
                repository.guardar(rutina)
            } else {
                repository.actualizar(rutina)
            }
            _guardado.value = GuardarResultado.Exito
        }
    }
}

