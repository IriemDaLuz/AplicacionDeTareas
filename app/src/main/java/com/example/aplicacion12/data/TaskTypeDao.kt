package com.example.aplicacion12.data

import androidx.room.*

@Dao
interface TaskTypeDao {

    // Insertar un nuevo tipo de tarea
    @Insert
    suspend fun insert(taskType: TaskType)

    // Obtener un tipo de tarea por título
    @Query("SELECT * FROM task_types WHERE title = :title LIMIT 1")
    suspend fun getTaskTypeByTitle(title: String): TaskType?

    // Actualizar un tipo de tarea existente
    @Update
    suspend fun update(taskType: TaskType)

    // Eliminar un tipo de tarea específico
    @Delete
    suspend fun delete(taskType: TaskType)

    // Obtener todos los tipos de tarea
    @Query("SELECT * FROM task_types")
    suspend fun getAllTaskTypes(): List<TaskType>
}
