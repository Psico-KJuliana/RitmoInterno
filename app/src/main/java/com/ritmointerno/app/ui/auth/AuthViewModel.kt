package com.ritmointerno.app.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ritmointerno.app.data.AuthRepository
import com.ritmointerno.app.data.AuthResultado
import kotlinx.coroutines.launch

/** ViewModel compartido por Login y Registro (MVVM: no conoce Views ni Room directamente). */
class AuthViewModel(private val repository: AuthRepository) : ViewModel() {

    private val _resultado = MutableLiveData<AuthResultado>()
    val resultado: LiveData<AuthResultado> = _resultado

    fun registrar(nombre: String, email: String, password: String) {
        viewModelScope.launch {
            _resultado.value = repository.registrar(nombre, email, password)
        }
    }

    fun iniciarSesion(email: String, password: String) {
        viewModelScope.launch {
            _resultado.value = repository.iniciarSesion(email, password)
        }
    }
}