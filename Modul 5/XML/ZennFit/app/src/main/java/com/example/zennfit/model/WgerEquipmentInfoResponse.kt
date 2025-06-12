package com.example.zennfit.model

import com.google.gson.annotations.SerializedName

data class WgerExerciseInfoResponse(
    @SerializedName("count") val count: Int,
    @SerializedName("next") val next: String?,
    @SerializedName("previous") val previous: String?,
    @SerializedName("results") val results: List<ExerciseInfo> // Ganti List<Equipment> ke List<ExerciseInfo>
)