package com.ritmointerno.app.ui.auth

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ritmointerno.app.data.AuthRepository
import com.ritmointerno.app.data.db.AppDatabase

class AuthViewModelFactory(context: Context) : ViewModelProvider.Factory {

    private val repository = AuthRepository(AppDatabase.getInstance(context).userDao())

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return AuthViewModel(repository) as T
    }
}