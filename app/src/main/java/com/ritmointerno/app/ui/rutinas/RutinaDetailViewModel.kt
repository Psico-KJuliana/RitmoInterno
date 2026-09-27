package com.ritmointerno.app.ui.rutinas

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ritmointerno.app.data.RutinaRepository
import com.ritmointerno.app.data.db.RutinaEntity
import kotlinx.coroutines.launch

/** ViewModel de la Vista 3 (detalle): carga, marca favorito y elimina una rutina. */
class RutinaDetailViewModel(private val repository: RutinaRepository) : ViewModel() {

    private val _rutina = MutableLiveData<RutinaEntity?>()
    val rutina: LiveData<RutinaEntity?> = _rutina

    private val _eliminado = MutableLiveData<Boolean>()
    val eliminado: LiveData<Boolean> = _eliminado

    fun cargar(id: Long) {
        viewModelScope.launch {
            _rutina.value = repository.obtenerPorId(id)
        }
    }

    fun alternarFavorito() {
        val actual = _rutina.value ?: return
        viewModelScope.launch {
            repository.alternarFavorito(actual)
            _rutina.value = repository.obtenerPorId(actual.id)
        }
    }

    fun eliminar() {
        val actual = _rutina.value ?: return
        viewModelScope.launch {
            repository.eliminar(actual)
            _eliminado.value = true
        }
    }
}