package com.example.zennfit.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ExerciseInfoDao {

    @Query("SELECT * FROM exercise_info_table ORDER BY name ASC")
    fun getAllExerciseInfo(): Flow<List<ExerciseInfoEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(exerciseInfo: List<ExerciseInfoEntity>)

    @Query("DELETE FROM exercise_info_table")
    suspend fun deleteAllExerciseInfo()
}