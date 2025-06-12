package com.example.zennfit.network

import com.example.zennfit.model.WgerExerciseInfoResponse
import com.example.zennfit.model.ExerciseInfo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WgerApiService {
    @GET("api/v2/exerciseinfo/")
    suspend fun getExerciseInfo(
        @Query("search") query: String? = null
    ): Response<WgerExerciseInfoResponse>

    @GET("api/v2/exerciseinfo/{id}/")
    suspend fun getExerciseInfoById(
        @Path("id") id: Int
    ): Response<ExerciseInfo>
}