package com.ritmointerno.app.ui.rutinas

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ritmointerno.app.data.RutinaRepository
import com.ritmointerno.app.data.db.AppDatabase

class RutinaDetailViewModelFactory(context: Context) : ViewModelProvider.Factory {

    private val repository = RutinaRepository(AppDatabase.getInstance(context).rutinaDao())

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return RutinaDetailViewModel(repository) as T
    }
}