package com.example.aplicacion12.data

import androidx.room.*

@Dao
interface TaskTypeDao {
    @Insert
    suspend fun insert(taskType: TaskType)

    @Query("SELECT * FROM task_types WHERE title = :title LIMIT 1")
    suspend fun getTaskTypeByTitle(title: String): TaskType?

    @Update
    suspend fun update(taskType: TaskType)

    @Delete
    suspend fun delete(taskType: TaskType)

    @Query("SELECT * FROM task_types")
    suspend fun getAllTaskTypes(): List<TaskType>
}