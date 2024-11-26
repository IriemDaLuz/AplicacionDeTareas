package com.example.aplicacion12.data

import androidx.room.*

@Dao
interface TaskTypeDao {


    // Actualizar un tipo de tarea existente
    @Update
    suspend fun update(taskType: TaskType)

    // Eliminar un tipo de tarea específico
    @Delete
    suspend fun delete(taskType: TaskType)

    @Query("SELECT * FROM task_types")
    suspend fun getAllTaskTypes(): List<TaskType>

    @Query("SELECT * FROM task_types WHERE title = :title LIMIT 1")
    suspend fun getTaskTypeByTitle(title: String): TaskType?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(taskType: TaskType)
}
