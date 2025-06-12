package com.example.zennfit.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "exercise_info_table")
data class ExerciseInfoEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val description: String,
    val imageUrl: String?,
    val videoUrl: String?
)