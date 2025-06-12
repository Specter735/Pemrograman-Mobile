package com.example.zennfit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zennfit.data.AlatGymRepository
import com.example.zennfit.model.ExerciseInfo
import com.example.zennfit.model.getLocalizedName
import com.example.zennfit.model.getLocalizedDescription
import com.example.zennfit.model.getMainImageUrl
import com.example.zennfit.model.getMainVideoUrl

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import android.util.Log

class AlatGymViewModel(
    private val repository: AlatGymRepository
) : ViewModel() {

    private val _alatList = MutableStateFlow<List<ExerciseInfo>>(emptyList())
    val alatList: StateFlow<List<ExerciseInfo>> = _alatList.asStateFlow()

    private val _selectedItem = MutableStateFlow<ExerciseInfo?>(null)
    val selectedItem: StateFlow<ExerciseInfo?> = _selectedItem.asStateFlow()

    private val curatedExerciseIds = listOf(
        73,  // Bench Press
        272, // Hammercurls
        465,
        567,
        194,
        475
    )

    init {
        Log.d("AlatGymViewModel", "ViewModel init block executed.")
        loadCuratedExercises()
    }

    private fun loadCuratedExercises() {
        Log.d("AlatGymViewModel", "loadCuratedExercises() called to fetch specific IDs.")
        viewModelScope.launch {
            val loadedExercises = mutableListOf<ExerciseInfo>()
            for (id in curatedExerciseIds) {
                Log.d("AlatGymViewModel", "Attempting to fetch exercise with ID: $id")
                val exercise = repository.getExerciseInfoById(id)
                if (exercise != null) {
                    val hasImage = !exercise.getMainImageUrl().isNullOrEmpty()
                    val hasDescription = !(exercise.getLocalizedDescription() == "Deskripsi tidak tersedia." || exercise.getLocalizedDescription().isBlank())
                    val hasVideo = !exercise.getMainVideoUrl().isNullOrEmpty()

                    if (hasImage && hasDescription) {
                        loadedExercises.add(exercise)
                        Log.d("AlatGymViewModel", "Successfully added complete exercise ID $id: ${exercise.getLocalizedName()} | HasImg: $hasImage | HasDesc: $hasDescription | HasVid: $hasVideo")
                    } else {
                        Log.w("AlatGymViewModel", "Exercise ID $id (${exercise.getLocalizedName()}) is not 'complete' enough (missing img/desc). Not adding to list. HasImg: $hasImage | HasDesc: $hasDescription | HasVid: $hasVideo")
                    }

                } else {
                    Log.w("AlatGymViewModel", "Failed to fetch exercise with ID: $id. Not adding to list.")
                }
            }
            _alatList.value = loadedExercises
            Log.d("AlatGymViewModel", "Finished loading curated exercises. Displaying ${loadedExercises.size} items.")
        }
    }

    fun onItemClicked(item: ExerciseInfo) {
        _selectedItem.value = item
        Log.d("AlatGymViewModel", "Item diklik: ${item.getLocalizedName()}")
    }

    fun clearSelectedItem() {
        _selectedItem.value = null
        Log.d("AlatGymViewModel", "Selected item cleared.")
    }

    fun refreshData() {
        Log.d("AlatGymViewModel", "refreshData() called. Reloading curated exercises.")
        loadCuratedExercises()
    }
}