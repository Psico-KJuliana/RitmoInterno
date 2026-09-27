package com.ritmointerno.app.ui.rutinas

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ritmointerno.app.data.RutinaRepository
import com.ritmointerno.app.data.db.RutinaEntity
import kotlinx.coroutines.launch

/** ViewModel de la Vista 2 (listado): expone las rutinas del usuario con sesión activa. */
class RutinaListViewModel(
    private val repository: RutinaRepository,
    private val userId: Long
) : ViewModel() {

    val rutinas: LiveData<List<RutinaEntity>> = repository.observarRutinas(userId)

    fun eliminar(rutina: RutinaEntity) {
        viewModelScope.launch { repository.eliminar(rutina) }
    }

    fun alternarFavorito(rutina: RutinaEntity) {
        viewModelScope.launch { repository.alternarFavorito(rutina) }
    }
}