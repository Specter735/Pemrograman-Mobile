package com.example.zennfit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.zennfit.data.AlatGymRepository

class AlatGymViewModelFactory(
    private val repository: AlatGymRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AlatGymViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AlatGymViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}