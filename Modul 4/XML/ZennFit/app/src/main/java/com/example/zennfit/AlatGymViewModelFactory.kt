package com.example.zennfit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class AlatGymViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AlatGymViewModel::class.java)) {
            return AlatGymViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
