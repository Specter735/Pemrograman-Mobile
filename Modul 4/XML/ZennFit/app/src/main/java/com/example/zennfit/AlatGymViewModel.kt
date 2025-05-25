package com.example.zennfit

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import android.util.Log

class AlatGymViewModel : ViewModel() {
    private val _alatList = MutableStateFlow<List<AlatGym>>(emptyList())
    val alatList: StateFlow<List<AlatGym>> = _alatList.asStateFlow()

    private val _selectedItem = MutableStateFlow<AlatGym?>(null)
    val selectedItem: StateFlow<AlatGym?> = _selectedItem.asStateFlow()

    init {
        loadAlatGym()
    }

    private fun loadAlatGym() {
        val data =listOf(
            AlatGym(1, "BenchPress", R.string.desc_benchpress, R.drawable.benchpress, R.drawable.incline_benchpress),
            AlatGym(2, "Row Cable", R.string.desc_row, R.drawable.cablerow, R.drawable.rowbro),
            AlatGym(3, "Shoulderpress Machine", R.string.desc_shoulder, R.drawable.shoulderpress, R.drawable.shoulderpress_machine),
            AlatGym(4, "Legpress Machine", R.string.desc_legpress, R.drawable.legpress, R.drawable.legpressmachine),
            AlatGym(5, "Fly Machine", R.string.desc_fly, R.drawable.fly, R.drawable.fly_machine)
        )
        _alatList.value = data
        Log.d("AlatGymViewModel", "Data alat gym berhasil dimuat: ${data.size} item")
    }
    fun onItemClicked(item: AlatGym) {
        _selectedItem.value = item
        Log.d("AlatGymViewModel", "Item diklik: ${item.nama}")
    }
}