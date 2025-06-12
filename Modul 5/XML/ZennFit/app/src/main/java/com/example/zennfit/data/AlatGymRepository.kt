package com.example.zennfit.data

import android.util.Log
import com.example.zennfit.model.*
import com.example.zennfit.network.WgerApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext

class AlatGymRepository(
    private val wgerApiService: WgerApiService,
    private val exerciseInfoDao: ExerciseInfoDao
) {
    fun getExerciseInfo(searchQuery: String? = null): Flow<List<ExerciseInfo>> = exerciseInfoDao.getAllExerciseInfo()
        .map { entities ->
            Log.d("AlatGymRepositoryFlow", "Mapping ${entities.size} items from Room entities to external models.")
            entities.map { it.toExternalModel() }
        }
        .onEach {
            Log.d("AlatGymRepositoryFlow", "Flow emitted data to ViewModel (from Room): ${it.size} items.")

            if (it.isEmpty() || searchQuery != null) {
                Log.d("AlatGymRepositoryFlow", "Attempting to fetch from network with query: '${searchQuery ?: "none"}'")
                fetchAndSaveToRoom(searchQuery)
            } else {
                Log.d("AlatGymRepositoryFlow", "Room contains data, skipping immediate network fetch for now.")
            }
        }
        .catch { e ->
            Log.e("AlatGymRepositoryFlow", "Error in flow collection: ${e.message}", e)
            emit(emptyList())
        }
        .flowOn(Dispatchers.IO)

    private suspend fun fetchAndSaveToRoom(query: String?) {
        coroutineScope {
            try {
                Log.d("AlatGymRepositoryNetwork", "Starting network fetch with query: '${query ?: "none"}'")
                val response = wgerApiService.getExerciseInfo(query)
                Log.d("AlatGymRepositoryNetwork", "Network response received. isSuccessful: ${response.isSuccessful}, Code: ${response.code()}, Message: ${response.message()}")

                if (response.isSuccessful) {
                    val apiResponse = response.body()
                    if (apiResponse != null) {
                        val exerciseInfoList = apiResponse.results
                        if (exerciseInfoList != null && exerciseInfoList.isNotEmpty()) {
                            Log.d("AlatGymRepositoryNetwork", "API response body is not null. Results size: ${exerciseInfoList.size}")

                            val exerciseInfoEntities = exerciseInfoList.map { it.toEntity() }

                            exerciseInfoDao.deleteAllExerciseInfo()
                            exerciseInfoDao.insertAll(exerciseInfoEntities)
                            Log.d("AlatGymRepositoryNetwork", "Successfully fetched ${exerciseInfoList.size} items from network and saved to Room.")

                        } else {
                            Log.w("AlatGymRepositoryNetwork", "API response results are null or empty for query: '${query ?: "none"}'")
                            if (query != null) {
                                exerciseInfoDao.deleteAllExerciseInfo()
                                Log.d("AlatGymRepositoryNetwork", "Cleared Room data as network search for '${query}' returned empty.")
                            }
                        }
                    } else {
                        Log.w("AlatGymRepositoryNetwork", "API response body is null.")
                    }
                } else {
                    Log.e("AlatGymRepositoryNetwork", "API call failed with code: ${response.code()}, message: ${response.message()}, Raw Error Body: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.e("AlatGymRepositoryNetwork", "Error during network fetch for query '${query ?: "none"}': ${e.message}", e)
            }
        }
    }

    suspend fun getExerciseInfoById(id: Int): ExerciseInfo? {
        return withContext(Dispatchers.IO) {
            try {
                Log.d("AlatGymRepositoryById", "Attempting to fetch exercise with ID: $id")
                val response = wgerApiService.getExerciseInfoById(id)
                Log.d("AlatGymRepositoryById", "Response for ID $id: isSuccessful: ${response.isSuccessful}, Code: ${response.code()}, Message: ${response.message()}")

                if (response.isSuccessful) {
                    val exercise = response.body()
                    if (exercise != null) {
                        Log.d("AlatGymRepositoryById", "Successfully fetched exercise ID $id: ${exercise.getLocalizedName()}")
                        exercise
                    } else {
                        Log.w("AlatGymRepositoryById", "API response body for ID $id is null.")
                        null
                    }
                } else {
                    Log.e("AlatGymRepositoryById", "API call failed for ID $id with code: ${response.code()}, message: ${response.message()}, Raw Error Body: ${response.errorBody()?.string()}")
                    null
                }
            } catch (e: Exception) {
                Log.e("AlatGymRepositoryById", "Error fetching exercise with ID $id: ${e.message}", e)
                null
            }
        }
    }

    private fun ExerciseInfo.toEntity(): ExerciseInfoEntity {
        return ExerciseInfoEntity(
            id = this.id,
            name = this.getLocalizedName(),
            description = this.getLocalizedDescription(),
            imageUrl = this.getMainImageUrl(),
            videoUrl = this.getMainVideoUrl()
        )
    }

    private fun ExerciseInfoEntity.toExternalModel(): ExerciseInfo {
        return ExerciseInfo(
            id = this.id,
            uuid = "",
            created = null,
            lastUpdate = null,
            lastUpdateGlobal = null,
            category = null,
            muscles = emptyList(),
            musclesSecondary = emptyList(),
            equipment = emptyList(),
            license = null,
            licenseAuthor = null,
            images = if (this.imageUrl != null) listOf(ExerciseImage(0, null, this.imageUrl, true, "")) else null,
            videos = if (this.videoUrl != null) listOf(ExerciseVideo(0, null, this.videoUrl, true, "")) else null,
            translations = listOf(
                ExerciseTranslation(
                    id = 0,
                    uuid = "",
                    name = this.name,
                    exerciseId = this.id,
                    description = this.description,
                    created = null,
                    languageId = 2,
                    aliases = emptyList(),
                    notes = emptyList(),
                    license = null,
                    licenseTitle = null,
                    licenseObjectUrl = null,
                    licenseAuthor = null,
                    licenseAuthorUrl = null,
                    licenseDerivativeSourceUrl = null,
                    authorHistory = emptyList()
                )
            ),
            variations = null,
            authorHistory = emptyList(),
            totalAuthorsHistory = emptyList()
        )
    }
}