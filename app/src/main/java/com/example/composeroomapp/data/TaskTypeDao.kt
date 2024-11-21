package com.example.composeroomapp.data

import androidx.room.*

@Dao
interface TaskTypeDao {
    @Insert
    suspend fun insert(taskType: TaskType)

    @Query("SELECT * FROM task_types")
    suspend fun getAllTaskTypes(): List<TaskType>

    @Update
    suspend fun update(taskType: TaskType)

    @Delete
    suspend fun delete(taskType: TaskType)
}
